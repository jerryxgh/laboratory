package com.jerryxgh.java.classloader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaClassLoaderExperiment {
    public static class L1 extends ClassLoader {
        private Map<String, SourceCompiler.JavaClassObject> javaClassObjectList = null;

        public L1(Map<String, SourceCompiler.JavaClassObject> javaClassObjectList, ClassLoader parent) {
            super(parent);
            this.javaClassObjectList = javaClassObjectList;
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
//            System.out.println("L1 load class:" + name);
            if ("com.jerryxgh.java.classloader.Spoofed".equals(name) || "com.jerryxgh.java.classloader.C".equals(name)) {
                return findClass(name);
            }

            return super.loadClass(name);
        }

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] data = javaClassObjectList.get(name).getBytes();
            return defineClass(name, data, 0, data.length);
        }
    }


    public static class L2 extends ClassLoader {
        private Map<String, SourceCompiler.JavaClassObject> javaClassObjectList = null;

        public L2(Map<String, SourceCompiler.JavaClassObject> javaClassObjectList) {
            this.javaClassObjectList = javaClassObjectList;
        }

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] data = javaClassObjectList.get(name).getBytes();
            return defineClass(name, data, 0, data.length);
        }
    }

    public static String inputStreamToString(URL url) throws IOException {
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream())) {
                while ((length = bufferedInputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
            }

            return result.toString("UTF-8");
        }
    }

    public static void main(String[] args) throws Exception {
        List<SourceCompiler.StringJavaFileObject> stringJavaFileObjectList = new ArrayList<>();
        stringJavaFileObjectList.add(new SourceCompiler.StringJavaFileObject("com.jerryxgh.java.classloader.C", inputStreamToString(JavaClassLoaderExperiment.class.getClassLoader().getResource("C.txt"))));
        stringJavaFileObjectList.add(new SourceCompiler.StringJavaFileObject("com.jerryxgh.java.classloader.Delegated", inputStreamToString(JavaClassLoaderExperiment.class.getClassLoader().getResource("Delegated.txt"))));
        stringJavaFileObjectList.add(new SourceCompiler.StringJavaFileObject("com.jerryxgh.java.classloader.Spoofed", inputStreamToString(JavaClassLoaderExperiment.class.getClassLoader().getResource("Spoofed.txt"))));

        Map<String, SourceCompiler.JavaClassObject> javaClassObjectList = SourceCompiler.javaxToolsJavac(stringJavaFileObjectList);
        L2 l2 = new L2(javaClassObjectList);
        L1 l1 = new L1(javaClassObjectList, l2);

        l1.loadClass("com.jerryxgh.java.classloader.Spoofed").newInstance();

        Class classDelegated = l1.loadClass("com.jerryxgh.java.classloader.Delegated");
        Object objDelegated = classDelegated.newInstance();
        classDelegated.getMethod("g").invoke(objDelegated);

        Class classC = l1.loadClass("com.jerryxgh.java.classloader.C");
//        System.out.println(classC.getSimpleName());
//        System.out.println(classC.getTypeName());
        Object c = classC.newInstance();
        classC.getMethod("f");
//        classC.getMethod("f").invoke(c);
//        new C().f();
//        new C().f();
//        System.out.println(C.class.getClassLoader());
    }
}