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

package com.nesp.gradle.plugin.javafx.reflect;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/1 2:39
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public class MethodExecutableElement implements ExecutableElement {

    private Method mMethod;

    public MethodExecutableElement(final Method method) {
        mMethod = method;
    }

    @Override
    public TypeMirror asType() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.METHOD;
    }

    @Override
    public Set<Modifier> getModifiers() {
        Set<Modifier> modifiers = new HashSet<>();
        if (java.lang.reflect.Modifier.isPublic(mMethod.getModifiers())) {
            modifiers.add(Modifier.PUBLIC);
        }
        if (java.lang.reflect.Modifier.isProtected(mMethod.getModifiers())) {
            modifiers.add(Modifier.PROTECTED);
        }
        if (java.lang.reflect.Modifier.isPrivate(mMethod.getModifiers())) {
            modifiers.add(Modifier.PRIVATE);
        }
        if (java.lang.reflect.Modifier.isAbstract(mMethod.getModifiers())) {
            modifiers.add(Modifier.ABSTRACT);
        }
        if (java.lang.reflect.Modifier.isStatic(mMethod.getModifiers())) {
            modifiers.add(Modifier.STATIC);
        }
        if (java.lang.reflect.Modifier.isFinal(mMethod.getModifiers())) {
            modifiers.add(Modifier.FINAL);
        }
        if (java.lang.reflect.Modifier.isTransient(mMethod.getModifiers())) {
            modifiers.add(Modifier.TRANSIENT);
        }
        if (java.lang.reflect.Modifier.isVolatile(mMethod.getModifiers())) {
            modifiers.add(Modifier.VOLATILE);
        }
        if (java.lang.reflect.Modifier.isSynchronized(mMethod.getModifiers())) {
            modifiers.add(Modifier.SYNCHRONIZED);
        }
        if (java.lang.reflect.Modifier.isNative(mMethod.getModifiers())) {
            modifiers.add(Modifier.NATIVE);
        }
        if (java.lang.reflect.Modifier.isStrict(mMethod.getModifiers())) {
            modifiers.add(Modifier.STRICTFP);
        }
        return modifiers;
    }

    @Override
    public List<? extends TypeParameterElement> getTypeParameters() {
        return mMethod.getTypeParameters();
    }

    @Override
    public TypeMirror getReturnType() {
        return mMethod.getReturnType();
    }

    @Override
    public List<? extends VariableElement> getParameters() {
        return mMethod.getParameters();
    }

    @Override
    public TypeMirror getReceiverType() {
        return null;
    }

    @Override
    public boolean isVarArgs() {
        return mMethod.isVarArgs();
    }

    @Override
    public boolean isDefault() {
        return mMethod.isDefault();
    }

    @Override
    public List<? extends TypeMirror> getThrownTypes() {
        return null;
    }

    @Override
    public AnnotationValue getDefaultValue() {
        return null;
    }

    @Override
    public Name getSimpleName() {
        return null;
    }

    @Override
    public Element getEnclosingElement() {
        return null;
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return null;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return mMethod;
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return mMethod.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return null;
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return null;
    }
}
