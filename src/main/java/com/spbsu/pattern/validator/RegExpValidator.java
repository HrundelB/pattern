package com.spbsu.pattern.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Sergey Afonin on 27.03.2017.
 */
public class RegExpValidator implements Validator {

    private List<Pattern> patternList;

    public RegExpValidator() {
        List<String> patterns = RegExpReader.readPatterns(RegExpReader.getRegExp());
        patternList = patterns.stream().map(Pattern::compile).collect(Collectors.toList());
    }

    @Override
    public boolean validate(String s) {
        for (Pattern pattern : patternList) {
            if (pattern.matcher(s).matches())
                return true;
        }
        return false;
    }

}
