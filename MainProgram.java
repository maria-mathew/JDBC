package testpack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

	public class MainProgram {
	public static void main(String arg [] ) {

//declaring and initializing choice variable for switch
	int choice = 0;
//create a DB_Logic object to call the constructor and establish connection
		DB_Logic db = new DB_Logic();
		
//creating scanner object
		Scanner input = new Scanner(System.in);
		
			while(choice!=7) {
				
				choice = showMenu();                  //displaying menu to user to choose from
				switch(choice) {
				
				case 1: //show all students
					ArrayList<Student> all = db.getAllStudents();        //arraylist to hold all student objects from the table
					if(all.size()!=0) {
						for(Student student:all) {
							showStudent(student);                       //printing the table
							System.out.println();
						}
					}
					else {
						System.out.println("The Table is Empty!!\n");        //table empty
					}
					break;
					
				case 2: //enter new information
					db.insertData(getStudentInfo());              //calling method from DB_logic to insert data into the table
					break;
					
				case 3: //update an existing student information
					Student student = getUpdateInfo();          //Receiving update info from user and creating a student object
					if(student==null) {
						System.out.println("Student does not exist");   //student info to be updated does not exist
					}
					else {
						db.updateData(student);      //calling method from DB_Logic to update the table
					}
					break;
					
				case 4: //delete an existing student information
						System.out.print("Enter the Id of the student to be deleted: ");
						int id = input.nextInt();
						
						//validating the id entered (checking if it exists in the table)
						if(!db.idCheck(id)) {
							System.out.println("The id does not exist!!");
						}
						else
						db.deleteData(id);          //calling method from DB_Logic to delete a data
						
					break;
					
				case 5: //search for a student using phone number
					System.out.print("Enter the phone number to be searched for: ");
					long phone = 0;
					
				//validating the entered phone number
					boolean validIntegerEntered = false;
					while(!validIntegerEntered) {
						try {
							  phone = input.nextLong();
							 validIntegerEntered = true;
							 }
						catch(Exception e) {
							input.next();
							System.out.print(e+": Enter a valid phone number: ");
							}
						}
					
				//searching for the student with entered phone number
					ArrayList <Student> listWithPhoneNumber = db.searchByPhone(phone);
					if(listWithPhoneNumber.size()!=0) {
						for(Student temp: listWithPhoneNumber) {
							showStudent(temp);             //showing result
							System.out.println();
						}
					}
					else if(listWithPhoneNumber.size()==0) {
						System.out.println("It does not exist in the table.");  //student does not exist with the entered number
					}
					
					break;
					
				case 6: //search for student information using first name
					
				//declaring name variable
					 String name;
					 
				//prompiting user to enter the name to be searched
					System.out.print("Enter the first name or last name of the student: ");
		           
				//validating name entered by user
					while(true) {
						 name = input.next();
					if(!name.matches("[a-zA-Z]+")){
						System.out.print("Wrong Input!! Enter only letters: ");
					}
					else{
						break;
					}}
				
				//searched student with the name entered by the user
					ArrayList <Student> studentWithName = db.searchStudentsByName(name);
					
					if(studentWithName.size()!=0) {
						for(Student temp1: studentWithName) {
							showStudent(temp1);                //showing result
							System.out.println();
						}
					}
					else if(studentWithName.size()==0) {
						System.out.println("Student with the name '"+name+"' does not exist in the table ");   //student with name entered does not exist
					}
					break;
				}
			}
	
//exiting the program
	if(choice == 7) {
		System.out.println("Exited the program succesfully!!");
	}
	
	
}
// method to display menu of actions possible
	public static int showMenu() {
	
	//creating scanner object
		Scanner input = new Scanner(System.in);
	//declaring and initializing a choice variable 
		int choice = 0;
		System.out.println("\n\nMENU:\n"
				+ "--------------------------------------------------------------"
				+ "\n1. Show all students information from the table: studentinfo"
				+ "\n2. Insert new student information into the table"
				+ "\n3. Update an existing student information"
				+ "\n4. Delete an existing student information" 
				+ "\n5. Search for a student information using phone number"
				+ "\n6. Search for a student information using first name"
				+ "\n7. Exit"
				+ "\n--------------------------------------------------------------");
		
		try {
		choice = input.nextInt();
		}
		catch(InputMismatchException e){
			System.out.println(e+"\nEnter an interger value\n");
		}
		return choice;
	}

//method to show student info
	public static  void showStudent(Student student) {
		System.out.printf("%10d%15s%15s%15d",student.getId(),student.getFirstName(), student.getLastName(), student.getPhone());
	}
	
//method to get student information from user to insert new student
	public static Student getStudentInfo() {
		Scanner input = new Scanner(System.in);
	
	//declaring variables
		String firstName;
		String lastName;
		long phone = 0;
		
	//prompting user to enter first name
		System.out.print("Enter the first name: ");		
		while(true) {
			 firstName = input.next();
		if(!firstName.matches("[a-zA-Z]+")){
			System.out.print("Wrong Input!! Enter only letters: ");
		}
		else{
			break;
		}}
	
	//promping user to enter last name
		System.out.print("Enter the last name: ");
		while(true) {
			 lastName = input.next();
			if(!lastName.matches("[a-zA-Z]+")){
				System.out.print("Wrong Input!! Enter only letters: ");
			}
			else{
				break;
		}}
		
	//prompting user to enter phone
		System.out.print("Enter the phone number: ");
	
	//validating phone number enetered by the user
		
		boolean validIntegerEntered = false;
		while(!validIntegerEntered) {
			try {
				 phone = input.nextLong();
				 validIntegerEntered = true;
				 }
			catch(Exception e) {
				input.next();
				System.out.print(e+": Enter a valid phone number: ");
				}
			}
	
	//creating student object
			Student student = new Student(0, firstName, lastName, phone);
			
			return student;
			}
			
//method to get update information to update an existing information
		public static Student getUpdateInfo() {
		
		//creating DB_Logic object
			DB_Logic db = new DB_Logic();
		
		//creating scanner object
			Scanner input = new Scanner(System.in);
			
		//declaring variables
			int id;
			String firstName;
			String lastName;
			long phone  =  0;
			Student student = null;
		
		//prompting user to enter the id
			System.out.print("Enter the id of the student to update info: ");
			
		//validating the id entered	
			while(true) {
				try {
				id = input.nextInt();
				break;}
				catch(Exception e) {
					System.out.print(e+"Enter an Integet value!: ");
				}
			}
			
		//checking if the id entered exist in the table
			if(!db.idCheck(id)) {
				System.out.println("The id does not exists!!");
			}
			
		//continuing with accepting info from user as the id exist in the table
			else {
		
		//prompting user to enter the first name
				System.out.print("Enter the first name: ");
				
				while(true) {
					firstName = input.next();
					if(!firstName.matches("[a-zA-Z]+")){
					System.out.print("Wrong Input!! Enter only letters: ");
				}
				else {break;}
			}
				
		//prompting user to enter the last name
				System.out.print("Enter the last name: ");
				while(true) {
					
					lastName = input.next();
					if(!lastName.matches("[a-zA-Z]+")){
						System.out.print("Wrong Input!! Enter only letters: ");
					}
					else {break;}
				}
		//prompting user to enter phone number
				System.out.print("Enter the phone number: ");
			
				boolean validIntegerEntered = false;
				while(!validIntegerEntered) {
				try {
					 phone = input.nextLong();
					 validIntegerEntered = true;
					 }
				catch(Exception e) {
					input.next();
					System.out.print(e+": Enter a valid phone number: ");
					}
				}
		//creating the student object
				 student = new Student(id, firstName, lastName, phone);
				}
		return student;     															
		}
	}
