package com.nesp.gradle.plugin.javafx;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public abstract class GenerateViewBindingFileTask extends DefaultTask {

    private final String[] supportFileTypes = new String[]{
            ".fxml"
    };

    @OutputFile
    abstract RegularFileProperty getDestination();

    @TaskAction
    void generate() {
        File file = getDestination().get().getAsFile();

        file.getParentFile().mkdirs();
//        file.write 'Hello!'
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
