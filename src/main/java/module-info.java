module com.example.mine {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mine to javafx.fxml;
    exports com.example.mine;
}