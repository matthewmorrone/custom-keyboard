package com.custom.keyboard.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class ArrayUtils {

    public static ArrayList<String> unique(ArrayList<String> input) {
        Set<String> set = new LinkedHashSet<>(input);
        input.clear();
        input.addAll(set);
        return input;
    }
}
