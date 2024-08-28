module a5.cmpt213.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens a5.cmpt213.client to javafx.fxml;
    exports a5.cmpt213.client;
}