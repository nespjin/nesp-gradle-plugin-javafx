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

import com.nesp.gradle.plugin.javafx.BaseTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public abstract class GenerateRClassFileTask extends BaseTask {

    @TaskAction
    public void run() {
        final Project project = getProject();
        final File desFile = getSourcePathGenerate();
        final String packageName = getPackageName();
        final ClassLoader classLoader = getClassLoader();

        List<RInnerClass> rInnerClasses = new ArrayList<>();

        Properties properties = new Properties();
        InputStream resourceAsStream = classLoader.getResourceAsStream("values/strings/strings.properties");
        try {
            properties.load(resourceAsStream);
            System.out.println("Properties size = " + properties.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (properties.size() > 0) {
            RInnerClass rInnerClass = new RInnerClass();
            rInnerClass.setName(ResourceType.STRING.name);
            Map<Object, Object> fields = new HashMap<>();
            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                fields.put(entry.getKey(), entry.getValue());
            }
            rInnerClass.setFields(fields);
            rInnerClasses.add(rInnerClass);
        }

        new RClassGenerate(desFile, packageName, rInnerClasses).generate();
    }

}
