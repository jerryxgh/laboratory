package com.jerryxgh.sentineldemo.java;

import org.junit.Test;

public class JavaTest {
    @Test
    public void test1() {
        System.out.println(Integer.numberOfLeadingZeros(128) | (1 << (16 - 1)));
    }
}
