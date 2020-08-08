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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class allows to convert source code to a class. It uses one class loader
 * per class.
 */
public class SourceCompiler {
    ClassLoader classLoader = new ClassLoader(getClass().getClassLoader()) {
        byte[] data = null;

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            return defineClass(name, data, 0, data.length);
        }
    };

    public Method getMethod(Class<?> clazz) throws ClassNotFoundException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            int modifiers = m.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                String name = m.getName();
                if (!name.startsWith("_") && !m.getName().equals("main")) {
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * Compile using the standard java compiler.
     *
     * @param packageName the package name
     * @param className the class name
     * @param source the source code
     * @return the class
     */
    Class<?> javaxToolsJavac(String packageName, String className, String source) throws ClassNotFoundException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        String fullClassName = packageName + "." + className;
        StringWriter writer = new StringWriter();
        JavaFileManager fileManager = new ClassFileManager(javaCompiler.getStandardFileManager(null, null, null));

        ArrayList<JavaFileObject> compilationUnits = new ArrayList<>();
        compilationUnits.add(new StringJavaFileObject(fullClassName, source));
        javaCompiler.getTask(writer, fileManager, null, null,
                    null, compilationUnits).call();
        String output = writer.toString();
        return fileManager.getClassLoader(null).loadClass(fullClassName);
    }

    /**
     * An in-memory java source file object.
     */
    static class StringJavaFileObject extends SimpleJavaFileObject {

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
    static class JavaClassObject extends SimpleJavaFileObject {

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
        Map<String, JavaClassObject> classObjectsByName = new HashMap<>();

        private SecureClassLoader classLoader = new SecureClassLoader() {

            @Override
            protected Class<?> findClass(String name)
                    throws ClassNotFoundException {
                byte[] bytes = classObjectsByName.get(name).getBytes();
                return super.defineClass(name, bytes, 0,
                        bytes.length);
            }
        };

        public ClassFileManager(StandardJavaFileManager standardManager) {
            super(standardManager);
        }

        @Override
        public ClassLoader getClassLoader(Location location) {
            return this.classLoader;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location,
                                                   String className, Kind kind, FileObject sibling) throws IOException {
            JavaClassObject classObject = new JavaClassObject(className, kind);
            classObjectsByName.put(className, classObject);
            return classObject;
        }
    }
}
