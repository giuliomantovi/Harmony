module com.gmantovi.harmony{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires static java.sql;
    requires java.desktop;
    requires com.google.gson;
    //requires mysql.connector.j;
    opens com.gmantovi.harmony.gsonClasses;
    opens com.gmantovi.harmony.gsonClasses.error;
    opens com.gmantovi.harmony.gsonClasses.track;
    opens com.gmantovi.harmony.gsonClasses.track.chart;
    opens com.gmantovi.harmony to javafx.fxml;
    exports com.gmantovi.harmony;
}