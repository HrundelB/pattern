package com.spbsu.pattern.validator;

import dk.brics.automaton.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergey Afonin on 29.03.2017.
 */
public class BricsRegExpAutomaton implements Validator {

    private ThreadLocal<List<Automaton>> automatons = new ThreadLocal<>();

    public BricsRegExpAutomaton() {
        List<String> patterns = RegExpReader.readPatterns(RegExpReader.getBricsRegExp());
        automatons.set(new ArrayList<>(patterns.size()));
        for (String pattern : patterns) {
            RegExp r = new RegExp(pattern);
            Automaton a = r.toAutomaton(new DatatypesAutomatonProvider(true,true,false));
            automatons.get().add(a);
        }
    }

    @Override
    public boolean validate(String s) {
        for (Automaton automaton : automatons.get()) {
            if (automaton.run(s))
                return true;
        }
        return false;
    }
}
