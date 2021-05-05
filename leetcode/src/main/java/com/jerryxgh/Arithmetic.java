package com.jerryxgh;

public class Arithmetic {
    public static void main(String[] args) {
        System.out.println("abc");
        Arithmetic arithmetic = new Arithmetic();
        System.out.println(arithmetic.compute("(9/1)*2+(1+1)*4+(1+1+1)3*4"));
        arithmetic.index = 0;
        System.out.println(arithmetic.compute("((1+4)*(5+1)+1)*2"));
        System.out.println(arithmetic.compute("1+2*4"));
    }

    private int index = 0;

    public int compute(String exp) {
        return exp(exp);
    }

    private int exp(String exp) {
        int value = 0;
        int sign = 1;
        while (true) {
            if (index >= exp.length()) {
                return value;
            }

            char current = exp.charAt(index);
            if (current == '+') {
                index++;
                sign = 1;
                continue;
            } else if (current == '-') {
                index++;
                sign = -1;
                continue;
            } else if (current == '(' || isNumber(current)) {
                int currentValue = term(exp);
                if (sign > 0) {
                    value += currentValue;
                } else {
                    value -= currentValue;
                }
            } else {
                return value;
            }
        }
    }

    private boolean isNumber(char c) {
        return c>= '0' && c<= '9';
    }

    private int term(String exp) {
        int value = 1;
        int sign = 1;
        while (true) {
            if (index >= exp.length()) {
                return value;
            }
            char current = exp.charAt(index);
            if (isNumber(current)) {
                index++;
                int currentValue = current - '0';
                if (sign > 0) {
                    value *= currentValue;
                } else {
                    value /= currentValue;
                }
            } else if (current == '*') {
                index++;
                sign = 1;
            } else if (current == '/') {
                index++;
                sign = -1;
            } else if (current == '+' || current == '-') {
                return value;
            } else if (current == '(') {
                index++;
                int expValue = exp(exp);
                if (sign > 0) {
                    value *= expValue;
                } else {
                    value /= expValue;
                }
                index++;
            } else {
                return value;
                // 错误处理
            }
        }
    }
}
