package com.spbsu.pattern;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.util.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by Sergey Afonin on 27.03.2017.
 */
public class ServiceTest {

    private static final String URL = "http://localhost:8080/pattern/tasks/";

    @Test
    public void test() throws IOException {
        List<String> patterns = Files.readAllLines(Paths.get("src/main/resources/patterns.txt"));
        Gson gson = new Gson();
        List<String> jsons = new ArrayList<>();
        HashMap<String, Boolean> answers = new HashMap<>();
        Random random = new Random();

        for (int n = 0; n < 10000; ++n) {
            StringBuilder s = new StringBuilder();
            int characters = random.nextInt(7) + 1;
            for (int k = 0; k < characters; ++k) {
                char c = (char) (random.nextInt('Z' - 'A') + 'A');
                int rep = random.nextInt(95) + 5;
                for (int j = 0; j < rep; ++j)
                    s.append(c);
            }
            String randomString = s.toString();
            String pattern = patterns.get(random.nextInt(patterns.size()));
            StringBuilder result = new StringBuilder(pattern.replace("%w+", randomString));
            Boolean answer;
            if (random.nextBoolean()) {
                int position = random.nextInt(pattern.length() - 10) + 5;
                result.insert(position, "¶@¢ڴ");
                answer = false;
            } else {
                answer = true;
            }

            String string = result.toString();
            Message message = new Message(string);
            String json = gson.toJson(message);
            jsons.add(json);
            answers.put(string, answer);
        }

        long start = System.currentTimeMillis();
        final Map<HttpResponse, String> map = new ConcurrentHashMap<>();
        jsons.stream().parallel().forEach(json -> doRequest(map, json));
        long finish = System.currentTimeMillis();
        System.out.println(String.format("Jsons size: %s, Responses size: %s", jsons.size(), map.size()));
        System.out.println(String.format("Takes: %s ms", finish - start));

        for (HttpResponse response : map.keySet()) {
            String s = readResponseToString(response);
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(s).getAsJsonObject();
            boolean valid = obj.get("valid").getAsBoolean();
            String m = obj.get("message").getAsString();
            Assert.assertThat(obj.toString(), valid, equalTo(answers.get(m)));
        }
    }

    private void doRequest(Map<HttpResponse, String> responses, String json) {
        HttpResponse response = null;
        try {
            response = Request.Post(URL).bodyString(json, ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        responses.put(response, json);
    }

    private static String readResponseToString(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return "";
        }
        InputStream content = entity.getContent();
        Scanner s = new Scanner(content).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
