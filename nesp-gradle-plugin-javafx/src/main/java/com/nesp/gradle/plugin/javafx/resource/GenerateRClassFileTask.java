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

import com.nesp.gradle.plugin.javafx.BaseTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.eclipse.jgit.annotations.NonNull;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenerateRClassFileTask extends BaseTask {

    @NonNull
    private final ResourceConfig mResourceConfig;

    @Inject
    public GenerateRClassFileTask(ResourceConfig resourceConfig) {
        mResourceConfig = Optional.ofNullable(resourceConfig).orElse(ResourceConfig.getDefault());
    }

    @TaskAction
    public void run() {
        final Project project = getProject();
        final File desFile = getSourcePathGenerate();
        final String packageName = getPackageName();
        final ClassLoader classLoader = getClassLoader();

        List<RInnerClass> rInnerClasses = new ArrayList<>();

        StringInnerResourceClassLoader.loadFromDirPaths(classLoader, rInnerClasses,
                mResourceConfig.getStringSrcDirs(project));

        LayoutInnerResourceClassLoader.loadFromDirPaths(classLoader, rInnerClasses,
                mResourceConfig.getLayoutSrcDirs(project));

        StyleInnerResourceClassLoader.loadFromDirPaths(classLoader, rInnerClasses,
                mResourceConfig.getStyleSrcDirs(project));

        new RClassGenerate(desFile, packageName, rInnerClasses).generate();
    }


}
