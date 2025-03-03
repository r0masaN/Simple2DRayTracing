module romasan.simple2draytracing {
    requires javafx.controls;
    requires javafx.fxml;

    opens romasan.simple2draytracing to javafx.fxml;
    exports romasan.simple2draytracing;
}
