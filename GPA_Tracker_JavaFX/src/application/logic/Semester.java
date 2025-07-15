package application.logic;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Semester {
	
	//Instance variables for semester class
	private String name;
	private double tgpa, cgpa;
	private Integer numOfCourses = 0;
	ArrayList<Course> courses = new ArrayList<>();

	
	public Semester(String name) {
		this.name = name;
		tgpa = 0;
		cgpa = 0; 
	}
	
	// Adds the course into the semester
	public void addCourse(Course course) {
		courses.add(course);
		setNumOfCourses(getNumOfCourses() + 1);
		this.calculateTGPA();
	}
	
	//Calculates tgpa
	public void calculateTGPA() {
		double totalTGPA = 0;
		for(int i = 0; i < courses.size(); i++) {
			totalTGPA += courses.get(i).getCourseGPA();
		}
		
		this.tgpa = totalTGPA/courses.size();
	}
	
	//returns an array of courses
	public ObservableList<Course> getCourses(){
		ObservableList<Course> observableCourses = FXCollections.observableArrayList(courses);;
		return observableCourses;
	}
	
	public void addAssessment(Course course, Assessment assessment) {
		courses.get(courses.indexOf(course)).addAssessment(assessment);
	}
	
	public void removeAssessment(Course course, Assessment assessment) {
		courses.get(courses.indexOf(course)).removeAssessment(assessment);
	}
	public void replaceAssessment(Course course, Assessment oldAssessment, Assessment newAssessment) {
		courses.get(courses.indexOf(course)).replaceAssessment(oldAssessment, newAssessment);
	}
	
	

	//Getters and Setters
	public double getTgpa() {
		return tgpa;
	}
	public void setTgpa(double tgpa) {
		this.tgpa = tgpa;
	}
	public double getCgpa() {
		return cgpa;
	}
	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTotalCourseNumber() {
		return this.courses.size();
	}
	
	public Course getCourseByIndex(int i) {
		return courses.get(i);
	}
	
	public String toString() {
		String temp = "";
		this.calculateTGPA();
		temp += "Semester: " + this.name +"\n";
		for(int i = 0; i < courses.size(); i++) {
			temp += courses.get(i).getCourseCode()+", ";
		}
		temp += "\nTGPA: " + this.getTgpa();
		return temp;
		
	}

	public int getNumOfCourses() {
		return numOfCourses;
	}

	public void setNumOfCourses(int numOfCourses) {
		this.numOfCourses = numOfCourses;
	}
}
