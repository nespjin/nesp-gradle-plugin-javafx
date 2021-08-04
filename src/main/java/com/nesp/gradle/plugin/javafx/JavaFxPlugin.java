package com.nesp.gradle.plugin.javafx;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class JavaFxPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        JavaFxPluginExtension javaFxPluginExtension =
                project.getExtensions().create("nespJavaFx", JavaFxPluginExtension.class);

    }

}
