package application.screens;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import application.logic.Assessment;
import application.logic.Course;
import application.logic.Semester;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class UIScreen {
	// Creating a stage for the scenes
    protected Stage primaryStage;
    
    
    public UIScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public abstract Scene buildUI();
    
    protected void switchToScreen(UIScreen newScreen) {
        primaryStage.setScene(newScreen.buildUI());
    }
    
    public static void saveData(ObservableList<Semester> semesters2){
		
		Course tempCourse;
		Assessment tempAssessment;
		
		try (PrintWriter writer = new PrintWriter("data.txt")){
			// For Writing the semester info
			for(int i = 0; i < semesters2.size(); i++) {
				// Write semester information
	            writer.println("semester," + semesters2.get(i).getTotalCourseNumber() + "," + semesters2.get(i).getName());
	            
	            // For writing the course info
	            for(int j = 0; j < semesters2.get(i).getTotalCourseNumber(); j++) {
	            	// Holding the temp courser to be used
	            	tempCourse = semesters2.get(i).getCourseByIndex(j);
	            	// Writing the info related to the course
	                writer.println("course," + tempCourse.getTotalAssessmentNumber() + "," 
	                						 + tempCourse.getCourseCode()+ ","
	                						 + tempCourse.getCourseName()+ "," 
	                						 + tempCourse.isTheoryComponent() + "," 
	                						 + tempCourse.isLabComponent());
	                
	                // For writing the assessment info
	                for(int k = 0; k < tempCourse.getTotalAssessmentNumber(); k++) {
	                	// holding the temp assessment that will be used
	                	tempAssessment = tempCourse.getAssessmentByIndex(k);
	                	// Writing the info related to the assessment
	                	writer.println("assessment," + tempAssessment.getName() + ","
	                								 + tempAssessment.getAssessmentType() + ","
	                								 + tempAssessment.getMark() + ","
	                								 + tempAssessment.getWeight() + ","
	                								 + tempAssessment.getIsTheoryAssessment() + ","
	                								 + tempAssessment.getIsLabAssessment());
	                }
	            }
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}

