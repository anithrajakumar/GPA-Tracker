package application;
	
import application.screens.SemesterScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) {
		try {
			//Creating an instance of the semester selection screen
			SemesterScreen semesterSelectionScreen = new SemesterScreen(primaryStage);
			
			// Giving the stage the scene of the semester selection screen
			primaryStage.setScene(semesterSelectionScreen.buildUI());
			
			// Setting the title of the application 
			primaryStage.setTitle("GPA Calculator");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
