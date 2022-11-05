/*
 * Copyright (C) 2022 The NESP Open Source Project
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

package com.nesp.plugin.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.io.IOException;

public class MainWindowScene extends Scene {

    public MainWindowScene() {
        super(new Group(), 600, 400);
        setupRootView();
    }

    private void setupRootView() {
        try {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("layout/window_main.fxml"));
            fxmlLoader.setController(new MainWindowController());
            setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
