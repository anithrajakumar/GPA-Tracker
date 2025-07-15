package application.screens;



import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import application.logic.Assessment;
import application.logic.Course;
import application.logic.Semester;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SemesterScreen extends UIScreen {
	
	// Creating a table to hold the semesters
	TableView<Semester> semesterTable = new TableView<>();
	ObservableList<Semester> semesters;
	Boolean editing = false;
	

	public SemesterScreen(Stage primaryStage) {
		super(primaryStage);
		this.semesters = loadData();
	}
	public SemesterScreen(Stage primaryStage, Semester selectedSemester) {
		super(primaryStage);
		this.semesters = loadData();
	}
	
	public SemesterScreen(Stage primaryStage, ObservableList<Semester> semesters) {
		super(primaryStage);
		this.semesters = semesters;
	}

	// Builds the scene for our application
	@Override
	//Builds the screen layout otherwise known as ui
	public Scene buildUI() {
		
		// Creating elements to add to the scene
		TextField semName = new TextField("");
		semName.setPromptText("Semester Name");
		
		Button addSem = new Button("Add Semester");
		Button editButton = new Button("Edit");
		Button enter = new Button("Enter");
		Button deleteButton = new Button("Delete");
		
		Label error = new Label();
		error.setVisible(false);
		
        // Creating the table with the data
        createTable(semesters);
        
        enter.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
            	Semester selectedSemester = null;
            	selectedSemester = semesterTable.getSelectionModel().getSelectedItem();
            	//Ensures that a semester is selected
            	if(selectedSemester != null) {
            		error.setVisible(false);
            		//Switches over to the semester screen
            		switchToScreen(new CourseScreen(primaryStage, semesters, semesters.indexOf(selectedSemester)));
            	} else {
            		error.setText("Error - Please select the semester you would like to enter");
            		error.setVisible(true);
            	}
            }

        });
        
        
        
        editButton.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
            	Semester selectedSemester = null;
            	selectedSemester = semesterTable.getSelectionModel().getSelectedItem();
            	//Ensures that a semester is selected
            	if(selectedSemester != null) {
            		if(!editing) {
            			semName.setText(selectedSemester.getName());
            			selectedSemester.setName("-- Editing --");
	            		semesters.set(semesters.indexOf(selectedSemester), selectedSemester);
	            		error.setVisible(false);
	            		deleteButton.setVisible(false);
	            		enter.setVisible(false);
	            		addSem.setVisible(false);
	            		editing = true;
	            		editButton.setText("Save");
            		} else {
            			selectedSemester.setName(semName.getText());
            			semName.setText("");
            			semesters.set(semesters.indexOf(selectedSemester), selectedSemester);
            			error.setText("Error - Please select the semester you would like to edit");
            			error.setVisible(true);
	            		deleteButton.setVisible(true);
	            		enter.setVisible(true);
	            		addSem.setVisible(true);
	            		editing = false;
	            		editButton.setText("Edit");
	            		saveData(semesters);
            		}
            		
            	} else {
        			error.setText("Error - Please select the semester you would like to edit");
            		error.setVisible(true);
            	}
            }

        });
        
        addSem.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
            	if(semName.getText().isEmpty()) {
            		error.setText("Error - Please type a semester name");
            		error.setVisible(true);
            	} else {
            		error.setVisible(false);
            		Semester tempSem = new Semester(semName.getText());
            		semesters.add(tempSem);
            		semName.setText("");
            		saveData(semesters);
            	}
            }
        }); 
       
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {   
        	public void handle(ActionEvent event) {
	        	Semester selectedSemester = semesterTable.getSelectionModel().getSelectedItem();
	            if (selectedSemester != null) {
	                error.setVisible(false);
	                // Creating a CONFIRMATION alert
	                Alert confirm = new Alert(AlertType.CONFIRMATION);
	                confirm.setContentText("Are you sure you want to delete \"" + selectedSemester.getName() + "\"?\n(This can not be undone)");
	                confirm.showAndWait().ifPresent(response -> {
	                    if (response == ButtonType.OK) {
	                        semesters.remove(selectedSemester); 
	                        saveData(semesters);
	                    }
	                });
	            } else {
	            	error.setText("Error - Please select the semester you would like to delete.");
	                error.setVisible(true);
	            }
        	}
            
        });
		
		HBox layout = new HBox(3, semName, addSem, enter, deleteButton, editButton);
		VBox layout1 = new VBox(10, semesterTable, layout,error);
		return new Scene(layout1, 400, 300);
	}
	
	//Loads in the data from the file
	public ObservableList<Semester> loadData() {
		
		String temp;
		String[] arguments;
		Semester sem = null;
		Course course = null;
		Boolean foundOne = false;
		ObservableList<Semester> listOfSemesters = FXCollections.observableArrayList();
		try {
			//Creating a scanner for the data file
			Scanner scanner = new Scanner(Path.of("data.txt"));
			// While the file has a next line
			while(scanner.hasNextLine()) {
				//Copying the line to a variable
				temp = scanner.nextLine();
				//Splitting the line into an array
				arguments = temp.split(",");
				//Checking the line to see if its a semester
				if(arguments[0].equals("semester")) {
					//Ensuring that we found one
					if(!foundOne) {
						foundOne = true;
					} else {
						listOfSemesters.add(sem);
					}
					//Creating a semester object
					sem = new Semester(arguments[2]);

					int courseCount = Integer.parseInt(arguments[1]);
					for(int i = 0; i < courseCount; i++) {
						if(scanner.hasNextLine()) {
							//Copying the line to a variable
							temp = scanner.nextLine();
							//Splitting the line into an array
							arguments = temp.split(",");
							//Creating a course object
							course = new Course(arguments[2], arguments[3]);
							int assessmentCount = Integer.parseInt(arguments[1]);
							for(int j = 0; j < assessmentCount; j++) {
								//Copying the line to a variable
								temp = scanner.nextLine();
								//Splitting the line into an array
								arguments = temp.split(",");
								
								course.addAssessment(new Assessment(arguments[1], arguments[2], 
																	Double.parseDouble(arguments[4]), 
																	Double.parseDouble(arguments[3]),
																	Boolean.parseBoolean(arguments[5]), 
																	Boolean.parseBoolean(arguments[6])));
								//System.out.println(course);
							}
							sem.addCourse(course);
							course = null;
							}
						
					}	
					
				} 		
			}	
		} catch (IOException e) {
			System.out.println("Error with Scanner occured");
		}
		sem.calculateTGPA();
		listOfSemesters.add(sem);
		return listOfSemesters;
	}

	// Creates the table to hold all of the semesters 
	@SuppressWarnings("unchecked")
	public void createTable(ObservableList<Semester> semesters) {
		
		//Creates a column for semester names 
		TableColumn<Semester, String> semesterName = new TableColumn<>("Semester Name");
		semesterName.setCellValueFactory(cellData -> 
        	new SimpleStringProperty(cellData.getValue().getName()));
		
		// Creates a column for the number of courses in the semester 
		TableColumn<Semester, Integer> numOfCourse = new TableColumn<>("# of Courses");
		numOfCourse.setCellValueFactory(cellData -> 
		    new SimpleIntegerProperty(cellData.getValue().getNumOfCourses()).asObject());
		
		//Creates a column for term gpa
        TableColumn<Semester, Double> tgpa = new TableColumn<>("TGPA");
        // Gets the element e2 from <e1, e2> in the semester class
        tgpa.setCellValueFactory(cellData ->
        	new SimpleDoubleProperty(((double)Math.round(cellData.getValue().getTgpa()*1000))/1000).asObject());
       
        
        for(int i = 0; i < semesters.size(); i++) {
        	double totalCourses = 0, totalGPA = 0;
        	if(i == 0) {
        		semesters.get(i).setCgpa(semesters.get(i).getTgpa());
        	} else {
        		for(int j = i; j >= 0; j--) {
        			totalCourses += semesters.get(j).getNumOfCourses();
        			totalGPA += (semesters.get(j).getTgpa())*semesters.get(j).getNumOfCourses();
        		}
        		semesters.get(i).setCgpa(totalGPA/totalCourses);
        	}
        }
        
        //Creates a column for Cumulative gpa
        TableColumn<Semester, Double> cgpa = new TableColumn<>("CGPA");
        cgpa.setCellValueFactory(cellData ->
        	new SimpleDoubleProperty(((double)Math.round(cellData.getValue().getCgpa()*1000))/1000).asObject());
        

        
        //Adding all of the created columns to the table
        semesterTable.getColumns().addAll(semesterName, numOfCourse, tgpa, cgpa);
        
        //restricting the resizing
        semesterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Forcing the size for of 'select' column
        semesterName.setPrefWidth(160);
        semesterName.setMinWidth(160);
        semesterName.setMaxWidth(160);
        
		semesterTable.setItems(semesters);
		        
	}
}
