package application.screens;

import application.logic.Semester;


import application.logic.Assessment;
import application.logic.Course;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AssessmentScreen extends UIScreen{
	
	// Creating a table to hold the semesters
	TableView<Assessment> assessmentTable = new TableView<>();
	TableView<Assessment> theoryAssessmentTable = new TableView<>();
	TableView<Assessment> labAssessmentTable = new TableView<>();
	Course selectedCourse;
	ObservableList<Assessment> assessments;
	Boolean editingAssessment = false;
	Assessment selectedAssessment;
	
	
	ObservableList<Semester> semesters;
	int indexOfSelectedSem;

	public AssessmentScreen(Stage primaryStage) {
		super(primaryStage);
	}

	public AssessmentScreen(Stage primaryStage, Course selectedCourse) {
		super(primaryStage);
		this.selectedCourse = selectedCourse;
		assessments = selectedCourse.getAssessments();
	}
	
	public AssessmentScreen(Stage primaryStage, Course selectedCourse, ObservableList<Semester> semesters, int indexOfSelectedSem) {
		super(primaryStage);
		this.selectedCourse = selectedCourse;
		this.semesters = semesters;
		this.indexOfSelectedSem = indexOfSelectedSem;
		assessments = selectedCourse.getAssessments();
	}

	@Override
	public Scene buildUI() {
		@SuppressWarnings({ })
		
        ObservableList<String> assessmentTypes = FXCollections.observableArrayList(
            "Homework", "Quiz", "Test", "Pre-Lab", "Lab", 
            "Tutorial", "Assignment", "Midterm", "Final"
        );

      
        ComboBox<String> assessmentTypeOptions = new ComboBox<>();
        assessmentTypeOptions.setItems(assessmentTypes); // Set the list of options


		Button addAssessment = new Button("Add Assessment");
		Button editAssessment = new Button("Edit Assessment");
		Button backToSemesterScreen = new Button("Back");
		
		TextField assessmentNameTextField = new TextField();
		TextField assessmentMarkTextField = new TextField();
		TextField assessmentWeightTextField = new TextField();
        Text courseInfo = new Text();
        
        Text assessmentNameLabel = new Text("Assessment Name: *");
        Text assessmentTypeLabel = new Text("Type (Optional):");
        Text markLabel = new Text("Mark: ");
        Text weightLabel = new Text("Weight: * ");
        Text theoryLabel = new Text("Theory Component (Optional):");
        Text labLabel = new Text("Lab Component (Optional):");
        //Text theoryTableLabel = new Text("Theory Table:");
        //Text labTableLabel = new Text("Laboratory Table:");
        
        CheckBox theoryCheck = new CheckBox();
        CheckBox labCheck = new CheckBox();
        CheckBox markCheck = new CheckBox();
        
        
        
        // Setting the Prompt Text for certain elements
        assessmentNameTextField.setPromptText("Assessment Name");
        assessmentMarkTextField.setPromptText("Mark");
        assessmentWeightTextField.setPromptText("Weight");
		assessmentTypeOptions.setPromptText("--N \\ A--");
		
		assessmentNameTextField.setMaxSize(primaryStage.getWidth()*0.4, primaryStage.getWidth()*0.4);
        assessmentMarkTextField.setMaxSize(primaryStage.getWidth()*0.15, primaryStage.getWidth()*0.15);
        assessmentWeightTextField.setMaxSize(primaryStage.getWidth()*0.15, primaryStage.getWidth()*0.15);
        
     
        
		createTable(assessments);
		courseInfo.setStyle("-fx-font: 18 arial;");
		courseInfo.setText(selectedCourse.getCourseCode());
		
		
		theoryCheck.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(theoryCheck.isSelected()) {
					labCheck.setDisable(true);
				} else {
					labCheck.setDisable(false);
				}
				
			}
		});
		
		markCheck.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
			}
		});
		
		labCheck.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(labCheck.isSelected()) {
					theoryCheck.setDisable(true);
				} else {
					theoryCheck.setDisable(false);
				}
				
			}
		});
		
		Assessment pendingEdit = new Assessment("--Editing--", null, 0, 0, false, false);
		editAssessment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(!editingAssessment) {
					selectedAssessment = assessmentTable.getSelectionModel().getSelectedItem();
				}
	    		// When clicked we were not editing assessment
	    		if((!editingAssessment)&&(selectedAssessment != null)) {
	    			editingAssessment = true;
		    		assessments.set(assessments.indexOf(selectedAssessment), pendingEdit);
		    		semesters.get(indexOfSelectedSem).replaceAssessment(selectedCourse, selectedAssessment, pendingEdit);
		    		editAssessment.setText("Save Assessment");
		    		assessmentNameTextField.setText(selectedAssessment.getName());
	            	assessmentMarkTextField.setText(""+selectedAssessment.getMark());
	            	assessmentWeightTextField.setText(""+selectedAssessment.getWeight());
	            	addAssessment.setVisible(false);
	            	if(selectedAssessment.getIsTheoryAssessment()) {
	            		theoryCheck.setSelected(true);
	            		labCheck.setDisable(true);
	            	} else if(selectedAssessment.getIsLabAssessment()) {
	            		labCheck.setSelected(true);
	            		theoryCheck.setDisable(true);
	            	} else {
	            		labCheck.setSelected(false);
	            		theoryCheck.setSelected(false);
	            	}
	            	if(!selectedAssessment.getAssessmentType().equals("null")) {
	            		assessmentTypeOptions.setValue(assessmentTypes.get(assessmentTypes.indexOf(selectedAssessment.getAssessmentType())));        // Default selection
	            	}
	    		} else {
	    			editingAssessment = false;
	    			
	    			selectedAssessment = new Assessment(assessmentNameTextField.getText(), 
							  						   ("" + assessmentTypeOptions.getValue()),
							  						   Double.parseDouble(assessmentWeightTextField.getText()),
							  						   Double.parseDouble(assessmentMarkTextField.getText()),
							  						   theoryCheck.isSelected(),
							  						   labCheck.isSelected());
	    			semesters.get(indexOfSelectedSem).replaceAssessment(selectedCourse, pendingEdit, selectedAssessment);
	    			
	    			assessments.set(assessments.indexOf(pendingEdit), selectedAssessment);
	    			editAssessment.setText("Edit Assessment");
	    			assessmentNameTextField.setText("");
	            	assessmentMarkTextField.setText("");
	            	assessmentWeightTextField.setText("");
	            	addAssessment.setVisible(true);
	            	labCheck.setDisable(false);
	            	labCheck.setSelected(false);
	            	theoryCheck.setDisable(false);
	            	theoryCheck.setSelected(false);
	            	assessmentTypeOptions.getSelectionModel().clearSelection();
	            	assessmentTypeOptions.setPromptText("--N \\ A--");
	            	saveData(semesters);
	    		}
			}
		});
		
		backToSemesterScreen.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
            	switchToScreen(new CourseScreen(primaryStage, semesters, indexOfSelectedSem));
            }
        });
		
		addAssessment.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
            	Assessment tempAssessment = new Assessment(assessmentNameTextField.getText(), 
						   								  ("" + assessmentTypeOptions.getValue()),
						   								  Double.parseDouble(assessmentWeightTextField.getText()),
						   								  Double.parseDouble(assessmentMarkTextField.getText()),
						   								  theoryCheck.isSelected(),
						   								  labCheck.isSelected());
             	
            	assessments.add(tempAssessment);
             	
             	semesters.get(indexOfSelectedSem).addAssessment(selectedCourse, tempAssessment);
             	saveData(semesters);
             	
            	// Resetting the text Fields
            	assessmentNameTextField.setText("");
            	assessmentMarkTextField.setText("");
            	assessmentWeightTextField.setText("");
            	assessmentTypeOptions.getSelectionModel().clearSelection();
            	assessmentTypeOptions.setPromptText("--N \\ A--");
            	theoryCheck.setSelected(false);
            	labCheck.setSelected(false);

            }
        });
		
		Text blankText = new Text();
		
		VBox  assessmentNameBox = new VBox(3, assessmentNameLabel, assessmentNameTextField);
		VBox  assessmentTypeBox = new VBox(3, assessmentTypeLabel, assessmentTypeOptions);
		HBox markTitle = new HBox(10, markLabel, markCheck);
		VBox  assessmentMarkBox = new VBox(3, markTitle, assessmentMarkTextField);
		VBox  assessmentWeightBox = new VBox(3, weightLabel, assessmentWeightTextField);
		VBox  addAssessmentButtonBox = new VBox(3, blankText, addAssessment, editAssessment);
		
		HBox theoryCheckerBox = new HBox(3, theoryLabel, theoryCheck);
        HBox labCheckerBox = new HBox(3, labLabel, labCheck);
        
        HBox hLayout1 = new HBox(5, assessmentNameBox, assessmentTypeBox, assessmentMarkBox, assessmentWeightBox);
        HBox labTheoryCheckerBox = new HBox(30, theoryCheckerBox, labCheckerBox);
		
		VBox assessmentInfoBox = new VBox(10, hLayout1, labTheoryCheckerBox);
		
		HBox allControlsBox = new HBox(5, assessmentInfoBox, addAssessmentButtonBox);
        
		HBox hLayout3 = new HBox(20, backToSemesterScreen);
		VBox finalLayout = new VBox(10, courseInfo, assessmentTable, allControlsBox, hLayout3);
		return new Scene(finalLayout, 525, 400);
	}
	@SuppressWarnings({ "unchecked" })
	public void createTable(ObservableList<Assessment> assessments) {
		//Creates a column for assessment name
		TableColumn<Assessment, String> assessmentName = new TableColumn<>("Assessment Name");
		assessmentName.setCellValueFactory(cellData -> 
		     	new SimpleStringProperty(cellData.getValue().getName()));
				
		//Creates a column for term mark
		TableColumn<Assessment, Double> mark = new TableColumn<>("Mark");
		mark.setCellValueFactory(cellData ->
				new SimpleDoubleProperty(cellData.getValue().getMark()).asObject());
		        
	    //Creates a column for term weight
		TableColumn<Assessment, Double> weight = new TableColumn<>("Weight");
		weight.setCellValueFactory(cellData ->
				new SimpleDoubleProperty(cellData.getValue().getWeight()).asObject());
				       
		//Creates a column for term weight
		TableColumn<Assessment, String> percent = new TableColumn<>("Percent");
		percent.setCellValueFactory(cellData ->
				new SimpleStringProperty(""+(Math.round((cellData.getValue().getMark())/(cellData.getValue().getWeight())*10000)) / 100.0+"%"));
		
		TableColumn<Assessment, String> theoryOrLab = new TableColumn<>("Theory/Lab");
		theoryOrLab.setCellValueFactory(cellData ->
				new SimpleStringProperty(cellData.getValue().getTheoryOrLab()));
		
		//Adding all of the created columns to the table
		assessmentTable.getColumns().addAll(assessmentName, mark, weight, percent, theoryOrLab);
		System.out.println();
		
		// Remove the constrained resize policy
		assessmentTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

		// Set initial widths (will be adjusted by the listener)
		assessmentName.setPrefWidth(200);  // Temporary value
		mark.setPrefWidth(100);
		weight.setPrefWidth(100);
		percent.setPrefWidth(100);
		theoryOrLab.setPrefWidth(100);


		assessmentTable.widthProperty().addListener((obs, oldVal, newVal) -> {
		    double tableWidth = newVal.doubleValue();
		    
		    // Set first column to 40%
		    assessmentName.setPrefWidth(tableWidth * 0.4);
		    
		    // Distribute remaining 60% among other 4 columns (15% each)
		    double otherColumnWidth = tableWidth * 0.15;
		    mark.setPrefWidth(otherColumnWidth);
		    weight.setPrefWidth(otherColumnWidth);
		    percent.setPrefWidth(otherColumnWidth);
		    theoryOrLab.setPrefWidth(otherColumnWidth);
		    
		    assessmentTable.layout();

		});
		
		        
		assessmentTable.setItems(assessments);
	}
}
