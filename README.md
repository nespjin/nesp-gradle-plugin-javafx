# nesp-gradle-plugin-javafx

JavaFx Gradle Plugin

扫描FXML、CSS、properties、等文件并生成对应的映射类

配合[nesp-sdk-javafx](https://github.com/nespjin/nesp-sdk-javafx)使用，开发JavaFx就像开发Android一样简单。

## 提示

该项目仍在开发验证阶段

参与项目开发请加群428741525联系群主

## 开发环境

- Gradle: Gradle-7.3
- Java: OpenJdk-17
- JavaFx: 17.0.1

## 功能特性

1. 自动生成BaseController类 可根据FXML界面布局文件生成BaseController，BaseController包含布局界面控件的Id映射。
2. 自动生成R类

> 目前已经实现:
> - R.string
> - R.layout

3. 自动生成Build类， 包含一些构建信息， 如 versionCode、 versionName

配合[nesp-sdk-javafx](https://github.com/nespjin/nesp-sdk-javafx) 的```Context.getRespource()``` 
可以更方便引用资源。例如： ```
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
    defaultConfig {
        versionCode = 1
        versionName = "1.0.0"
        debug = true
    }
    
    // 不推荐使用该方式，推荐使用 viewBinding
    baseControllerOptions {
        // 是否生成 BaseController
        enable = true
        // 父类或接口需要定义在子Module中
        // 配置BaseController的父类
        superClass = "com.nesp.plugin.app.BaseController"
        // 配置BaseController的接口，可配置多个接口
        interfaces = ["javafx.fxml.Initializable"]
    }

    // 打开ViewBinding
    viewBinding = true
}
```

## 示例： 

### ViewBinding
```java
package com.nesp.javafx.sample;

import com.nesp.sdk.java.lang.SingletonFactory;
import com.nesp.sdk.javafx.BaseStage;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public class MainStage extends BaseStage {

    private static final String TAG = "MainStage";

    private MainStage() {
        //no instance
    }

    private MainStageViewBinding mBinding;

    private static boolean isShown = false;

    public static void showWindow() {
        if (isShown) return;
        var shared =
                SingletonFactory.getWeakInstance(MainStage.class, MainStage::new);
        shared.show();
        isShown = true;
    }

    @Override
    public void onCreate(final @NotNull Stage stage) {
        super.onCreate(stage);
        initializeViews();
    }

    private void initializeViews() {
        mBinding = MainStageViewBinding.inflate(R.layout.main_stage);
        setContent(mBinding.getRoot());
        final String title = getResource().getString(R.string.app_name);
        setTitle(title);

        StringProperty buttonText = new SimpleStringProperty("Click Me");

        IntegerProperty clickCount = new SimpleIntegerProperty() {
            @Override
            protected void invalidated() {
                super.invalidated();
                buttonText.setValue("Clicked " + get());
            }
        };

        mBinding.btn_click.setOnMouseClicked(event -> clickCount.set(clickCount.get() + 1));
        mBinding.btn_click.textProperty().bind(buttonText);

        mBinding.btn_switch_lang.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                if (Objects.equals(Locale.getDefault().getLanguage(), "zh")) {
                    Locale.setDefault(Locale.ENGLISH);
                    mBinding.btn_switch_lang.setText("中文");
                } else {
                    Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
                    mBinding.btn_switch_lang.setText("English");
                }

                recreate();
            }
        });

        if (!Objects.equals(Locale.getDefault().getLanguage(), "zh")) {
            mBinding.btn_switch_lang.setText("中文");
        } else {
            mBinding.btn_switch_lang.setText("English");
        }
    }


}

```



