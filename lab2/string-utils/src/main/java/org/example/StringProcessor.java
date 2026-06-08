package org.example;

import org.apache.commons.lang3.StringUtils;

public class StringProcessor {
    public static String reverse(String input) {
        if (input == null) return null;
        return StringUtils.reverse(input);
    }

    public static String capitalize(String input) {
        if (input == null) return null;
        return StringUtils.capitalize(input);
    }
}