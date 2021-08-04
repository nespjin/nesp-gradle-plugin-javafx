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

import org.gradle.api.Project;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.internal.impldep.com.google.gson.Gson;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 23:59
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
class JavaFxPluginTest {

    @Test
    public void javafxPluginAddsGenerateBaseControllerFileTaskToProject() {
        final Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("com.nesp.javafx");
        project.getPluginManager().apply("application");
        final ApplicationPlugin application = (ApplicationPlugin) project.findProject("application");

        System.out.println(project.findProperty("mainClassName"));
    }
}