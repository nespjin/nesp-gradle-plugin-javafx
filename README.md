# nesp-gradle-plugin-javafx

JavaFx Gradle Plugin

配合[nesp-sdk-javafx](https://github.com/nespjin/nesp-sdk-javafx)使用，开发JavaFx就像开发Android一样简单。

## 提示

该项目仍在开发验证阶段

参与项目开发请加群428741525联系群主

## 开发环境

- Gradle: Gradle-7.2
- Java: OpenJdk-17

## 功能特性

1. 自动生成BaseController类 可根据FXML界面布局文件生成BaseController，BaseController包含布局界面控件的Id映射。
2. 自动生成R类

> 目前已经实现:
> - R.string
> - R.layout

配合[nesp-sdk-javafx](https://github.com/nespjin/nesp-sdk-javafx)的```Context.getRespource()``` 可以更方便引用资源。例如： ```
Context.getResource().getString(R.string.name)```

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
        // 父类或接口需要定义在子Module中
        // 配置BaseController的父类
        superClass = "com.nesp.plugin.app.BaseController"
        // 配置BaseController的接口，可配置多个接口
        interfaces = ["javafx.fxml.Initializable"]
    }
}
```






