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

import java.util.List;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 20:09
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
@SuppressWarnings("UnusedReturnValue")
public final class BaseControllerClass {

    private static final String TAG = "BaseControllerClass";

    private String mPackageName = "";
    private String mClassName = "";
    private List<ClassField> mFields;
    private List<ClassMethod> mClassMethods;

    public String getPackageName() {
        return mPackageName;
    }

    public BaseControllerClass setPackageName(final String packageName) {
        mPackageName = packageName;
        return this;
    }

    public String getClassName() {
        return mClassName;
    }

    public BaseControllerClass setClassName(final String className) {
        mClassName = className;
        return this;
    }

    public List<ClassField> getFields() {
        return mFields;
    }

    public BaseControllerClass setFields(final List<ClassField> fields) {
        mFields = fields;
        return this;
    }

    public List<ClassMethod> getClassMethods() {
        return mClassMethods;
    }

    public BaseControllerClass setClassMethods(final List<ClassMethod> classMethods) {
        mClassMethods = classMethods;
        return this;
    }
}
