module edu.rgr.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires guru.nidi.graphviz;
    requires java.desktop;


    opens edu.rgr.app to javafx.fxml;
    exports edu.rgr.app;
}