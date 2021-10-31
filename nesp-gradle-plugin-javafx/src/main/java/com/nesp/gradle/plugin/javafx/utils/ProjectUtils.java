package com.nesp.gradle.plugin.javafx.utils;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.*;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

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
     * @param project project
     * @return The new ClassLoader.
     */
    public static ClassLoader createClassLoader(Project project) {
        var refClasspath = new Object() {
            FileCollection classpath = project.files();
        };

        project.getTasksByName("compileJava", true)
                .forEach(task -> {
                    JavaCompile javaCompileTask = (JavaCompile) task;
                    final FileCollection classpath = javaCompileTask.getClasspath();
                    final FileCollection outputs = javaCompileTask.getOutputs().getFiles();
                    refClasspath.classpath = refClasspath.classpath.plus(classpath);
                    refClasspath.classpath = refClasspath.classpath.plus(outputs);
                });

        refClasspath.classpath = refClasspath.classpath.plus(
                project.getTasks().getByName("jar").getOutputs().getFiles());

        final Task task = project.getTasks().getByName("classes");
        refClasspath.classpath = refClasspath.classpath.plus(task.getOutputs().getFiles());

        Set<File> classpathFiles = refClasspath.classpath.getFiles();
        Set<URL> urls = new HashSet<>();

        File[] classpathFileArray = classpathFiles.toArray(new File[0]);
        for (final File file : classpathFileArray) {
            try {
                urls.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

//        Arrays.stream(urls).forEach(url -> JavaFxPlugin.printDebugLog("ClassPath", String.valueOf(url)));

        URL[] urlArray = new URL[urls.size()];
        final List<URL> urlList = urls.stream().toList();
        for (int i = 0; i < urlList.size(); i++) {
            urlArray[i] = urlList.get(i);
        }
        return new URLClassLoader(urlArray, Thread.currentThread().getContextClassLoader());
    }

    public static String getGenerateSourcePath(Project project) {
        return project.getBuildDir() + "/generated/sources/javafx/src/main/java";
    }

    public static String getDefaultClassesPath(Project project) {
        return project.getBuildDir() + "classes";
    }
}
