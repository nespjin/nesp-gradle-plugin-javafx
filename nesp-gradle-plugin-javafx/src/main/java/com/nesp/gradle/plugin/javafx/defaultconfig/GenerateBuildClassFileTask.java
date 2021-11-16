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

import com.nesp.gradle.plugin.javafx.BaseTask;
import com.nesp.gradle.plugin.javafx.JavaFxPlugin;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.eclipse.jgit.annotations.NonNull;

import javax.inject.Inject;
import java.io.File;
import java.util.Optional;

public abstract class GenerateBuildClassFileTask extends BaseTask {

    @NonNull
    private final DefaultConfig mDefaultConfig;

    @Inject
    public GenerateBuildClassFileTask(DefaultConfig defaultConfig) {
        mDefaultConfig = Optional.ofNullable(defaultConfig).orElse(DefaultConfig.getDefault());
    }

    @TaskAction
    public void run() {
        JavaFxPlugin.printLog("GenerateBuildClassFileTask", "run");
        final File desFile = getSourcePathGenerate();
        final String packageName = getPackageName();
        new BuildClassGenerate(desFile, packageName, mDefaultConfig).generate();
    }


}
