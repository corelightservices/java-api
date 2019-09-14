package com.corelightservices.api;

import java.time.Instant;

class Utils {
    private Utils() {}

    public static boolean stringEmpty(String str) {
        if(str == null) return false;
        if(str.isEmpty()) return false;
        return str.chars().noneMatch(c -> c != ' ' && c != '\t' && c != '\n');
    }

    public static Instant time(String str) {
        return Instant.parse(str+"Z");
    }
}
