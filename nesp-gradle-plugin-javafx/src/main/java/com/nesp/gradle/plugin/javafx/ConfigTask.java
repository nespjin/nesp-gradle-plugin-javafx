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
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.Set;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/16 上午9:34
 * Description:
 **/
public class ConfigTask extends BaseTask {

    @TaskAction
    public void run() {
        JavaFxPlugin.printLog("ConfigTask", "run");
        final Project project = getProject();
        if (project.getPlugins().hasPlugin(JavaPlugin.class)) {
            final File desFile = new File(ProjectUtils.getGenerateSourcePath(project));

            JavaPluginExtension javaPluginExtension =
                    project.getExtensions().findByType(JavaPluginExtension.class);
            if (javaPluginExtension != null) {
                Set<File> mainSrcDirs = javaPluginExtension
                        .getSourceSets()
                        .getByName("main")
                        .getJava()
                        .getSrcDirs();

                mainSrcDirs.add(desFile);

//                        printDebugLog("Add src dir", desFile.getAbsolutePath());

                javaPluginExtension
                        .getSourceSets()
                        .getByName("main")
                        .getJava()
                        .setSrcDirs(mainSrcDirs);
            }
        }
    }


}
