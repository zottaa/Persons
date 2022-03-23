module com.company.persons {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.company.persons to javafx.fxml;
    exports com.company.persons;
}