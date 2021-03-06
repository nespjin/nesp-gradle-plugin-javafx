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

import com.nesp.gradle.plugin.javafx.defaultconfig.DefaultConfig;
import com.nesp.gradle.plugin.javafx.fxml.BaseControllerOptions;
import com.nesp.gradle.plugin.javafx.resource.ResourceConfig;
import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.util.internal.ConfigureUtil;

public class NespJavaFxPluginExtension {
    private Boolean mViewBinding = null;

    BaseControllerOptions mBaseControllerOptions;
    ResourceConfig mResourceConfig;
    DefaultConfig mDefaultConfig;

    public Boolean getViewBinding() {
        return mViewBinding != null && mViewBinding;
    }

    public void setViewBinding(Boolean viewBinding) {
        this.mViewBinding = viewBinding;
    }

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

    public ResourceConfig resourceConfig(Closure<? super ResourceConfig> closure) {
        return resourceConfig(ConfigureUtil.configureUsing(closure));
    }

    public ResourceConfig resourceConfig(Action<? super ResourceConfig> action) {
        if (mResourceConfig == null) {
            mResourceConfig = new ResourceConfig();
        }
        action.execute(mResourceConfig);
        return mResourceConfig;
    }

    public ResourceConfig resourceConfig() {
        return mResourceConfig;
    }

    public DefaultConfig defaultConfig(Closure<? super DefaultConfig> closure) {
        return defaultConfig(ConfigureUtil.configureUsing(closure));
    }

    public DefaultConfig defaultConfig(Action<? super DefaultConfig> action) {
        if (mDefaultConfig == null) {
            mDefaultConfig = new DefaultConfig();
        }
        action.execute(mDefaultConfig);
        return mDefaultConfig;
    }

    public DefaultConfig defaultConfig() {
        return mDefaultConfig;
    }


}
