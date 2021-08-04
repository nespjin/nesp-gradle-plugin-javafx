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

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class GenerateBaseControllerFileTask extends DefaultTask {

    private final String[] supportFileTypes = new String[]{
            ".fxml"
    };

    @OutputFile
    abstract RegularFileProperty getDestination();

    @TaskAction
    void doTask() {
        final Project project = getProject();
        final List<File> fxmlFiles = scanProjectFxmlFiles(project);

        final List<BaseControllerClass> baseControllerClasses = new ArrayList<>();
        for (final File fxmlFile : fxmlFiles) {
            final BaseControllerFXMLParser baseControllerFXMLParser = new BaseControllerFXMLParser();
            try {
                baseControllerFXMLParser.parse(fxmlFile);
                baseControllerClasses.add(baseControllerFXMLParser.getBaseControllerClass());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for (final BaseControllerClass baseControllerClass : baseControllerClasses) {
        }

    }


    private List<File> scanProjectFxmlFiles(Project project) {
        List<File> result = new LinkedList<>();
        File fxmlFilesRootDir = new File(project.getProjectDir() + "src/main/resources");
        return scanDirFxmlFiles(fxmlFilesRootDir);
    }

    private List<File> scanDirFxmlFiles(File dir) {
        List<File> result = new LinkedList<>();
        if (dir.isFile()) return result;
        final File[] files = dir.listFiles();
        if (files == null) return result;
        for (File file : files) {
            if (file.isFile()) {
                if (isSupportFile(file.getName())) {
                    result.add(file);
                }
            } else if (file.isDirectory()) {
                result.addAll(scanDirFxmlFiles(file));
            }
        }
        return result;
    }

    private boolean isSupportFile(String fileName) {
        for (String supportFileType : supportFileTypes) {
            if (fileName.endsWith(supportFileType)) {
                return true;
            }
        }
        return false;
    }
}
