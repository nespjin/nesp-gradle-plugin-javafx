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

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 23:36
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public final class ClassMethod {

    private static final String TAG = "ClassMethod";

    private String name;
    private List<Modifier> modifiers;
    private String returnTypeName;
    private Type returnType;
    private List<Param> params;

    public static ClassMethod of(Method method) {
        if (method == null) return null;
        final ClassMethod classMethod = new ClassMethod();
        classMethod.setName(method.getName());
        classMethod.setModifiers(Modifier.fromReflectModifiers(method.getModifiers()));
        classMethod.setReturnType(method.getReturnType());
        classMethod.setParams(new ArrayList<>());
        for (final Parameter parameter : method.getParameters()) {
            classMethod.getParams().add(new Param(parameter.getType(), parameter.getName()));
        }
        return classMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModifiers(final List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public String getReturnTypeName() {
        return returnTypeName;
    }

    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof ClassMethod other) {
            if (getName().equals(other.getName())) {
                if (getReturnType() != other.getReturnType()) {
                    return false;
                }
                return equalParamTypes(getParams(), other.getParams());
            }
        }
        return false;
    }

    public static boolean exits(List<ClassMethod> classMethods, ClassMethod classMethod) {
        for (final ClassMethod method : classMethods) {
            if (method.equals(classMethod)) return true;
        }
        return false;
    }

    public static boolean equalParamTypes(List<Param> params1, List<Param> params2) {
        if (params1 == null && params2 != null && !params2.isEmpty()) return false;
        if (params1 != null && !params1.isEmpty() && params2 == null) return false;
        if ((params1 == null || params1.isEmpty()) && (params2 == null || params2.isEmpty())) {
            return true;
        }
        /* Avoid unnecessary cloning */
        if (params1.size() == params2.size()) {
            for (int i = 0; i < params1.size(); i++) {
                if (!Objects.equals(params1.get(i), params2.get(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ClassMethod{" +
                "name='" + name + '\'' +
                ", modifiers=" + modifiers +
                ", returnTypeName='" + returnTypeName + '\'' +
                ", returnType=" + returnType +
                ", params=" + params +
                '}';
    }

    public static class Param {

        private Type type;
        private String name;

        public Param(Type type, String name) {
            this.type = type;
            this.name = name;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Param param = (Param) o;
            return type.equals(param.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }

        @Override
        public String toString() {
            return "Param{" +
                    "type=" + type +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
