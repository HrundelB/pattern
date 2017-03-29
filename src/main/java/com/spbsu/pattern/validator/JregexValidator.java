package com.spbsu.pattern.validator;

import jregex.Matcher;
import jregex.Pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Afonin on 27.03.2017.
 */
public class JregexValidator implements Validator {

    private ThreadLocal<List<Matcher>> matchers = new ThreadLocal<>();

    public JregexValidator() {
        List<String> patterns = RegExpReader.readPatterns(RegExpReader.getJRegExp());
        matchers.set(new ArrayList<>(patterns.size()));
        for (String pattern : patterns) {
            Pattern p = new Pattern(pattern);
            matchers.get().add(p.matcher());
        }
    }

    @Override
    public boolean validate(String s) {
        for (Matcher matcher : matchers.get()) {
            if (matcher.matches(s))
                return true;
        }
        return false;
    }
}
