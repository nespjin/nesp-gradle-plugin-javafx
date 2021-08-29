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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class StyleInnerResourceClassLoader {
    private static final String STYLE_RES_FILE_EXTENSION = ".css";

    private StyleInnerResourceClassLoader() {
    }

    static void loadFromDirPaths(ClassLoader classLoader,
                                 List<RInnerClass> rInnerClasses,
                                 List<String> resourceDirPaths) {
        RInnerClass rInnerClass = new RInnerClass();
        rInnerClass.setName(ResourceType.STYLE.name);
        rInnerClass.setjDoc("This is the style resource");
        Map<Object, Object> fields = new HashMap<>();

        for (String resourcePath : resourceDirPaths) {
            loadFromDirPath(classLoader, fields, resourcePath);
        }
        rInnerClass.setFields(fields);
        rInnerClasses.add(rInnerClass);
    }

    private static void loadFromDirPath(ClassLoader classLoader,
                                        Map<Object, Object> fields,
                                        String resourceDirPath) {
        File dir = new File(resourceDirPath);
        File[] files = dir.listFiles();

        if (ArrayUtils.isEmpty(files)) return;

        for (File file : files) {
            if (file.isFile() && file.getAbsolutePath().endsWith(STYLE_RES_FILE_EXTENSION)) {
                String name = file.getName();
                if (name.endsWith(STYLE_RES_FILE_EXTENSION)) {
                    name = name.substring(0, name.lastIndexOf(STYLE_RES_FILE_EXTENSION));
                }
                if (!fields.containsKey(name)) {
                    fields.put(name, name);
                }
            } else {
                loadFromDirPath(classLoader, fields, resourceDirPath);
            }
        }
    }

}
