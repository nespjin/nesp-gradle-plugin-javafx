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

package com.nesp.gradle.plugin.javafx.resource;

import com.nesp.gradle.plugin.javafx.utils.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

final class StringInnerResourceClassLoader {
    private static final String STRING_RES_FILE_EXTENSION = ".properties";

    private StringInnerResourceClassLoader() {
    }

    static void loadFromDirPaths(ClassLoader classLoader,
                                 List<RInnerClass> rInnerClasses,
                                 List<String> resourceDirPaths) {
        for (String resourcePath : resourceDirPaths) {
            loadFromDirPath(classLoader, rInnerClasses, resourcePath);
        }
    }

    private static void loadFromDirPath(ClassLoader classLoader,
                                        List<RInnerClass> rInnerClasses,
                                        String resourceDirPath) {
        File dir = new File(resourceDirPath);
        File[] files = dir.listFiles();

        if (ArrayUtils.isEmpty(files)) return;

        for (File file : files) {
            if (file.isFile() && file.getAbsolutePath().endsWith(STRING_RES_FILE_EXTENSION)) {
                load(classLoader, rInnerClasses, file.getAbsolutePath());
            } else {
                loadFromDirPath(classLoader, rInnerClasses, resourceDirPath);
            }
        }
    }

    private static void load(ClassLoader classLoader,
                             List<RInnerClass> rInnerClasses,
                             String resourceFilePath
    ) {
        if (rInnerClasses.stream().anyMatch(rInnerClass -> rInnerClass.getName().equals(ResourceType.STRING.name))) {
            return;
        }

        Properties properties = new Properties();
        try {
            // "values/strings/strings.properties"
            InputStream fileInputStream = new FileInputStream(resourceFilePath);
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (properties.size() > 0) {
            RInnerClass rInnerClass = new RInnerClass();
            rInnerClass.setName(ResourceType.STRING.name);
            rInnerClass.setjDoc("This is the string resource");
            Map<Object, Object> fields = new HashMap<>();
            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                if (!fields.containsKey(entry.getKey())) {
                    fields.put(entry.getKey(), entry.getValue());
                }
            }
            rInnerClass.setFields(fields);
            rInnerClasses.add(rInnerClass);
        }
    }
}
