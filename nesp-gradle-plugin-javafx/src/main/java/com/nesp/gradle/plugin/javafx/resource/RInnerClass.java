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

import java.util.Map;

public class RInnerClass {
    private String name;
    private Map<Object, Object>  fields;
    private String jDoc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Object, Object> getFields() {
        return fields;
    }

    public void setFields(Map<Object, Object> fields) {
        this.fields = fields;
    }

    public String getjDoc() {
        return jDoc;
    }

    public void setjDoc(String jDoc) {
        this.jDoc = jDoc;
    }
}
