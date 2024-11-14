module motlohi.javagroupassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens motlohi.javagroupassignment to javafx.fxml;
    exports motlohi.javagroupassignment;
    exports motlohi.javaass;
    opens motlohi.javaass to javafx.fxml;
}