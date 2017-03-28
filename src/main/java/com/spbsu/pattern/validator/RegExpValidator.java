package com.spbsu.pattern.validator;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergey Afonin on 27.03.2017.
 */
public class RegExpValidator implements Validator {

    private static final String SPECIAL = "!№#%.,:;?\\/()+-“”―_'\"`&^{}[]<>|@$=~*";
    public static final String FILE = "patterns.txt";

    private List<Matcher> matchers = new ArrayList<>();

    public RegExpValidator(List<String> patterns) {
        StringBuilder builder = new StringBuilder("[\\w\\s\\Q");
        for (int i = 0; i < SPECIAL.length(); i++) {
            builder.append(SPECIAL.charAt(i));
        }
        builder.append("\\E]+?");
        String w = builder.toString();
        for (String pattern : patterns) {
            pattern = pattern.replace("%w+", w);
            Matcher matcher = Pattern.compile(pattern).matcher("");
            matchers.add(matcher);
        }
    }

    @Override
    public boolean validate(String s) {
        for (Matcher matcher : matchers) {
            if (matcher.reset(s).matches())
                return true;
        }
        return false;
    }

    public static RegExpValidator buildValidator() {
        try {
            List<String> strings = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(
                            Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE), "UTF8")))
            {
                for(String line; (line = br.readLine()) != null; ) {
                    strings.add(line);
                }
            }
            return new RegExpValidator(strings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
