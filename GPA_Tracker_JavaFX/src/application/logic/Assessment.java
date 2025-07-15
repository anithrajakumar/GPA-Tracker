package application.logic;

public class Assessment {
	// Instances variables for assessment class
	private String name, assessmentType, theoryOrLab;
	private double weight, mark, percent;
	private Boolean isTheoryAssessment;
	private Boolean isLabAssessment;
	
	// Constructor for assessment class
	public Assessment(String name, String assessmentType, double weight, double mark, double percent) {
		// Assigning variables
		this.name = name;
		this.assessmentType = assessmentType;
		this.weight = weight;
		this.mark = mark;
		this.percent = percent;
		this.isTheoryAssessment = false;
		this.isLabAssessment = false;
		
		// Will assign based on whether mark or percent is given
		if(mark == -1) {
			this.mark = this.percent*this.weight/100;
		} else if(percent == -1) {
			this.percent = this.mark/this.weight;
		}
		
	}
	
	// Constructor for assessment class
	public Assessment(String name, String assessmentType, double weight, double mark, Boolean theory, Boolean lab) {
		// Assigning variables
		this.name = name;
		this.assessmentType = assessmentType;
		this.weight = weight;
		this.mark = mark;
		this.isTheoryAssessment = theory;
		this.isLabAssessment = lab;
		
		if(theory) {
			setTheoryOrLab("Theory");
		} else if(lab) {
			setTheoryOrLab("Lab");
		} else {
			setTheoryOrLab("N/A");
		}
	}
	
	
	// Constructor for assessment class
	public Assessment(String name, String assessmentType, double weight, double mark, double percent, Boolean theory, Boolean lab) {
		// Assigning variables
		this.name = name;
		this.assessmentType = assessmentType;
		this.weight = weight;
		this.mark = mark;
		this.percent = percent;
		this.isTheoryAssessment = theory;
		this.isLabAssessment = lab;
		
		// Will assign based on whether mark or percent is given
		if(mark == -1) {
			this.mark = this.percent*this.weight/100;
		} else if(percent == -1) {
			this.percent = this.mark/this.weight;
		}

	}
	
	// Ability to use constructor with percent only
	public static Assessment givenPercent(String name, String assessmentType, double weight, double percent) {
	    return new Assessment(name, assessmentType, weight, -1, percent);
	}
	
	// Ability to use constructor with mark only
	public static Assessment givenMark(String name, String assessmentType, double weight, double mark) {
	    return new Assessment(name, assessmentType, weight, mark, -1);
	}

	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public String getAssessmentType() {
		return assessmentType;
	}
	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}
	public Boolean getIsTheoryAssessment() {
		return isTheoryAssessment;
	}
	public void setIsTheoryAssessment(Boolean isTheoryAssessment) {
		this.isTheoryAssessment = isTheoryAssessment;
		if(isTheoryAssessment) {
			setTheoryOrLab("Theory");
		} else {
			setTheoryOrLab("N/A");
		}
		
	}
	public Boolean getIsLabAssessment() {
		return isLabAssessment;
	}
	public void setIsLabAssessment(Boolean isLabAssessment) {
		this.isLabAssessment = isLabAssessment;
		if(isLabAssessment) {
			setTheoryOrLab("Lab");
		} else {
			setTheoryOrLab("N/A");
		}
	}
	public String toString() {
		String temp = "";
		temp += this.getName();
		return temp;
	}

	public String getTheoryOrLab() {
		return theoryOrLab;
	}

	public void setTheoryOrLab(String theoryOrLab) {
		this.theoryOrLab = theoryOrLab;
	}
}
