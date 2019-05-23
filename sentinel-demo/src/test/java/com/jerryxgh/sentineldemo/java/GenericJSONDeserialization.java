package com.jerryxgh.sentineldemo.java;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.List;

public class GenericJSONDeserialization {
    static class GenericA<T> {
        private T a;
        public GenericA() {
        }

        public GenericA(T a) {
            this.a = a;
        }

        public T getA() {
            return a;
        }

        public void setA(T a) {
            this.a = a;
        }
    }

    static class GenericB extends GenericA<Long> {
        public GenericB() {
        }
        public GenericB(Long a) {
            super(a);
        }
    }

    @Test
    public void testGSON() {
        Gson gson = new Gson();

        System.out.println(Integer.MAX_VALUE);

        GenericA genericA = new GenericB();
        genericA.setA("abc");
        System.out.println(genericA.getA());

        genericA = new GenericA<>(10101001L);
        String jsonOfGenericA = JSON.toJSONString(genericA);
        System.out.println(JSON.toJSONString(genericA));
        System.out.println(gson.toJson(genericA));

        System.out.println("=============================");

        System.out.println(JSON.toJSONString(gson.fromJson(jsonOfGenericA, GenericA.class)) + ":" + gson.fromJson(jsonOfGenericA, GenericA.class).getA().getClass());
        System.out.println(JSON.toJSONString(gson.fromJson(jsonOfGenericA, GenericB.class)) + ":" + gson.fromJson(jsonOfGenericA, GenericB.class).getA().getClass());
        System.out.println(JSON.toJSONString(JSON.parseObject(jsonOfGenericA, GenericA.class)) + ":" + JSON.parseObject(jsonOfGenericA, GenericA.class).getA().getClass());
        System.out.println(JSON.toJSONString(JSON.parseObject(jsonOfGenericA, GenericB.class)) + ":" + JSON.parseObject(jsonOfGenericA, GenericB.class).getA().getClass());
        ;

        String s = "[2147483648, 2147483647, 5.6]";
        List<Object> arr = JSON.parseArray(s);
        JSON.parseArray(s).forEach(o -> { System.out.println(o.getClass()); });
        gson.fromJson(s, List.class).forEach(o -> { System.out.println(o.getClass()); });
        System.out.println("================================");
        JSON.parseObject(s, new TypeReference<List<Long>>(){}).forEach(o -> { System.out.println(o.getClass()); });
        new Gson().<List<Long>>fromJson(s, new TypeToken<List<Long>>(){}.getType()).forEach(o -> { System.out.println(o.getClass()); });
//        new Gson().fromJson()
    }
}
