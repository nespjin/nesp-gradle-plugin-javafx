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

import com.nesp.gradle.plugin.javafx.fxml.BaseControllerOptions;
import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.util.internal.ConfigureUtil;

public class NespJavaFxPluginExtension {
    BaseControllerOptions mBaseControllerOptions;

    public BaseControllerOptions baseControllerOptions(Closure<? super BaseControllerOptions> closure) {
        return baseControllerOptions(ConfigureUtil.configureUsing(closure));
    }

    public BaseControllerOptions baseControllerOptions(Action<? super BaseControllerOptions> action) {
        if (mBaseControllerOptions == null) {
            mBaseControllerOptions = new BaseControllerOptions();
        }
        action.execute(mBaseControllerOptions);
        return mBaseControllerOptions;
    }

    public BaseControllerOptions baseControllerOptions() {
        return mBaseControllerOptions;
    }
}
