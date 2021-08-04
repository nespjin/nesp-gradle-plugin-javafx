package com.nesp.gradle.plugin.javafx;

import org.gradle.api.provider.Property;

public interface JavaFxPluginExtension {
    Property<Boolean> getViewBinding();
}
