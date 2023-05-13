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
    opens com.gmantovi.harmony.gsonClasses.track.get;
    opens com.gmantovi.harmony.gsonClasses.track.search;
    opens com.gmantovi.harmony.gsonClasses.snippet.get;
    opens com.gmantovi.harmony.gsonClasses.snippet;
    opens com.gmantovi.harmony.gsonClasses.lyrics;
    opens com.gmantovi.harmony.gsonClasses.lyrics.get;
    opens com.gmantovi.harmony.gsonClasses.artist;
    opens com.gmantovi.harmony.gsonClasses.artist.search;
    opens com.gmantovi.harmony.gsonClasses.artist.get;
    opens com.gmantovi.harmony.gsonClasses.artist.getAlbums;
    opens com.gmantovi.harmony to javafx.fxml;
    exports com.gmantovi.harmony;
}