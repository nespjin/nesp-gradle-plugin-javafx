package com.nesp.plugin.app;

import javafx.fxml.FXML;

public class HelloController extends BaseHelloViewController  {

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}