module hollywooddos {
    requires javafx.controls;
    requires javafx.fxml;
    requires info.movito.themoviedbapi;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;

    // Opens the package to JavaFX (needed for FXML)
    opens com.hollywooddos.jessica.views to javafx.fxml;

    // Exports main package so JavaFX can use it
    exports com.hollywooddos.jessica;

}