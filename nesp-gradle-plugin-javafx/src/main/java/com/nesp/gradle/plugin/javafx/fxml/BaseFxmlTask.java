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

package com.nesp.gradle.plugin.javafx.fxml;

import com.nesp.gradle.plugin.javafx.BaseTask;
import org.gradle.api.Project;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/26 上午10:26
 * Description:
 **/
class BaseFxmlTask extends BaseTask {

    private static final String TAG = "BaseFxmlTask";

    private static final String[] SUPPORT_FILE_TYPES = new String[]{
            ".fxml"
    };


    /**
     * Scan all fxml files under project.
     *
     * @param project project.
     * @return Fxml files scanned.
     */
    protected List<File> scanProjectFxmlFiles(Project project) {
        File fxmlFilesRootDir = new File(project.getProjectDir() + "/src/main/resources");
        return scanDirFxmlFiles(fxmlFilesRootDir);
    }

    /**
     * Scan all fxml files recursive under dir.
     *
     * @param dir Root directory.
     * @return Fxml files scanned.
     */
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

    /**
     * Check the file is supported.
     *
     * @param fileName file name for checking.
     * @return true if supported, otherwise false.
     */
    private boolean isSupportFile(String fileName) {
        for (String supportFileType : SUPPORT_FILE_TYPES) {
            if (fileName.endsWith(supportFileType)) {
                return true;
            }
        }
        return false;
    }

}
