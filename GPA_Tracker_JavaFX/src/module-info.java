module GPACalculatorJavaFX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.base;  // Explicitly require FXML if using .fxml files
    
    opens application to javafx.fxml;  // Only javafx.fxml needed here
    exports application;  // Required to make your classes accessible
}