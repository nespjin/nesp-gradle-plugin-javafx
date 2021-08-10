package com.nesp.gradle.plugin.javafx.utils;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.*;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;
import java.util.Set;

/**
 * Utility class for project.
 */
public class ProjectUtils {
    private static final String PACKAGE_NAME_DEFAULT = "com.nesp.gradle.plugin.javafx";

    /**
     * Try to find package name of project, default is {@link #PACKAGE_NAME_DEFAULT}
     *
     * @param project project.
     * @return package name.
     */
    public static String findPackageName(Project project) {
        PluginContainer plugins = project.getPlugins();
        String packageName = PACKAGE_NAME_DEFAULT;
        if (plugins.hasPlugin(ApplicationPlugin.class)) {
            JavaApplication javaApplication =
                    project.getExtensions().findByType(JavaApplication.class);
            if (javaApplication != null) {
                Property<String> mainClass = javaApplication.getMainClass();
                String mainClassString = mainClass.getOrNull();
                if (mainClassString != null && !mainClassString.isEmpty()) {
                    packageName = parsePackageNameFromMainClassName(mainClassString);
                }
            }
        }

        if (plugins.hasPlugin(JavaPlugin.class)) {
            JavaPluginExtension javaPluginExtension = project.getExtensions().findByType(JavaPluginExtension.class);
            if (javaPluginExtension != null) {
                String mainClassString = Optional.ofNullable(javaPluginExtension.manifest().getAttributes()
                        .get("Main-Class")).map(Object::toString).orElse("");
                if (!mainClassString.isEmpty() && packageName.equals(PACKAGE_NAME_DEFAULT)) {
                    packageName = parsePackageNameFromMainClassName(mainClassString);
                }
            }
        }
        return packageName;
    }

    /**
     * Get package name from main class name.
     *
     * @param mainClassName full name of Main-Class.
     * @return package of main class.
     */
    private static String parsePackageNameFromMainClassName(String mainClassName) {
        int i = mainClassName.lastIndexOf('.');
        return mainClassName.substring(0, i);
    }

    /**
     * Create a new classloader to load project classes and dependencies classes.
     *
     * @return The new ClassLoader.
     * @param project project
     */
    public static ClassLoader createClassLoader(Project project) {
        var refClasspath = new Object() {
            FileCollection classpath = project.files();
        };

        project.getTasksByName("compileJava", true)
                .forEach(task -> {
                    JavaCompile javaCompileTask = (JavaCompile) task;
                    refClasspath.classpath = refClasspath.classpath.plus(javaCompileTask.getClasspath());
                });

        refClasspath.classpath = refClasspath.classpath.plus(
                project.getTasks().getByName("jar").getOutputs().getFiles());


        refClasspath.classpath = refClasspath.classpath.plus(
                project.getTasks().getByName("classes").getOutputs().getFiles());

        Set<File> classpathFiles = refClasspath.classpath.getFiles();
        URL[] urls = new URL[classpathFiles.size()];
        File[] classpathFileArray = classpathFiles.toArray(new File[0]);
        for (int i = 0; i < classpathFileArray.length; i++) {
            try {
                urls[i] = classpathFileArray[i].toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


        return new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
    }
}
