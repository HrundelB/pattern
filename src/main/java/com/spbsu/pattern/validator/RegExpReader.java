package com.spbsu.pattern.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Afonin on 29.03.2017.
 */
public class RegExpReader {

    private static final String SPECIAL = "!№#%.,:;?\\/()+-“”―_'\"`&^{}[]<>|@$=~*";
    private static final String FILE = "patterns.txt";

    public static String getRegExp() {
        StringBuilder builder = new StringBuilder("[\\w\\s\\Q");
        for (int i = 0; i < SPECIAL.length(); i++) {
            builder.append(SPECIAL.charAt(i));
        }
        builder.append("\\E]+?");
        return builder.toString();
    }

    public static String getJRegExp() {
        String spc = "!";
        StringBuilder builder = new StringBuilder("[\\w\\s\\Q");
        for (int i = 0; i < spc.length(); i++) {
            builder.append(spc.charAt(i));
        }
        builder.append("\\E]+?");
        return builder.toString();
    }

    public static String getBricsRegExp() {
        return "\\w+";
    }

    public static List<String> readPatterns(String w) {
        List<String> strings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE), "UTF8"))) {
            for (String line; (line = br.readLine()) != null; ) {
                line = line.replace("%w+", w);
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
