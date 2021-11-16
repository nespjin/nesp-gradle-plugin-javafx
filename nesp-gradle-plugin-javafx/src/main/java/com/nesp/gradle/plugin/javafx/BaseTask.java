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

import com.nesp.gradle.plugin.javafx.utils.ProjectUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;

import java.io.File;

public abstract class BaseTask extends DefaultTask {
    private final File sourcePathGenerate;
    private final String packageName;
    private final ClassLoader classLoader;

    public BaseTask() {
        Project project = getProject();
        sourcePathGenerate = new File(ProjectUtils.getGenerateSourcePath(project));
        final String classLoaderPropertyName = "classLoader";

        if (project.getExtensions().getExtraProperties().has(classLoaderPropertyName)
                && project.getExtensions().getExtraProperties().get(classLoaderPropertyName) != null) {
            this.classLoader = (ClassLoader) project.getExtensions().getExtraProperties().get(classLoaderPropertyName);
        } else {
            this.classLoader = ProjectUtils.createClassLoader(project);
            project.getExtensions().getExtraProperties().set(classLoaderPropertyName, this.classLoader);
        }

        final String packageNamePropertyName = "packageName";
        if (project.getExtensions().getExtraProperties().has(packageNamePropertyName)
                && project.getExtensions().getExtraProperties().get(packageNamePropertyName) != null) {
            this.packageName =
                    (String) project.getExtensions().getExtraProperties().get(packageNamePropertyName);
        } else {
            this.packageName = ProjectUtils.findPackageName(project);
            project.getExtensions().getExtraProperties().set(packageNamePropertyName, this.packageName);
        }
    }

    public File getSourcePathGenerate() {
        return sourcePathGenerate;
    }

    public String getPackageName() {
        return packageName;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
