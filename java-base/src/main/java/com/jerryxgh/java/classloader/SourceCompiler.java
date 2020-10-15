/*
 * Copyright 2004-2020 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (https://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package com.jerryxgh.java.classloader;

import javax.tools.*;
import javax.tools.JavaFileObject.Kind;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class allows to convert source code to a class. It uses one class loader
 * per class.
 */
public class SourceCompiler {
//    ClassLoader classLoader = new ClassLoader(getClass().getClassLoader()) {
//        byte[] data = null;
//
//        @Override
//        public Class<?> findClass(String name) throws ClassNotFoundException {
//            return defineClass(name, data, 0, data.length);
//        }
//    };

    /**
     * Compile using the standard java compiler.
     *
     * @param stringJavaFileObjectList 字符串形式的Java源文件列表
     * @return the class
     */
    public static Map<String, JavaClassObject> javaxToolsJavac(List<StringJavaFileObject> stringJavaFileObjectList) throws ClassNotFoundException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StringWriter writer = new StringWriter();
        ClassFileManager fileManager = new ClassFileManager(javaCompiler.getStandardFileManager(null, null, null));

        ArrayList<JavaFileObject> compilationUnits = new ArrayList<>();
        compilationUnits.addAll(stringJavaFileObjectList);
        Boolean compileSucc = javaCompiler.getTask(writer, fileManager, null, null,
                    null, compilationUnits).call();
        String output = writer.toString();
//        System.out.println(compileSucc + "," + output);
//        return fileManager.getClassLoader(null).loadClass(fullClassName);

        return fileManager.getClassObjects();
    }

    /**
     * An in-memory java source file object.
     */
    public static class StringJavaFileObject extends SimpleJavaFileObject {

        private final String sourceCode;

        public StringJavaFileObject(String className, String sourceCode) {
            super(URI.create("string:///" + className.replace('.', '/')
                    + Kind.SOURCE.extension), Kind.SOURCE);
            this.sourceCode = sourceCode;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCode;
        }
    }

    /**
     * An in-memory java class object.
     */
    public static class JavaClassObject extends SimpleJavaFileObject {
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();

        public JavaClassObject(String name, Kind kind) {
            super(URI.create("string:///" + name.replace('.', '/')
                    + kind.extension), kind);
        }

        public byte[] getBytes() {
            return out.toByteArray();
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return out;
        }
    }

    /**
     * An in-memory class file manager.
     */
    static class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
        /**
         * We use map because there can be nested, anonymous etc classes.
         */
        private Map<String, JavaClassObject> classObjectsByName = new HashMap<>();

        public ClassFileManager(StandardJavaFileManager standardManager) {
            super(standardManager);
        }

        public JavaClassObject getJavaClassObject(String name) {
            return classObjectsByName.get(name);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) {
            JavaClassObject classObject = new JavaClassObject(className, kind);
            classObjectsByName.put(className, classObject);
            return classObject;
        }

        public Map<String, JavaClassObject> getClassObjects() {
            return classObjectsByName;
        }
    }
}
