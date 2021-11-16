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

package com.nesp.gradle.plugin.javafx.defaultconfig;

import org.gradle.api.tasks.Input;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/16 上午8:47
 * Description:
 **/
public class DefaultConfig {

    private String versionName;
    private Integer versionCode;

    public static DefaultConfig getDefault() {
        final DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig.setVersionCode(1);
        defaultConfig.setVersionName("1.0");
        return defaultConfig;
    }

    @Input
    public Integer getVersionCode() {
        return versionCode;
    }

    public DefaultConfig setVersionCode(final Integer versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    @Input
    public String getVersionName() {
        return versionName;
    }

    public DefaultConfig setVersionName(final String versionName) {
        this.versionName = versionName;
        return this;
    }

}
