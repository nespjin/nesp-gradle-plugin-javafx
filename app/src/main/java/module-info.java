module com.nesp.plugin.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nesp.plugin.app to javafx.fxml;
    exports com.nesp.plugin.app;
}