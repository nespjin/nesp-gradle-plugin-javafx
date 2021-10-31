/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nesp.gradle.plugin.javafx.fxml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.BasicPermission;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 22:39
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public class FXMLParser {

    private static final String TAG = "FXMLParser";

    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    public static final String LANGUAGE_PROCESSING_INSTRUCTION = "language";
    public static final String IMPORT_PROCESSING_INSTRUCTION = "import";
    public static final String COMPILE_PROCESSING_INSTRUCTION = "compile";
    public static final String FX_NAMESPACE_PREFIX = "fx";
    public static final String FX_CONTROLLER_ATTRIBUTE = "controller";
    public static final String FX_ID_ATTRIBUTE = "id";
    public static final String FX_VALUE_ATTRIBUTE = "value";
    public static final String ROOT_TAG = "root";
    public static final String CONTROLLER_KEYWORD = "controller";
    public static final String CONTROLLER_SUFFIX = "Controller";
    public static final String INITIALIZE_METHOD_NAME = "initialize";

    protected XMLStreamReader mXMLStreamReader = null;
    protected final List<String> mPackages = new LinkedList<>();
    protected Map<String, Class<?>> mClasses = new HashMap<>();
    protected Map<String, String> mClassesInfo = new HashMap<>();
    protected ClassLoader mClassLoader = null;
    protected static ClassLoader sDefaultClassLoader = null;

    // Instance of StackWalker used to get caller class (must be private)
//    private static final StackWalker WALKER =
//            AccessController.doPrivileged((PrivilegedAction<StackWalker>) () ->
//                    StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE));
    private static final StackWalker WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    protected File mFXMLFile;

    public void parse(File fxmlFile) throws FXMLParseException {
        this.mFXMLFile = fxmlFile;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fxmlFile);
            parse(inputStream);
        } catch (FileNotFoundException e) {
            throw constructParseException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void parse(URL url) throws FXMLParseException {
        try {
            this.mFXMLFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw constructParseException(e);
        }
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            parse(inputStream);
        } catch (IOException e) {
            throw constructParseException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void parse(InputStream inputStream) throws FXMLParseException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }

        try {
            final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            xmlInputFactory.setProperty("javax.xml.stream.isCoalescing", true);

            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            mXMLStreamReader = new StreamReaderDelegate(xmlInputFactory.createXMLStreamReader(inputStreamReader)) {
                @Override
                public String getPrefix() {
                    String prefix = super.getPrefix();
                    if (prefix != null
                            && prefix.length() == 0) {
                        prefix = null;
                    }
                    return prefix;
                }

                @Override
                public String getAttributePrefix(final int index) {
                    String attributePrefix = super.getAttributePrefix(index);
                    if (attributePrefix != null
                            && attributePrefix.length() == 0) {
                        attributePrefix = null;
                    }
                    return attributePrefix;
                }
            };
        } catch (XMLStreamException e) {
            throw constructParseException(e);
        }

        // Parse the XML stream
        try {
            onPreParse();
            while (mXMLStreamReader.hasNext()) {
                final int event = mXMLStreamReader.next();
                switch (event) {
                    case XMLStreamConstants.PROCESSING_INSTRUCTION -> processProcessingInstruction();
                    case XMLStreamConstants.COMMENT -> processComment();
                    case XMLStreamConstants.START_ELEMENT -> processStartElement();
                    case XMLStreamConstants.END_ELEMENT -> processEndElement();
                    case XMLStreamConstants.CHARACTERS -> processCharacters();
                }
            }
            onPostParse();
        } catch (XMLStreamException e) {
            throw constructParseException(e);
        }
    }

    protected void onPreParse() {

    }

    protected void onPostParse() {

    }

    protected void processComment() {
        /* do nothing */
    }

    protected void processStartElement() {

    }

    protected void processEndElement() {
        /* do nothing */
    }

    protected void processCharacters() {
        /* do nothing */
    }


    private void processProcessingInstruction() throws FXMLParseException {
        String piTarget = mXMLStreamReader.getPITarget().trim();

        switch (piTarget) {
            case LANGUAGE_PROCESSING_INSTRUCTION:
            case COMPILE_PROCESSING_INSTRUCTION:
                /* do nothing */
                break;
            case IMPORT_PROCESSING_INSTRUCTION:
                processImport();
                break;
        }
    }

    private void processImport() throws FXMLParseException {
        String target = mXMLStreamReader.getPIData().trim();

        if (target.endsWith(".*")) {
            importPackage(target.substring(0, target.length() - 2));
        } else {
            importClass(target);
        }
    }

    private void importPackage(String name) {
        mPackages.add(name);
    }

    private void importClass(String name) throws FXMLParseException {
        try {
            loadType(name, true);
        } catch (ClassNotFoundException exception) {
            throw constructParseException(exception);
        }
    }

    public Class<?> getType(String name) {
        Class<?> type = null;

        if (Character.isLowerCase(name.charAt(0))) {
            // This is a fully-qualified class name
            try {
                type = loadType(name, false);
            } catch (ClassNotFoundException exception) {
                // No-op
            }
        } else {
            // This is an unqualified class name
            type = mClasses.get(name);

            if (type == null) {
                // The class has not been loaded yet; look it up
                for (String packageName : mPackages) {
                    try {
                        type = loadTypeForPackage(packageName, name);
                    } catch (ClassNotFoundException exception) {
                        // No-op
                    }

                    if (type != null) {
                        break;
                    }
                }

                if (type != null) {
                    mClasses.put(name, type);
                }
            }
        }

        return type;
    }

    private Class<?> loadType(String name, boolean cache) throws ClassNotFoundException {
        int i = name.indexOf('.');
        int n = name.length();
        while (i != -1
                && i < n
                && Character.isLowerCase(name.charAt(i + 1))) {
            i = name.indexOf('.', i + 1);
        }

        if (i == -1 || i == n) {
            throw new ClassNotFoundException();
        }

        String packageName = name.substring(0, i);
        String className = name.substring(i + 1);

        Class<?> type = loadTypeForPackage(packageName, className);

        if (cache) {
            mClassesInfo.put(className, packageName);
            mClasses.put(className, type);
        }

        return type;
    }

    // TODO Rename to loadType() when deprecated static version is removed
    private Class<?> loadTypeForPackage(String packageName, String className) throws ClassNotFoundException {
        return getClassLoader().loadClass(packageName + "." + className.replace('.', '$'));
    }


    private static boolean needsClassLoaderPermissionCheck(Class<?> caller) {
        if (caller == null) {
            return false;
        }
        return !FXMLParser.class.getModule().equals(caller.getModule());
    }

    private static ClassLoader getDefaultClassLoader(Class<?> caller) {
        if (sDefaultClassLoader == null) {
//            final SecurityManager sm = System.getSecurityManager();
//            if (sm != null) {
//                if (needsClassLoaderPermissionCheck(caller)) {
//                    sm.checkPermission(new Permission(("modifyFXMLClassLoader")));
//                }
//            }
            return Thread.currentThread().getContextClassLoader();
        }
        return sDefaultClassLoader;
    }

    public static class Permission extends BasicPermission {
        public Permission(String name) {
            super(name);
        }
    }

    public static ClassLoader getDefaultClassLoader() {
//        final SecurityManager sm = System.getSecurityManager();
//        final Class caller = (sm != null) ?
//                WALKER.getCallerClass() :
//                null;
        return getDefaultClassLoader(WALKER.getCallerClass());
    }

    public ClassLoader getClassLoader() {
        if (mClassLoader == null) {
//            final SecurityManager sm = System.getSecurityManager();
//            final Class caller = (sm != null) ?
//                    WALKER.getCallerClass() :
//                    null;
            return getDefaultClassLoader(WALKER.getCallerClass());
        }
        return mClassLoader;
    }


    public void setClassLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            throw new IllegalArgumentException();
        }

        this.mClassLoader = classLoader;

        clearImports();
    }

    private void clearImports() {
        mPackages.clear();
        mClasses.clear();
    }

    public File getFXMLFile() {
        return mFXMLFile;
    }

    public List<String> getPackages() {
        return mPackages;
    }

    public Map<String, Class<?>> getClasses() {
        return mClasses;
    }

    public Map<String, String> getClassesInfo() {
        return mClassesInfo;
    }

    private FXMLParseException constructParseException(Throwable cause) {
        return new FXMLParseException(constructFXMLTrace(), cause);
    }

    private String constructFXMLTrace() {
        return "\n" + (mFXMLFile != null ? mFXMLFile.getPath() : "unknown path") +
                ":" +
                mXMLStreamReader.getLocation().getLineNumber();
    }
}
