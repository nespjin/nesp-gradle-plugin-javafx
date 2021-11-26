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

import java.net.URL;
import java.util.*;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 20:08
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public final class ViewBindingFXMLParser extends FXMLParser {

    private static final String TAG = "ViewBindingFXMLParser";

    private final ClassModel mViewBindingClass;

    private static final String VIEW_BINDING_CLASS_NAME_SUFFIX = VIEW_BINDING_SUFFIX;
    private static final String VIEW_BINDING_CLASS_NAME_PREFIX = "";


    public ViewBindingFXMLParser() {
        mViewBindingClass = new ClassModel();
        mViewBindingClass.setFields(new ArrayList<>());
        mViewBindingClass.setClassMethods(new ArrayList<>());
    }

    @Override
    protected void onPreParse() {
        mViewBindingClass.setClassName(
                String.format(
                        "%s%s%s",
                        VIEW_BINDING_CLASS_NAME_PREFIX,
                        regularFileName(),
                        VIEW_BINDING_CLASS_NAME_SUFFIX
                )
        );
        super.onPreParse();
    }

    @Override
    protected void onPostParse() {
  /*      final ClassMethod classMethod = new ClassMethod();
        classMethod.setName("initialize");
        classMethod.setModifier(Modifier.PUBLIC);
        classMethod.setReturnType(void.class);
        mBaseControllerClass.getClassMethods()
                .add(classMethod);*/

        final ClassMethod classMethod2 = new ClassMethod();
        classMethod2.setName("initialize");
        classMethod2.setModifiers(List.of(new Modifier[]{Modifier.PUBLIC}));
        classMethod2.setReturnType(void.class);

        List<ClassMethod.Param> params = new ArrayList<>();
        params.add(new ClassMethod.Param(URL.class, "location"));
        params.add(new ClassMethod.Param(ResourceBundle.class, "resources"));
        classMethod2.setParams(params);
        mViewBindingClass.getClassMethods().add(classMethod2);

        List<ClassField> fieldsNeedRemoved = new LinkedList<>();
        for (ClassField field : mViewBindingClass.getFields()) {
            Class<?> type = getType(field.getTypeName());
            if (type != null) {
                field.setType(type);
            } else {
                fieldsNeedRemoved.add(field);
            }
        }

        if (!fieldsNeedRemoved.isEmpty()) {
            mViewBindingClass.getFields().removeAll(fieldsNeedRemoved);
        }

        super.onPostParse();
    }

    @Override
    protected void processStartElement() {
        for (int i = 0; i < mXMLStreamReader.getAttributeCount(); i++) {
            final String name = mXMLStreamReader.getLocalName();
            final String prefix = mXMLStreamReader.getAttributePrefix(i);
            final String localName = mXMLStreamReader.getAttributeLocalName(i);
            final String value = mXMLStreamReader.getAttributeValue(i);

            if (FX_NAMESPACE_PREFIX.equals(prefix)
                    && FX_ID_ATTRIBUTE.equals(localName)
                    && !(name == null || name.length() == 0)) {
                mViewBindingClass.getFields()
                        .add(new ClassField(name, value));
            }
        }
    }

    private String regularFileName() {
        String name = mFXMLFile.getName();
        final int i1 = name.indexOf(".");
        if (i1 != -1) {
            name = name.substring(0, i1);
        }
        StringBuilder nameRegular = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            final String upperCaseChar = String.valueOf(name.charAt(i)).toUpperCase(Locale.ROOT);
            if (i == 0) {
                nameRegular = new StringBuilder(upperCaseChar);
                continue;
            }

            if (name.charAt(i) == '_' || name.charAt(i) == '-') continue;

            if (name.charAt(i - 1) == '_' || name.charAt(i - 1) == '-') {
                nameRegular.append(upperCaseChar);
                continue;
            }

            nameRegular.append(name.charAt(i));
        }
        return nameRegular.toString();
    }

    public ClassModel getViewBindingClass() {
        return mViewBindingClass;
    }
}
