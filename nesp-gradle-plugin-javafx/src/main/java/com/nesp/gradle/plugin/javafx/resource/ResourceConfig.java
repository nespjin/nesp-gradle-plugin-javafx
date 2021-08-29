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

package com.nesp.gradle.plugin.javafx.resource;

import com.nesp.gradle.plugin.javafx.utils.ListUtils;
import org.gradle.api.Project;

import java.io.File;
import java.util.List;

public class ResourceConfig {
    public static final String DEFAULT_RESOURCE_DIR_PATH_STRING = "strings";
    public static final String DEFAULT_RESOURCE_DIR_PATH_ASSETS = "assets";
    public static final String DEFAULT_RESOURCE_DIR_PATH_DRAWABLE = "drawable";
    public static final String DEFAULT_RESOURCE_DIR_PATH_LAYOUT = "layout";
    public static final String DEFAULT_RESOURCE_DIR_PATH_STYLE = "style";

    private List<String> mStringSrcDirs;
    private List<String> mDrawableSrcDirs;
    private List<String> mLayoutSrcDirs;
    private List<String> mAssetsSrcDirs;
    private List<String> mStyleSrcDirs;

    public static ResourceConfig getDefault() {
        return new ResourceConfig();
    }

    public List<String> getStringSrcDirs(final Project project) {
        return getResourceSrcDirs(project, mStringSrcDirs, DEFAULT_RESOURCE_DIR_PATH_STRING);
    }

    public void setStringSrcDirs(List<String> stringSrcDirs) {
        mStringSrcDirs = stringSrcDirs;
    }

    public List<String> getDrawableSrcDirs(final Project project) {
        return getResourceSrcDirs(project, mDrawableSrcDirs, DEFAULT_RESOURCE_DIR_PATH_DRAWABLE);
    }

    public void setDrawableSrcDirs(List<String> drawableSrcDirs) {
        mDrawableSrcDirs = drawableSrcDirs;
    }

    public List<String> getStyleSrcDirs(final Project project) {
        return getResourceSrcDirs(project, mStyleSrcDirs, DEFAULT_RESOURCE_DIR_PATH_STYLE);
    }

    public void setStyleSrcDirs(List<String> styleSrcDirs) {
        mDrawableSrcDirs = styleSrcDirs;
    }

    public List<String> getLayoutSrcDirs(final Project project) {
        return getResourceSrcDirs(project, mLayoutSrcDirs, DEFAULT_RESOURCE_DIR_PATH_LAYOUT);
    }

    public void setLayoutSrcDirs(List<String> layoutSrcDirs) {
        mLayoutSrcDirs = layoutSrcDirs;
    }

    public List<String> getAssetsSrcDirs(final Project project) {
        return getResourceSrcDirs(project, mAssetsSrcDirs, DEFAULT_RESOURCE_DIR_PATH_ASSETS);
    }

    public void setAssetsSrcDirs(List<String> assetsSrcDirs) {
        mAssetsSrcDirs = assetsSrcDirs;
    }

    private List<String> getResourceSrcDirs(final Project project,
                                            final List<String> resourceSrcDirs,
                                            final String defaultResourceDirPath) {
        return ListUtils.prefix(ListUtils
                .ifEmpty(resourceSrcDirs, defaultResourceDirPath), pathPrefix(project));
    }

    private static String pathPrefix(final Project project) {
        return project.getProjectDir().getAbsolutePath()
                .concat(File.separator)
                .concat("src")
                .concat(File.separator)
                .concat("main")
                .concat(File.separator)
                .concat("resources")
                .concat(File.separator);
    }
}
