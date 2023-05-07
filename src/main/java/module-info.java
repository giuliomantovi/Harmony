module com.gmantovi.harmony{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires static java.sql;
    requires java.desktop;
    requires com.google.gson;
    //requires mysql.connector.j;
    opens com.gmantovi.harmony to javafx.fxml;
    exports com.gmantovi.harmony;
}