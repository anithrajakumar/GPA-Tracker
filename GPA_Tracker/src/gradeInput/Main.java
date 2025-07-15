package gradeInput;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
		public static ArrayList<Double> gradeWeight = new ArrayList<Double>();
		public static ArrayList<Double> grade = new ArrayList<Double>();
		public static ArrayList<String> markName = new ArrayList<String>();
		
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		String UserInput = null;
		String courseName; 
		String AssignmentName = null;
				
		//Variables
		double gradeAchieved = 0;
		double gradeValue = 0;
		double theoreticalExamMark = 0;
		double AssignmentWeight = 0;
		int amountOfGrade = 0;
		
		//asking the course user wants to calculate marks for
		System.out.print("What Course are you looking to Calulate your Mark for?: ");
		courseName = input.nextLine();
		
		space();
		
		menu();
		
		space();
		space();
		
		
		do {
			
			space();
			
			//asking for input from user
			System.out.print("Input: ");
			UserInput = input.nextLine();
			
			//seeing if user asked for menu
			if(UserInput.equals("menu")) {
				space();
				menu();
			}else {
				String[] userInputSplit = UserInput.split(" ");
				
				//checking if the user did not enter done
				if(!(UserInput.equals("done"))) {
					//trying to catch if they enter the grade input incorrectly
					try {
						
						//Converting the first element as a int, then using it for the amount of that mark
						amountOfGrade = Integer.parseInt(userInputSplit[0]);
						//Converting the second element into a double to be used as the weight of the grade
						AssignmentWeight = Double.parseDouble(userInputSplit[1]);
						//Setting the third element to the assignment name
						AssignmentName = userInputSplit[2];
						
						try {
							//running a loop for the total amount of marks for this assignment 
							for(int i = 0; i < amountOfGrade; i++) {
								//adding the name of the mark into the array
								markName.add(AssignmentName + " #" + (i+1));
								//adding the weight of the grade to the array
								gradeWeight.add(AssignmentWeight);
								//asking for the mark achieved out of the assignment mark
								System.out.print("Mark of " + AssignmentName + " #" + (i+1) + " as a percent: " );
								//taking input of the mark received 
								double mark = Double.parseDouble(input.nextLine());
								grade.add(((mark/100)*AssignmentWeight));
							}
						}catch(Exception e){
							System.out.println("Oops");
						}
							
						
						
					}catch(Exception e) {
						System.out.println("Seems like you have entered something wrong");
						System.out.println("Ensure to enter [Amount of marks] [the weight] [the mark name]");
					} //End of try catch
					
					
					
				}// end of if statement 
				
				space();
			}
			
			
			
			
		}while(!(UserInput.equals("done")));
		
		System.out.print("Exam weight?: ");
		double examWeight = Double.parseDouble(input.nextLine());
		
		
		System.out.print("What will you like your final percent to be?: ");
		double wantedFinalMark = Double.parseDouble(input.nextLine());
		
		//adds up all of the received grades
		for(int i = 0; i < grade.size(); i++) {
			gradeAchieved += grade.get(i);
		}
		//adds up all of the grade weights
		for(int i = 0; i < gradeWeight.size(); i++) {
			gradeValue += gradeWeight.get(i);
		}
		
		space();
		
		System.out.println("Cousre: " + courseName); 
		System.out.println("------------------------------------------------------------");

		
		marks();
		
		
		System.out.println("------------------------------------------------------------");
		
		double currentMark = round((gradeAchieved/gradeValue)*100);
		
		System.out.println("Current Mark: " + currentMark + "%");
		
		space();
		
		gradeValue += examWeight;
	;
		double neededExamMark = (gradeValue*(wantedFinalMark/100)) - gradeAchieved;
		double neededExamPercent = round((neededExamMark/examWeight)*100);
		
		
		System.out.println("You will need to achieve a mark of: " + neededExamPercent + "% to get " + (wantedFinalMark) + "% as your final mark");
		
		space();
		
		System.out.println("Theoretical Marks");
		for(double i = 0; i < 11; i++) {
			
			System.out.print("Exam " + (i*10) + "%: ");
			theoreticalExamMark = gradeAchieved + (examWeight*(i/10));
			double theoreticalMark = round((theoreticalExamMark/gradeValue)*100);

			System.out.print(theoreticalMark + "%");
			space();
		}
		
		input.close();

	}
	public static void space() {
		System.out.println();
	}
	public static void menu() {
		//Displaying expected inputs from the user
		System.out.println("Expected Inputs: ");
		System.out.println("1) menu (to display this menu again)");	
		System.out.println("2) [Amount of marks] [the weight] [the mark name] (To enter grades)");
		System.out.println("3) done (Exit once completed)");
	}
	public static double round(double x) {
		x *= 100;
		x = Math.round(x);
		x /= 100;
		return x;
	}
	public static void marks() {
		for(int i = 0; i < markName.size(); i++) {
			double gradePercent = round((grade.get(i)/gradeWeight.get(i))*100);

			System.out.println("[" + (i+1)+ "] " + markName.get(i) + ": " + grade.get(i) + "/" + gradeWeight.get(i) + "   (" + gradePercent + "%)");
		}
	}

}