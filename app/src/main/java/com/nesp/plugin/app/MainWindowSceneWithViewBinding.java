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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Mnemonic;

import java.util.ResourceBundle;

public class MainWindowSceneWithViewBinding extends Scene {

    private WindowMainViewBinding viewBinding;

    public MainWindowSceneWithViewBinding() {
        super(new Group(), 600, 400);
        setupRootView();
        initViews();
    }

    private void setupRootView() {
        final ResourceBundle stringsBundle = ResourceBundle.getBundle("strings/strings");
        viewBinding = WindowMainViewBinding.inflate("layout/window_main.fxml", stringsBundle);
        setRoot((Parent) viewBinding.getRoot());
    }

    private void initViews() {
        viewBinding.lb_runtime_info.setText("Runtime: " + System.getProperty("java.runtime.name")
                + "\n"
                + "Version: " + System.getProperty("java.runtime.version")
        );

        StringProperty buttonText = new SimpleStringProperty("Click Me");

        IntegerProperty clickCount = new SimpleIntegerProperty() {
            @Override
            protected void invalidated() {
                super.invalidated();
                buttonText.setValue("Clicked " + get());
            }
        };

        viewBinding.btn_click.setOnMouseClicked(event -> clickCount.set(clickCount.get() + 1));
        viewBinding.btn_click.textProperty().bind(buttonText);
    }

}
