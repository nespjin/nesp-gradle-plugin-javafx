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

package com.nesp.gradle.plugin.javafx.reflect;

import javax.lang.model.element.Name;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/1 上午10:43
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public class SimpleName implements Name {

    private static final String TAG = "MethodName";
    private final String mName;

    public SimpleName(final String name) {
        mName = name;
    }

    @Override
    public boolean contentEquals(final CharSequence cs) {
        return mName.contentEquals(cs);
    }

    @Override
    public int length() {
        return mName.length();
    }

    @Override
    public char charAt(final int index) {
        return mName.charAt(index);
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        return mName.subSequence(start, end);
    }

    @Override
    public String toString() {
        return mName;
    }
}
