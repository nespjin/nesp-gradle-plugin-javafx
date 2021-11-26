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

import com.nesp.gradle.plugin.javafx.Config;
import com.nesp.gradle.plugin.javafx.JavaFxPlugin;
import com.nesp.gradle.plugin.javafx.reflect.MethodUtil;
import com.squareup.javapoet.*;
import javafx.fxml.FXML;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class GenerateBaseControllerClassFileTask extends BaseFxmlTask {
    private static final String TAG = "GenerateBaseControllerClassFileTask";

    private final String baseControllerSuperClassName;
    private final List<String> baseControllerSuperInterfaces;

    @Inject
    public GenerateBaseControllerClassFileTask(String superClass, List<String> superInterfaces) {
        this.baseControllerSuperClassName = superClass;
        this.baseControllerSuperInterfaces = superInterfaces;
    }

    @TaskAction
    public void run() {
        JavaFxPlugin.printLog("GenerateBaseControllerClassFileTask", "run");
        final Project project = getProject();
        final File desFile = getSourcePathGenerate();
        final String packageName = getPackageName();
        final ClassLoader classLoader = getClassLoader();

        final List<File> fxmlFiles = scanProjectFxmlFiles(project);
        Type baseControllerSuperClass = null;
        Class<?>[] baseControllerClassInterfaces = null;

        if (!baseControllerSuperClassName.isEmpty()) {
            try {
                baseControllerSuperClass = classLoader.loadClass(baseControllerSuperClassName);
                baseControllerClassInterfaces = ((Class<?>) baseControllerSuperClass).getInterfaces();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        final List<Type> superInterfaces = new ArrayList<>();
        final List<Method> superInterfaceMethods = new ArrayList<>();

        if (!baseControllerSuperInterfaces.isEmpty()) {
            for (String baseControllerSuperInterfaceName : baseControllerSuperInterfaces) {
                try {
                    final Class<?> baseControllerSuperInterfaceClass =
                            classLoader.loadClass(baseControllerSuperInterfaceName);
                    if (baseControllerSuperInterfaceClass != null) {
                        final Method[] methods = baseControllerSuperInterfaceClass.getMethods();
                        if (methods.length > 0) {
                            if (!superInterfaceMethods.isEmpty()) {
                                for (int i = 0, length = methods.length; i < length; i++) {
                                    final Method method = methods[i];
                                    if (!MethodUtil.exits(superInterfaceMethods, method)) {
                                        superInterfaceMethods.add(method);
                                    }
                                }
                            } else superInterfaceMethods.addAll(List.of(methods));
                        }
                        if (baseControllerSuperClass != null) {
                            if (!Arrays.stream(baseControllerClassInterfaces)
                                    .filter(aClass -> aClass == baseControllerSuperInterfaceClass)
                                    .toList().isEmpty()) {
                                // The base controller class is child of interface, skip it.
                                continue;
                            }
                        }
                    }
                    superInterfaces.add(baseControllerSuperInterfaceClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        final List<ClassModel> baseControllerClasses = new ArrayList<>();
        for (final File fxmlFile : fxmlFiles) {
            final BaseControllerFXMLParser baseControllerFXMLParser = new BaseControllerFXMLParser();
            baseControllerFXMLParser.setClassLoader(classLoader);
            try {
                baseControllerFXMLParser.parse(fxmlFile);
                baseControllerClasses.add(baseControllerFXMLParser.getBaseControllerClass());
            } catch (FXMLParseException e) {
                e.printStackTrace();
            }
        }

        for (final ClassModel baseControllerClass : baseControllerClasses) {

            TypeSpec.Builder classBuilder = TypeSpec
                    .classBuilder(baseControllerClass.getClassName())
                    .addJavadoc("Generated by NespJavaFxPlugin, do not edit!!!")
                    .addModifiers(Modifier.ABSTRACT)
                    .addModifiers(Modifier.PUBLIC);

            if (baseControllerSuperClass != null) {
                classBuilder.superclass(baseControllerSuperClass);
            }

            if (!superInterfaces.isEmpty()) {
                for (Type superInterface : superInterfaces) {
                    classBuilder.addSuperinterface(superInterface);
                }
            }

            List<ClassField> fields = baseControllerClass.getFields();
            for (ClassField field : fields) {
                FieldSpec.Builder filedBuilder = FieldSpec.builder(field.getType(), field.getName(), Modifier.PUBLIC);
                filedBuilder.annotations.add(AnnotationSpec.builder(FXML.class).build());
                classBuilder.addField(filedBuilder.build());
            }

            List<ClassMethod> classMethods = baseControllerClass.getClassMethods();

            if (!superInterfaceMethods.isEmpty()) {
                // Implements interface methods
                for (final Method superInterfaceMethod : superInterfaceMethods) {
                    final ClassMethod classMethod = ClassMethod.of(superInterfaceMethod);
                    if (!ClassMethod.exits(classMethods, classMethod)) {
                        classMethods.add(classMethod);
                    }
                }
            }

            for (ClassMethod classMethod : classMethods) {
                MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(classMethod.getName());
                methodBuilder.addModifiers(com.nesp.gradle.plugin.javafx.fxml.Modifier.toVmModifiers(classMethod.getModifiers()));
                List<ClassMethod.Param> params = classMethod.getParams();
                Class<?>[] paramClasses = null;
                if (params != null) {
                    paramClasses = new Class[params.size()];
                    for (int i = 0, paramsSize = params.size(); i < paramsSize; i++) {
                        final ClassMethod.Param param = params.get(i);
                        try {
                            final String className = param.getType().getTypeName();
                            if (className != null && !className.isEmpty()) {
                                paramClasses[i] = Class.forName(className);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        ParameterSpec parameterSpec =
                                ParameterSpec.builder(param.getType(), param.getName(), Modifier.FINAL).build();
                        methodBuilder.addParameter(parameterSpec);
                    }
                }

                final StringBuilder methodString = new StringBuilder(classMethod.getName() + "(");
                if (params != null && !params.isEmpty()) {
                    for (int i = 0, paramsSize = params.size(); i < paramsSize; i++) {
                        final ClassMethod.Param param = params.get(i);
                        if (i != 0) {
                            methodString.append(",");
                        }
                        methodString.append(param.getName());
                    }
                }
                methodString.append(")");

                boolean foundOnSuper = false;
                if (baseControllerSuperClass != null) {
                    final Class<?> baseControllerSuperClass1 = (Class<?>) baseControllerSuperClass;
                    Method method = null;
                    try {
                        final Method[] declaredMethods = baseControllerSuperClass1.getDeclaredMethods();
                        for (final Method declaredMethod : declaredMethods) {
                            if (ClassMethod.of(declaredMethod).equals(classMethod)) {
                                method = declaredMethod;
                                break;
                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        JavaFxPlugin.printLog(TAG, "method " + methodString
                                + " not found in " + baseControllerSuperClass);
                    }

                    if (method != null) {
                        // Found super method, add override code
                        // TODO: Replace with MethodSpec.override(method)
                        methodBuilder.addAnnotation(Override.class);
                        methodBuilder.addCode(classMethod.getReturnType().equals(void.class) ?
                                "super." + methodString + ";" :
                                "return super." + methodString + ";"
                        );
                        foundOnSuper = true;
                    }

                }

                methodBuilder.returns(classMethod.getReturnType());
                if (!foundOnSuper && !classMethod.getReturnType().equals(void.class)) {
                    if (classMethod.getReturnType().equals(boolean.class)) {
                        methodBuilder.addCode("return false;");
                    } else if (classMethod.getReturnType().equals(char.class)) {
                        methodBuilder.addCode("return '';");
                    } else if (classMethod.getReturnType().equals(byte.class)) {
                        methodBuilder.addCode("return (byte)0;");
                    } else if (classMethod.getReturnType().equals(short.class)) {
                        methodBuilder.addCode("return (short)-1;");
                    } else if (classMethod.getReturnType().equals(int.class)) {
                        methodBuilder.addCode("return -1;");
                    } else if (classMethod.getReturnType().equals(long.class)) {
                        methodBuilder.addCode("return -1L;");
                    } else if (classMethod.getReturnType().equals(float.class)) {
                        methodBuilder.addCode("return -1F;");
                    } else if (classMethod.getReturnType().equals(double.class)) {
                        methodBuilder.addCode("return -1D;");
                    } else {
                        methodBuilder.addCode("return null;");
                    }

                }
                classBuilder.addMethod(methodBuilder.build());
            }

            JavaFile.Builder javaFileBuilder = JavaFile.builder(packageName, classBuilder.build());
            javaFileBuilder.addFileComment(Config.CLASS_LICENSE_COMMENT);
            File file = new File(desFile.getAbsolutePath());
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    JavaFxPlugin.printLog(TAG, "file's parent create failed");
                }
            }
            try {
                javaFileBuilder.build().writeTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
