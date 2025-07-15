package application.logic;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Course {
	// Instance variables for course class
	@SuppressWarnings("unused")
	private String courseCode, courseName, letterGrade;
	private boolean isTheoryComponent, isLabComponent;
	private double theoryWeight, theoryMark, labWeight, labMark, totalMark, totalWeight, finalGrade;
	private double gpa;
	ArrayList<Assessment> assessments = new ArrayList<Assessment>();
	
	
	// Constructor for course class
	public Course(String courseCode) {
		// Assigning the variable 
		this.courseCode = courseCode;
		this.courseName = "";
		isTheoryComponent = false;
		isLabComponent = false;
	}
	// Constructor for course class
	public Course(String courseCode, String courseName) {
		// Assigning the variable 
		this.courseCode = courseCode;
		this.courseName = courseName;
		isTheoryComponent = false;
		isLabComponent = false;
	}
	
	
	
	//Method to add an assessment into the course
	public void addAssessment(Assessment a) {
		//Adding the assessment into the array
		assessments.add(a);
		this.totalWeight += a.getWeight();
		this.totalMark += a.getMark();
		
		// Adjusting the theory component
		if(a.getIsTheoryAssessment()) {
			this.theoryWeight += a.getWeight();
			this.theoryMark += a.getMark();
		}
		
		// Adjusting the lab component
		if(a.getIsLabAssessment()) {
			this.labWeight += a.getWeight();
			this.labMark += a.getMark();
		}

		this.updateGPA();
		this.setLetterGrade(this.getLetterGrade());
	}
	
	public void removeAssessment(Assessment a) {
		//Adding the assessment into the array
		assessments.remove(a);
		this.totalWeight -= a.getWeight();
		this.totalMark -= a.getMark();
		
		// Adjusting the theory component
		if(a.getIsTheoryAssessment()) {
			this.theoryWeight -= a.getWeight();
			this.theoryMark -= a.getMark();
		}
		
		// Adjusting the lab component
		if(a.getIsLabAssessment()) {
			this.labWeight -= a.getWeight();
			this.labMark -= a.getMark();
		}

		this.updateGPA();
		this.setLetterGrade(this.getLetterGrade());
	}
	
	public void replaceAssessment(Assessment oldA, Assessment newA) {
		//Adding the assessment into the array
		System.out.println(assessments.indexOf(oldA));
		assessments.set(assessments.indexOf(oldA), newA);
		this.totalWeight -= oldA.getWeight();
		this.totalMark -= oldA.getMark();
		
		this.totalWeight += newA.getWeight();
		this.totalMark += newA.getMark();
		
		// Adjusting the theory component
		if(oldA.getIsTheoryAssessment()) {
			this.theoryWeight -= oldA.getWeight();
			this.theoryMark -= oldA.getMark();
		}
		
		if(newA.getIsTheoryAssessment()) {
			this.theoryWeight += newA.getWeight();
			this.theoryMark += newA.getMark();
		}
		
		// Adjusting the lab component
		if(oldA.getIsLabAssessment()) {
			this.labWeight -= oldA.getWeight();
			this.labMark -= oldA.getMark();
		}
		if(newA.getIsLabAssessment()) {
			this.labWeight += newA.getWeight();
			this.labMark += newA.getMark();
		}

		this.updateGPA();
		this.setLetterGrade(this.getLetterGrade());
	}
	
	public void updateGPA() {
		this.gpa = this.getCourseGPA();
	}
	
	
	public double getFinalPercent() {
	    if (totalWeight == 0) {
	        this.finalGrade = 0;
	    } else {
	        this.finalGrade = totalMark / totalWeight*100;
	    }
	    return this.finalGrade;
	}
	
	public double getFinalPercentRounded() {
	    if (totalWeight == 0) {
	        this.finalGrade = 0;
	    } else {
	        this.finalGrade = totalMark / totalWeight*100;
	    }
	    Double temp = (double) Math.round(this.finalGrade*100);
	    return temp/100;
	}
	
	public double getCourseGPA() {
		double percentage = this.getFinalPercent();
		if (percentage >= 89.51 && percentage <= 100) {
	        return 4.33;    // A+
	    } else if (percentage >= 84.51 && percentage <= 89.50) {
	        return 4.00;    // A
	    } else if (percentage >= 79.51 && percentage <= 84.50) {
	        return 3.67;    // A-
	    } else if (percentage >= 76.51 && percentage <= 79.50) {
	        return 3.33;    // B+
	    } else if (percentage >= 72.51 && percentage <= 76.50) {
	        return 3.00;    // B
	    } else if (percentage >= 69.51 && percentage <= 72.50) {
	        return 2.67;    // B-
	    } else if (percentage >= 66.51 && percentage <= 69.50) {
	        return 2.33;    // C+
	    } else if (percentage >= 62.51 && percentage <= 66.50) {
	        return 2.00;    // C
	    } else if (percentage >= 59.51 && percentage <= 62.50) {
	        return 1.67;    // C-
	    } else if (percentage >= 56.51 && percentage <= 59.50) {
	        return 1.33;    // D+
	    } else if (percentage >= 52.51 && percentage <= 56.50) {
	        return 1.00;    // D
	    } else if (percentage >= 49.51 && percentage <= 52.50) {
	        return 0.67;    // D-
	    } else if (percentage >= 0 && percentage < 49.50) {
	        return 0;    // F
	    } else {
	        throw new IllegalArgumentException("Invalid percentage: " + percentage);
	    }
	}
	
	public String getLetterGrade() {
		double percentage = this.getFinalPercent();
		if (percentage >= 89.51 && percentage <= 100) {
	        return "A+";    // A+
	    } else if (percentage >= 84.51 && percentage <= 89.50) {
	        return "A";    // A
	    } else if (percentage >= 79.51 && percentage <= 84.50) {
	        return "A-";    // A-
	    } else if (percentage >= 76.51 && percentage <= 79.50) {
	        return "B+";    // B+
	    } else if (percentage >= 72.51 && percentage <= 76.50) {
	        return "B";    // B
	    } else if (percentage >= 69.51 && percentage <= 72.50) {
	        return "B-";    // B-
	    } else if (percentage >= 66.51 && percentage <= 69.50) {
	        return "C+";    // C+
	    } else if (percentage >= 62.51 && percentage <= 66.50) {
	        return "C";    // C
	    } else if (percentage >= 59.51 && percentage <= 62.50) {
	        return "C-";    // C-
	    } else if (percentage >= 56.51 && percentage <= 59.50) {
	        return "D+";    // D+
	    } else if (percentage >= 52.51 && percentage <= 56.50) {
	        return "D";    // D
	    } else if (percentage >= 49.51 && percentage <= 52.50) {
	        return "D-";    // D-
	    } else if (percentage >= 0 && percentage <= 49.50) {
	        return "F";    // F
	    } else {
	        throw new IllegalArgumentException("Invalid percentage: " + percentage);
	    }
	}
	
	// Getter and Setters 
	public String getName() {
		return courseName;
	}
	public void setName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public double getLabWeight() {
		return labWeight;
	}
	public void setLabWeight(double labWeight) {
		this.labWeight = labWeight;
	}
	public double getLabMark() {
		return labMark;
	}
	public void setLabMark(double labMark) {
		this.labMark = labMark;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public boolean isTheoryComponent() {
		return isTheoryComponent;
	}
	public void setTheoryComponent(boolean isTheoryComponent) {
		this.isTheoryComponent = isTheoryComponent;
	}
	public boolean isLabComponent() {
		return isLabComponent;
	}
	public void setLabComponent(boolean isLabComponent) {
		this.isLabComponent = isLabComponent;
	}
	public double getTheoryWeight() {
		return theoryWeight;
	}
	public void setTheoryWeight(double theoryWeight) {
		this.theoryWeight = theoryWeight;
	}
	public double getTheoryMark() {
		return theoryMark;
	}
	public void setTheoryMark(double theoryMark) {
		this.theoryMark = theoryMark;
	}
	public ObservableList<Assessment> getAssessments() {
		ObservableList<Assessment> observableAssessments = FXCollections.observableArrayList(assessments);;
		return observableAssessments;
	}
	public void setAssessments(ArrayList<Assessment> assessments) {
		this.assessments = assessments;
	}
	public double getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(double totalMark) {
		this.totalMark = totalMark;
	}
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public double getFinalGrade() {
		return finalGrade;
	}
	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade;
	}
	public int getTotalAssessmentNumber() {
		return assessments.size();
	}
	
	public Assessment getAssessmentByIndex(int i) {
		return assessments.get(i);
	}
	
	public String toString() {
		String temp = "";
		
		temp += this.courseCode +": " + this.courseName + "\nAssessment:";
		for(int i = 0; i < assessments.size(); i++) {
			temp += "\na" + i + ": " + assessments.get(i);
		}
		temp += "\nFinal Grade: " + this.getFinalPercent();
		return temp;
	}



	public double getGpa() {
		return gpa;
	}



	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
}
