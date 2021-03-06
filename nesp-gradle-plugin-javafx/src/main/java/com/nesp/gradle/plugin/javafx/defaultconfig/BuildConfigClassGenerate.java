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

package com.nesp.gradle.plugin.javafx.defaultconfig;

import com.nesp.gradle.plugin.javafx.Config;
import com.nesp.gradle.plugin.javafx.JavaFxPlugin;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public final class BuildConfigClassGenerate {
    private static final String TAG = "BuildClassGenerate";

    private static final String BUILD_CONFIG_CLASS_NAME = "BuildConfig";

    private final File mSourceDir;
    private final String mPackageName;
    private final DefaultConfig mDefaultConfig;

    public BuildConfigClassGenerate(File sourceDir, String packageName, DefaultConfig defaultConfig) {
        this.mSourceDir = sourceDir;
        this.mPackageName = packageName;
        this.mDefaultConfig = defaultConfig;
    }

    public void generate() {
        TypeSpec.Builder buildClassBuilder = TypeSpec
                .classBuilder(BUILD_CONFIG_CLASS_NAME)
                .addJavadoc("Generated by NespJavaFxPlugin, do not edit!!!")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL);

        final FieldSpec.Builder versionCodeFieldSpecBuilder = FieldSpec.builder(
                Integer.class,
                "VERSION_CODE",
                Modifier.PUBLIC,
                Modifier.STATIC,
                Modifier.FINAL
        );
        versionCodeFieldSpecBuilder.initializer(mDefaultConfig.getVersionCode().toString(), "");
        buildClassBuilder.addField(versionCodeFieldSpecBuilder.build());

        final FieldSpec.Builder versionNameFieldSpecBuilder = FieldSpec.builder(
                String.class,
                "VERSION_NAME",
                Modifier.PUBLIC,
                Modifier.STATIC,
                Modifier.FINAL
        );
        versionNameFieldSpecBuilder.initializer("\"" + mDefaultConfig.getVersionName() + "\"", "");
        buildClassBuilder.addField(versionNameFieldSpecBuilder.build());

        final FieldSpec.Builder debugFieldSpecBuilder = FieldSpec.builder(
                Boolean.class,
                "DEBUG",
                Modifier.PUBLIC,
                Modifier.STATIC,
                Modifier.FINAL
        );
        debugFieldSpecBuilder.initializer(mDefaultConfig.getDebug().toString(), "");
        buildClassBuilder.addField(debugFieldSpecBuilder.build());

        final JavaFile.Builder javaFileBuilder = JavaFile.builder(mPackageName, buildClassBuilder.build());
        javaFileBuilder.indent("    ");
        javaFileBuilder.addFileComment(Config.CLASS_LICENSE_COMMENT);
        File file = new File(mSourceDir.getAbsolutePath());
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                JavaFxPlugin.printLog(TAG, "R class file parent create failed");
            }
        }
        try {
            javaFileBuilder.build().writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
