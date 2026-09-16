/**
 * 
 */
/**
 * 
 */
module AulaBancodeDados {
	requires java.sql;
	requires javafx.controls;
    requires javafx.fxml;

    opens projetoJDBC to javafx.graphics, javafx.fxml, javafx.controls;
}