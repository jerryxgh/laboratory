package com.jerryxgh.sentineldemo.java;

public class Atoi {
    public int myAtoi(String str) {
        boolean minus = false;
        boolean needNumber = false;
        long value = 0;
        for (char c : str.toCharArray()) {
            if (c >= '0' && c <= '9') {
                needNumber = true;
                value = value * 10 + (c - '0');
                if (value > Integer.MAX_VALUE) {
                    break;
                }
                continue;
            }

            if (needNumber) {
                break;
            } else {
                if (' ' == c) {
                    continue;
                } else if ('-' == c) {
                    minus = true;
                    needNumber = true;
                } else if ('+' == c) {
                    minus = false;
                    needNumber = true;
                } else {
                    break;
                }
            }
        }


        if (minus) {
            if (-value < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            } else {
                return -(int)value;
            }
        } else {
            if (value > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            } {
                return (int)value;
            }
        }
    }
}

