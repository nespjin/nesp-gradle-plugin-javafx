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

package com.nesp.gradle.plugin.javafx.utils;

import java.util.List;

public final class ListUtils {

    private ListUtils() {
    }

    public static <T> List<T> ifEmpty(List<T> list, List<T> defaultValue) {
        return (list == null || list.isEmpty()) ? defaultValue : list;
    }

    @SafeVarargs
    public static <T> List<T> ifEmpty(List<T> list, T... values) {
        return (list == null || list.isEmpty()) ? List.of(values) : list;
    }

}
