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
 * Time: Created 2021/8/4 23:36
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public final class ClassMethod {

    private static final String TAG = "ClassMethod";

    private String name;
    private Modifier modifier;
    private String returnType;
    // 2.[type. name]
    private String[][] params;

    public String getName() {
        return name;
    }

    public ClassMethod setName(final String name) {
        this.name = name;
        return this;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public ClassMethod setModifier(final Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public String getReturnType() {
        return returnType;
    }

    public ClassMethod setReturnType(final String returnType) {
        this.returnType = returnType;
        return this;
    }

    public String[][] getParams() {
        return params;
    }

    public ClassMethod setParams(final String[][] params) {
        this.params = params;
        return this;
    }
}
