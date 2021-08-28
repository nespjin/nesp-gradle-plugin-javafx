# nesp-gradle-plugin-javafx
JavaFx Gradle Plugin

配合[nesp-sdk-javafx](https://github.com/nespjin/nesp-sdk-javafx)使用，开发JavaFx就像开发Android一样简单。

## 前提
需要使用：gradle-7.1

## 功能特性

1. 自动生成BaseController类
   可根据FXML界面布局文件生成BaseController，BaseController包含布局界面控件的Id映射。
2. 自动生成R类
   配合[nesp-sdk-javafx](https://github.com/nespjin/nesp-sdk-javafx)的```Context.getRespource()``` 可以更方便引用资源。例如： ```Context.getResource().getString(R.string.name)```

## 安装

## 使用

在项目的build.gradle文件中配置如下：
----
build.gradle

```Groovy
plugins {
    id 'com.nesp.javafx'
}

nespJfx {
    baseControllerOptions {
        // 是否生成 BaseController
        enable = true
        // 配置BaseController的父类
        superClass = "com.nesp.plugin.app.BaseController"
        // 配置BaseController的接口，可配置多个接口
        interfaces = ["javafx.fxml.Initializable"]
    }
}
```






