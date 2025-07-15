package application.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Test_Logic {

	public static void main(String[] args) {
		ArrayList<Semester> allSemesters = new ArrayList<Semester>();
		
		allSemesters = loadData();
		
		System.out.println("Main Page");
		System.out.println("CGPA: " + getTotalCGPA(allSemesters));
		System.out.println("\n[1] Edit");

		saveData(allSemesters);

	}
	
public static ArrayList<Semester> loadData() {
	
	// Used to hold a temp semester
	Semester tempSem = null;
	// Used to hold a temp Course
	Course tempCourse = null;
	// Used to hold a temp line of text
	String tempLine;
	// USed to split the temp line
	String[] arguments;
	
	int outterLoopNum, innerLoopNum;
	
	// An array to hold all of the semesters
	ArrayList<Semester> tempAllSem = new ArrayList<Semester>();

	
	try {
		//Creating a scanner for the data file
		Scanner scanner = new Scanner(Path.of("data.txt"));
		// While the file has a next line
		while(scanner.hasNextLine()) {
			//Copying the line of text over and splitting it
			tempLine = scanner.nextLine();
			arguments = tempLine.split(",");
			
			if(arguments[0].equals("semester")) {
				tempSem = new Semester(arguments[2]);
				// Setting number of loops 
				outterLoopNum = Integer.parseInt(arguments[1]);
				// Runs for however many courses are in this specific semester
				for(int i = 0; i < outterLoopNum; i++) {
					//Copying the line of text over and splitting it
					tempLine = scanner.nextLine();
					arguments = tempLine.split(",");
					tempCourse = new Course(arguments[2], arguments[3]);
					tempCourse.setTheoryComponent(Boolean.parseBoolean(arguments[4]));
					tempCourse.setLabComponent(Boolean.parseBoolean(arguments[5]));
					
					innerLoopNum = Integer.parseInt(arguments[1]);
					
					// Runs for however many assessments there are within this course
					for(int j = 0; j < innerLoopNum; j++) {
						//Copying the line of text over and splitting it
						tempLine = scanner.nextLine();
						arguments = tempLine.split(",");
						tempCourse.addAssessment(new Assessment(arguments[1], arguments[2], 
								Double.parseDouble(arguments[3]) , Double.parseDouble(arguments[4]), 
								Boolean.parseBoolean(arguments[5]), Boolean.parseBoolean(arguments[6])));
					} // End of Assessment For Loop
					tempSem.addCourse(tempCourse);
				} // End of Course For Loop
			} // End of If (Checking if semester)
			
			tempAllSem.add(tempSem);
		}
		
		
	} catch(IOException e) {
		
	}
	return tempAllSem;
}

public static void saveData(ArrayList<Semester> allSemesters){
	
	Course tempCourse;
	Assessment tempAssessment;
	
	try (PrintWriter writer = new PrintWriter("dataTesting1.txt")){
		// For Writing the semester info
		for(int i = 0; i < allSemesters.size(); i++) {
			// Write semester information
            writer.println("semester," + allSemesters.get(i).getTotalCourseNumber() + "," + allSemesters.get(i).getName());
            
            // For writing the course info
            for(int j = 0; j < allSemesters.get(i).getTotalCourseNumber(); j++) {
            	// Holding the temp courser to be used
            	tempCourse = allSemesters.get(i).getCourseByIndex(j);
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

public static Double getTotalCGPA(ArrayList<Semester> allSemesters) {
	Double totalCgpaFromAllSem = 0.0;
	int totalCourses = 0;
	for(int i = 0; i < allSemesters.size(); i++) {
		totalCgpaFromAllSem += allSemesters.get(i).getTgpa();
		totalCourses += allSemesters.get(i).getTotalCourseNumber();
	}
	return totalCgpaFromAllSem/totalCourses;
}

public static void loadSemesterPage(ArrayList<Semester> allSemesters) {
	System.out.println("Semester Page:\n--------------");
	for(int i = 0; i < allSemesters.size(); i++) {
		System.out.println((i+1)+") " + allSemesters.get(i).getName());
	}

}
}