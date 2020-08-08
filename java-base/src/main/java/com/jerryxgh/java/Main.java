package com.jerryxgh.java;

import java.util.TreeMap;

public class Main {
    private static class TestKey {

    }
    public static void main(String[] args) {
        TreeMap<TestKey, String> treeMap = new TreeMap<TestKey, String>();
        // 预期抛出类型转换异常
        treeMap.put(new TestKey(), "abc");
        System.out.println("hello");
    }
}
