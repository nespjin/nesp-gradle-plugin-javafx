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
import com.nesp.gradle.plugin.javafx.defaultconfig.GenerateBuildConfigClassFileTask;
import com.nesp.gradle.plugin.javafx.fxml.BaseControllerOptions;
import com.nesp.gradle.plugin.javafx.fxml.GenerateBaseControllerClassFileTask;
import com.nesp.gradle.plugin.javafx.fxml.GenerateViewBindingClassFileTask;
import com.nesp.gradle.plugin.javafx.resource.GenerateRClassFileTask;
import com.nesp.gradle.plugin.javafx.resource.ResourceConfig;
import com.nesp.gradle.plugin.javafx.utils.ProjectUtils;
import org.apache.tools.ant.util.FileUtils;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.compile.JavaCompile;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JavaFxPlugin implements Plugin<Project> {

    private static final String TAG = "JavaFxPlugin";

    public static final boolean DEBUG = false;

    public static final String NESP_JAVA_FX_PLUGIN_EXTENSION_NAME = "nespJfx";
    public static final String CONFIG_JAVAFX_TASK_NAME = "configJavaFx";
    public static final String GENERATE_BASE_CONTROLLER_FILE_TASK_NAME = "generateJavaFxBaseControllerFile";
    public static final String GENERATE_VIEW_BINDING_FILE_TASK_NAME = "generateJavaFxViewBindingFile";
    public static final String GENERATE_R_FILE_TASK_NAME = "generateJavaFxRFile";
    public static final String GENERATE_BUILD_FILE_TASK_NAME = "generateJavaFxBuildFile";

    public static void printDebugLog(final String tag, final String msg) {
        printLog(true, tag, msg);
    }

    public static void printLog(final String tag, final String msg) {
        printLog(false, tag, msg);
    }

    private static void printLog(final boolean isDebug, final String tag, final String msg) {
        if (isDebug && !DEBUG) return;
        final StringBuilder log = new StringBuilder(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME).append(": ");
        if (tag == null || tag.isEmpty()) {
            log.append(msg);
        } else {
            log.append(tag).append(": ").append(msg);
        }
        System.out.println(log);
    }

    @Override
    public void apply(@Nonnull Project project) {
        preClear(project);
        configureSourceSetGenerateFileOutputs(project);
        configureNespJavaFxPluginExtension(project);
        configureGenerateBuildFileTask(project);
        configureGenerateRFileTask(project);
        configureGenerateBaseControllerFileTask(project);
        configureGenerateViewBindingFileTask(project);
    }


    private void preClear(final Project project) {
        final File desFile = new File(ProjectUtils.getGenerateSourcePath(project));

        // Clear all pre generated
        if (desFile.isFile()) {
            FileUtils.delete(desFile);
        } else {
            deleteDirectory(desFile);
        }
    }

    /**
     * Add Java files generated to sourceSets
     *
     * @param project project
     */
    @SuppressWarnings("all")
    private void configureSourceSetGenerateFileOutputs(Project project) {
        // Do not use lambdas.
        // Using Java lambdas is not supported as task inputs.
        // Please refer to https://docs.gradle.org/7.2/userguide/validation_problems.html#implementation_unknown
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(@Nonnull final Project project1) {
                final ConfigTask configTask =
                        project.getTasks().create(CONFIG_JAVAFX_TASK_NAME, ConfigTask.class);
                configTask.run();
            }
        });
    }

    @SuppressWarnings("unused")
    private void deleteDirectory(final File desFile) {
        final File[] files = desFile.listFiles();
        if (files != null && files.length > 0) {
            for (final File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        printLog(TAG, "file " + file + " delete failed");
                    }
                } else {
                    deleteDirectory(file);
                }
            }
        }
    }

    private void configureNespJavaFxPluginExtension(Project project) {
        project.getExtensions().add(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME, NespJavaFxPluginExtension.class);
    }

    @SuppressWarnings("all")
    private void configureGenerateBuildFileTask(Project project) {
        // Do not use lambdas.
        // Using Java lambdas is not supported as task inputs.
        // Please refer to https://docs.gradle.org/7.2/userguide/validation_problems.html#implementation_unknown
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(@Nonnull final Project project1) {
                NespJavaFxPluginExtension nespJfx =
                        (NespJavaFxPluginExtension) project1.getExtensions()
                                .findByName(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME);
                if (nespJfx == null) return;

                GenerateBuildConfigClassFileTask generateBuildClassFileTask = project1.getTasks().create(
                        GENERATE_BUILD_FILE_TASK_NAME,
                        GenerateBuildConfigClassFileTask.class,
                        Optional.ofNullable(nespJfx.defaultConfig()).orElse(DefaultConfig.getDefault()));
                generateBuildClassFileTask.doFirst(new Action<Task>() {
                    @Override
                    public void execute(final Task task) {
                        final Set<Task> tasks =
                                project1.getTasksByName(CONFIG_JAVAFX_TASK_NAME, true);
                        if (!tasks.isEmpty()) {
                            ((ConfigTask) tasks.toArray(new Object[0])[0]).run();
                        }
                    }
                });
                generateBuildClassFileTask.run();
            }
        });
    }

    @SuppressWarnings("all")
    private void configureGenerateRFileTask(Project project) {
        // Do not use lambdas.
        // Using Java lambdas is not supported as task inputs.
        // Please refer to https://docs.gradle.org/7.2/userguide/validation_problems.html#implementation_unknown
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(@Nonnull final Project project1) {
                NespJavaFxPluginExtension nespJfx =
                        (NespJavaFxPluginExtension) project1.getExtensions()
                                .findByName(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME);
                if (nespJfx == null) return;

                GenerateRClassFileTask generateRClassFileTask = project1.getTasks().create(
                        GENERATE_R_FILE_TASK_NAME,
                        GenerateRClassFileTask.class,
                        Optional.ofNullable(nespJfx.resourceConfig()).orElse(ResourceConfig.getDefault()));
                generateRClassFileTask.doFirst(new Action<Task>() {
                    @Override
                    public void execute(final Task task) {
                        final Set<Task> tasks =
                                project1.getTasksByName(GENERATE_BUILD_FILE_TASK_NAME, true);
                        if (!tasks.isEmpty()) {
                            ((GenerateBuildConfigClassFileTask) tasks.toArray(new Object[0])[0]).run();
                        }
                    }
                });
                generateRClassFileTask.run();
                /*Set<Task> compileJavaTasks = project1.getTasksByName("compileJava", true);
                if (!compileJavaTasks.isEmpty()) {
                    JavaCompile compileJavaTask = (JavaCompile) compileJavaTasks.toArray(new Object[0])[0];
                    compileJavaTask.doFirst(new Action<Task>() {
                        @Override
                        public void execute(@Nonnull final Task task1) {
                            generateRClassFileTask.run();
                        }
                    });
                }*/
            }
        });
    }

    @SuppressWarnings("all")
    private void configureGenerateBaseControllerFileTask(Project project) {
        // Do not use lambdas.
        // Using Java lambdas is not supported as task inputs.
        // Please refer to https://docs.gradle.org/7.2/userguide/validation_problems.html#implementation_unknown
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(@Nonnull final Project project1) {
                NespJavaFxPluginExtension nespJfx =
                        (NespJavaFxPluginExtension) project1.getExtensions()
                                .findByName(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME);
                if (nespJfx == null) return;

                Optional<BaseControllerOptions> baseControllerOptions = Optional.ofNullable(nespJfx.baseControllerOptions());
                Boolean baseControllerEnable = baseControllerOptions.map(BaseControllerOptions::getEnable).orElse(false);
                String baseControllerSuperClass = baseControllerOptions.map(BaseControllerOptions::getSuperClass).orElse("");
                List<String> baseControllerSuperInterfaces = baseControllerOptions.map(BaseControllerOptions::getInterfaces).orElse(Collections.emptyList());

                GenerateBaseControllerClassFileTask generateBaseControllerClassFileTask = project1.getTasks().create(
                        GENERATE_BASE_CONTROLLER_FILE_TASK_NAME,
                        GenerateBaseControllerClassFileTask.class,
                        baseControllerSuperClass,
                        baseControllerSuperInterfaces
                );
                if (baseControllerEnable) {
                    final Set<Task> tasks =
                            project1.getTasksByName(GENERATE_R_FILE_TASK_NAME, true);
                    if (!tasks.isEmpty()) {
                        final GenerateRClassFileTask generateRClassFileTask = (GenerateRClassFileTask) tasks.toArray(new Object[0])[0];
                        generateBaseControllerClassFileTask.dependsOn(generateRClassFileTask);
                        generateBaseControllerClassFileTask.run();
                    }

                    Set<Task> compileJavaTasks = project1.getTasksByName("compileJava", true);
                    if (!compileJavaTasks.isEmpty()) {
                        JavaCompile compileJavaTask = (JavaCompile) compileJavaTasks.toArray(new Object[0])[0];
                        final Action<Task> taskAction = new Action<>() {
                            @Override
                            public void execute(@Nonnull final Task task1) {
                                if (baseControllerEnable) {
                                    generateBaseControllerClassFileTask.run();
                                }
                            }
                        };

                        // exec twice.
                        compileJavaTask.doFirst(taskAction);
                        compileJavaTask.doLast(taskAction);

                    }
                }
            }
        });
    }

    @SuppressWarnings("all")
    private void configureGenerateViewBindingFileTask(Project project) {
        // Do not use lambdas.
        // Using Java lambdas is not supported as task inputs.
        // Please refer to https://docs.gradle.org/7.2/userguide/validation_problems.html#implementation_unknown
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(@Nonnull final Project project1) {
                NespJavaFxPluginExtension nespJfx =
                        (NespJavaFxPluginExtension) project1.getExtensions()
                                .findByName(NESP_JAVA_FX_PLUGIN_EXTENSION_NAME);
                if (nespJfx == null) return;

                Boolean viewBindingEnable = nespJfx.getViewBinding();

                GenerateViewBindingClassFileTask generateViewBindingClassFileTask = project1.getTasks().create(
                        GENERATE_VIEW_BINDING_FILE_TASK_NAME,
                        GenerateViewBindingClassFileTask.class);
                if (viewBindingEnable) {
                    final Set<Task> tasks = project1.getTasksByName(GENERATE_R_FILE_TASK_NAME, true);
                    if (!tasks.isEmpty()) {
                        final GenerateRClassFileTask generateRClassFileTask =
                                (GenerateRClassFileTask) tasks.toArray(new Object[0])[0];
                        generateViewBindingClassFileTask.dependsOn(generateRClassFileTask);
                        generateViewBindingClassFileTask.run();
                    }

                    Set<Task> compileJavaTasks = project1.getTasksByName("compileJava", true);
                    if (!compileJavaTasks.isEmpty()) {
                        JavaCompile compileJavaTask = (JavaCompile) compileJavaTasks.toArray(new Object[0])[0];
                        final Action<Task> taskAction = new Action<>() {
                            @Override
                            public void execute(@Nonnull final Task task1) {
                                if (viewBindingEnable) {
                                    generateViewBindingClassFileTask.run();
                                }
                            }
                        };

                        // exec twice.
                        compileJavaTask.doFirst(taskAction);
                        compileJavaTask.doLast(taskAction);

                    }
                }
            }
        });
    }

}
