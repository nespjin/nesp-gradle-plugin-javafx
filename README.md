# nesp-gradle-plugin-javafx

Gradle Plugin for JavaFx

## Install

## Usages

----
build.gradle

```Groovy
plugins {
    id 'com.nesp.javafx'
}

nespJfx {
    baseControllerOptions {
        // Auto generate BaseController Java file for fxml files.
        enable = true
        // Add your super class for base controller class.
        superClass = "com.nesp.plugin.app.BaseController"
        // Add your super interfaces for base controller class.
        interfaces = ["javafx.fxml.Initializable"]
    }
}
```






