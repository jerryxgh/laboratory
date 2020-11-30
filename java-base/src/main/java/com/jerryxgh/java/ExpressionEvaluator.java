package com.jerryxgh.java;

public class ExpressionEvaluator {
    public static void eval(String s) {
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(1);

        }
    }

    private boolean isNum(char c) {
        return c >= '0' && c <= '9' || c == '.';
    }
}
