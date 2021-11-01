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
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/1 上午11:23
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public class ParameterVariableElement implements VariableElement {

    private static final String TAG = "ParameterVariableElement";

    private final Parameter mParameter;

    public ParameterVariableElement(final Parameter parameter) {
        mParameter = parameter;
    }

    @Override
    public TypeMirror asType() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.PARAMETER;
    }

    @Override
    public Set<Modifier> getModifiers() {
        final Set<Modifier> modifiers = new HashSet<>();
        if (java.lang.reflect.Modifier.isPublic(mParameter.getModifiers())) {
            modifiers.add(Modifier.PUBLIC);
        }
        if (java.lang.reflect.Modifier.isProtected(mParameter.getModifiers())) {
            modifiers.add(Modifier.PROTECTED);
        }
        if (java.lang.reflect.Modifier.isPrivate(mParameter.getModifiers())) {
            modifiers.add(Modifier.PRIVATE);
        }
        if (java.lang.reflect.Modifier.isAbstract(mParameter.getModifiers())) {
            modifiers.add(Modifier.ABSTRACT);
        }
        if (java.lang.reflect.Modifier.isStatic(mParameter.getModifiers())) {
            modifiers.add(Modifier.STATIC);
        }
        if (java.lang.reflect.Modifier.isFinal(mParameter.getModifiers())) {
            modifiers.add(Modifier.FINAL);
        }
        if (java.lang.reflect.Modifier.isTransient(mParameter.getModifiers())) {
            modifiers.add(Modifier.TRANSIENT);
        }
        if (java.lang.reflect.Modifier.isVolatile(mParameter.getModifiers())) {
            modifiers.add(Modifier.VOLATILE);
        }
        if (java.lang.reflect.Modifier.isSynchronized(mParameter.getModifiers())) {
            modifiers.add(Modifier.SYNCHRONIZED);
        }
        if (java.lang.reflect.Modifier.isNative(mParameter.getModifiers())) {
            modifiers.add(Modifier.NATIVE);
        }
        if (java.lang.reflect.Modifier.isStrict(mParameter.getModifiers())) {
            modifiers.add(Modifier.STRICTFP);
        }
        return modifiers;
    }

    @Override
    public Object getConstantValue() {
        return null;
    }

    @Override
    public Name getSimpleName() {
        return new SimpleName(mParameter.getName());
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
        return null;
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return null;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return null;
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return v.visit(this, p);
    }
}
