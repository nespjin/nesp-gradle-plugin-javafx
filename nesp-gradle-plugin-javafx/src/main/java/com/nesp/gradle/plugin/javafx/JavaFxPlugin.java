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

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;
import java.util.Optional;
import java.util.Set;

public class JavaFxPlugin implements Plugin<Project> {

    public static final String NESP_JAVA_FX_PLUGIN_EXTENSION_NAME = "nespJfx";
    public static final String GENERATE_BASE_CONTROLLER_FILE_TASK_NAME = "generateJavaFxBaseControllerFile";

    @Override
    public void apply(Project project) {
        configureSourceSetGenerateFileOutputs(project);
        configureNespJavaFxPluginExtension(project);
        configureGenerateBaseControllerFileTask(project);
    }

    /**
     * Add Java files generated to sourceSets
     *
     * @param project project
     */
    private void configureSourceSetGenerateFileOutputs(Project project) {
        project.afterEvaluate(project1 -> {
            if (project1.getPlugins().hasPlugin(JavaPlugin.class)) {
                final File desFile = new File(GenerateBaseControllerFileTask.getGenerateSourcePath(project));

                JavaPluginExtension javaPluginExtension =
                        project1.getExtensions().findByType(JavaPluginExtension.class);
                if (javaPluginExtension != null) {
                    Set<File> mainSrcDirs = javaPluginExtension
                            .getSourceSets()
                            .getByName("main")
                            .getJava()
                            .getSrcDirs();

                    mainSrcDirs.add(desFile);

                    javaPluginExtension
                            .getSourceSets()
                            .getByName("main")
                            .getJava()
                            .setSrcDirs(mainSrcDirs);

                }
            }
        });
    }

    private void configureNespJavaFxPluginExtension(Project project) {
        project.getExtensions().add(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME, NespJavaFxPluginExtension.class);
    }

    private void configureGenerateBaseControllerFileTask(Project project) {
        project.afterEvaluate(project1 -> {
            NespJavaFxPluginExtension nespJfx =
                    (NespJavaFxPluginExtension) project1.getExtensions()
                            .findByName(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME);
            if (nespJfx == null) return;

            Boolean baseControllerEnable = Optional.ofNullable(nespJfx.getBaseController()).orElse(false);

            GenerateBaseControllerFileTask generateBaseControllerFileTask = project1.getTasks()
                    .create(GENERATE_BASE_CONTROLLER_FILE_TASK_NAME, GenerateBaseControllerFileTask.class);

            Set<Task> compileJavaTasks = project1.getTasksByName("compileJava", true);
            if (!compileJavaTasks.isEmpty()) {
                JavaCompile compileJavaTask = (JavaCompile) compileJavaTasks.toArray(new Object[0])[0];
                compileJavaTask.doFirst(task1 -> {
                    if (baseControllerEnable) {
                        generateBaseControllerFileTask.run();
                    }
                });
            }
        });
    }

}
