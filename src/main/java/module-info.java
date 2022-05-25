module alinoss.onepiecedb {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.instrument;
    requires java.persistence;
    requires java.sql;
    requires java.base;
    
    opens alinoss.onepiecedb.entities;
    opens alinoss.onepiecedb to javafx.fxml;
    exports alinoss.onepiecedb;
}
