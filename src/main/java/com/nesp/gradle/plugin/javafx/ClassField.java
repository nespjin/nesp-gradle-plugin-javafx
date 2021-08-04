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

package com.nesp.gradle.plugin.javafx;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 22:00
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public final class ClassField {

    private static final String TAG = "ClassField";

    private String type;
    private String name;

    public ClassField(final String type, final String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ClassField setName(final String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public ClassField setType(final String type) {
        this.type = type;
        return this;
    }
}
