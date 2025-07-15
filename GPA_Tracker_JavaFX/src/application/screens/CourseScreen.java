package application.screens;

import application.logic.Semester;


import application.logic.Course;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseScreen extends UIScreen{
	
	Semester selectedSemester;
	ObservableList<Semester> listOfSemesters;
	int indexOfSelectedSem;
	TableView<Course> courseTable = new TableView<>();
    ObservableList<Course> courses;
	
	public CourseScreen(Stage primaryStage) {
		super(primaryStage);
	}

	public CourseScreen(Stage primaryStage, Semester selectedSemester) {
		super(primaryStage);
		this.selectedSemester = selectedSemester;
	}
	
	public CourseScreen(Stage primaryStage, ObservableList<Semester> semesters, int indexOfSelectedSem) {
		super(primaryStage);
		this.listOfSemesters = semesters;
		this.indexOfSelectedSem = indexOfSelectedSem;
		courses = listOfSemesters.get(indexOfSelectedSem).getCourses();
	}

	@Override
	public Scene buildUI() {
		Button backToSemSelect = new Button("Back");
		Button addCourse = new Button("Add Course");
        Button edit = new Button("Edit");
        Button enter = new Button("Enter");
		TextField courseCodeTextField = new TextField();
        
        backToSemSelect.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
            	switchToScreen(new SemesterScreen(primaryStage, listOfSemesters));
            }
        });
        
        enter.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
            	Course selectedCourse = null;
            	selectedCourse = courseTable.getSelectionModel().getSelectedItem();
            	//Ensures that a semester is selected
            	if(selectedCourse != null) {
            		switchToScreen(new AssessmentScreen(primaryStage, selectedCourse, listOfSemesters, indexOfSelectedSem));
            	} else {
            		
            	} 
            }
        });

        addCourse.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		if(courseCodeTextField.getText().isEmpty()) {
            		
            	} else {
            		Course tempCourse = new Course(courseCodeTextField.getText());
            		listOfSemesters.get(indexOfSelectedSem).addCourse(tempCourse);
            		courseCodeTextField.setText("");
            		courses.add(tempCourse);
            		saveData(listOfSemesters);
            	}
        	}
        });
        
        createTable(courses);
        
        
		HBox hLayout1 = new HBox(3, courseCodeTextField, addCourse, enter, backToSemSelect, edit);
		VBox layout = new VBox(10, courseTable, hLayout1);
		return new Scene(layout, 400, 300);
	}

	@SuppressWarnings("unchecked")
	public void createTable(ObservableList<Course> courses) {
		//Creates a column for semester code
		TableColumn<Course, String> courseCode = new TableColumn<>("Course Code");
		courseCode.setCellValueFactory(cellData -> 
        	new SimpleStringProperty(cellData.getValue().getCourseCode()));
		
		//Creates a column for term gpa
        TableColumn<Course, Double> gpa = new TableColumn<>("GPA");
        gpa.setCellValueFactory(cellData ->
		     new SimpleDoubleProperty(cellData.getValue().getGpa()).asObject());
        
        //Creates a column for letter Grade
        TableColumn<Course, String> letterGrade = new TableColumn<>("Letter Grade");
        letterGrade.setCellValueFactory(cellData ->
		     new SimpleStringProperty(cellData.getValue().getLetterGrade()));
        
        
        
        //Creates a column for term gpa
        TableColumn<Course, String> percent = new TableColumn<>("%");
        percent.setCellValueFactory(cellData ->
		     new SimpleStringProperty(""+cellData.getValue().getFinalPercentRounded()+"%"));
	
        //Adding all of the created columns to the table
        courseTable.getColumns().addAll(courseCode, gpa, letterGrade, percent);
        
        //restricting the resizing
        courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        courseTable.setItems(courses);
	}
}
