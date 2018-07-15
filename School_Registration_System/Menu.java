import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {

/**
* @ author ChrisOsufsen
* Worked with: Connor Brady
*
* This is a school registration system where the user can specify if they are a student or Admin
* They can then view a menu corresponding to their user type and use the application accordingly.
* Their altercations are Serialized to save what they have done so they can access it after the first run.
*
*/


	public static void main(String[] args) {


		// students list
		ArrayList<Course> CourseList = new ArrayList<Course>();
		// unregistered students list
		ArrayList<Student>StudentList = new ArrayList<Student>();

		// list to read data the first time
		ArrayList<String>myList = new ArrayList<String>();

		// read data from file
		ArrayList<String> ReadLines = new ArrayList<String>();

		File filename = new File("myfile");
		boolean exists = filename.exists();



		// if this is first time running program, then the serialized file doesnt exist
		if (exists != true) {

			String fileName = "MyUniversityCourses.csv";
			//References one line at a time
			String line = null;
			try{
				FileReader fileReader = new FileReader(fileName);

				BufferedReader bufferedReader = new BufferedReader(fileReader);
				int count = 0;
				while((line = bufferedReader.readLine()) != null) {
					if (count > 0) {

						myList.add(line);

					}

					count +=1;
				}
				bufferedReader.close();
			}
			// catch
			catch(FileNotFoundException ex){
				System.out.println( "Unable to open file '" + fileName + "'");
				ex.printStackTrace();
			}

			catch (IOException ex) {
				System.out.println( "Error reading file '" + fileName + "'");
				ex.printStackTrace();

			}


		       // put data into an object
		        for(String tmp: myList){
		            //System.out.println(tmp);

		            ArrayList<String> newList = new ArrayList<String>(Arrays.asList(tmp.split(",")));

		            Course temp = new Course(newList.get(0), newList.get(1), Integer.parseInt(newList.get(2)), Integer.valueOf(newList.get(3)), newList.get(5), Integer.valueOf(newList.get(6)), newList.get(7));
					// create objects from serialized file
		            CourseList.add(temp);
		        }

		}

		// if it's not the first time running, serialized file exists, so deserialize that and update the current course list
		else {
			CourseList = CourseDeserialization();
		}


		Scanner input = new Scanner (System.in);
	        //Begin system

	        // create an admin and student object to potentially use/Alter either depending on user
	        Student student = new Student();
	        Admin admin = new Admin();

	        // store courseList in same memory as admin's and student's
	        admin.setCourseList(CourseList);
	        student.setCourseList(CourseList);

	        admin.setStudentList(StudentList);

	        System.out.println("Press 1 if you are an Admin or press 2 if you are a Student");
			String userType = input.next();

			if (userType.equals("1")) {

				System.out.println("Enter your username: ");
				String givenUsername = input.next();
				System.out.println("Enter your password: ");
				String givenPassword = input.next();
				if (givenUsername.equals(admin.getUsername()) && givenPassword.equals(admin.getPassword())) {
					// if pass and username are correct
					while (true) {

						admin.showMenu();
						// choose from menu
						String choice = input.next();

						// add new entry
						if (choice.equals("1")) {
							// add course to course list
						    Course c = admin.CreateCourse();
				            CourseList.add(c);

				            System.out.println("course added.");
						}


						// delete
						else if (choice.equals("2")) {
							System.out.println("What is the id of the course to delete? ");
							String id = input.next();
							System.out.println("What is the section?");
							int section = input.nextInt();
							admin.Delete(id, section);

						}
						// edit
						else if (choice.equals("3")) {
							System.out.println("What is the id of the course to edit? ");
							String id = input.next();
							System.out.println("What is the section");
							int section = input.nextInt();
							admin.Edit(id, section);
						}

						// display
						else if (choice.equals("4")) {
							System.out.println("What is the id of the course to Display? ");
							String id = input.next();
							System.out.println("What is the section");
							int section = input.nextInt();
							admin.DisplayInfo(id, section);
						}
						// register student
						else if (choice.equals("5")) {
							System.out.println("What is the Student First name");
							String first = input.next();
							System.out.println("What is the Student Last name");
							String last = input.next();
							System.out.println("What is the Student username");
							String username = input.next();
							System.out.println("What is the Student password");
							String password = input.next();

							Student s = new Student(username, password, first, last);

							admin.RegisterStudent(s);

						}

						// view all courses
						else if (choice.equals("6")) {
							admin.ViewAll();
						}
						// view full courses
						else if (choice.equals("7")) {
							admin.ViewFull();
						}

						// Write to list
						else if (choice.equals("8")) {
							admin.Write();
						}

						// View students in a course
						else if (choice.equals("9")) {
							System.out.println("What is the id of the course to want to view? ");
							String id = input.next();
							System.out.println("What is the section? ");
							int section = input.nextInt();

							admin.ViewStudentsInCourse(id, section);
						}

						// View courses of a student
						else if (choice.equals("10")) {
							System.out.println("What is the Student First name");
							String first = input.next();
							System.out.println("What is the Student Last name");
							String last = input.next();

							admin.ViewCoursesWithStudent(first, last);
						}

						// sort by name
						else if (choice.equals("11")) {
							admin.SortCourses();

						}

						// serialize both the course list and students list to be used next time program is run
						else if (choice.equals("12")) {
							CourseSerialization(CourseList);
							StudentSerialization(StudentList);
							System.out.println("Goodbye");
							break;
						}
						// enters anything but an integer from 1-12
						else {
							System.out.println("Invalid entry, try again");
						}
						System.out.println(" ");

					}
				}
			}// end of admin choices


			// student choices
			else if (userType.equals("2")) {
				System.out.println("Enter your username: ");
				String givenUsername = input.next();
				System.out.println("Enter your password: ");
				String givenPassword = input.next();
				if (givenUsername.equals(student.getUsername()) && givenPassword.equals(student.getPassword())) {
					while (true) {

						student.showMenu();
						// choice from menu
						String choice = input.next();

						// view all courses
						if (choice.equals("1")) {
							student.ViewAll();
						}
						// view courses not full
						else if (choice.equals("2")) {
							student.ViewNotFull();
						}
						// register to course

						else if (choice.equals("3")) {
							student.Register();

						}
						// withdraw from course
						else if (choice.equals("4")) {
							student.Withdraw();
						}

						// see courses enrolled in
						else if (choice.equals("5")) {
							System.out.println("What is your First name");
							String first = input.next();
							System.out.println("What is your Last name");
							String last = input.next();
							student.ViewCoursesWithStudent(first, last);

						}
						// exit, serialize the course list so it can be used and accessed the next run
						else if (choice.equals("6")){
							System.out.println("Goodbye");
							CourseSerialization(CourseList);

							break;
						}
						else {
							System.out.println("Invalid input");
						}
					}
				}


			}
			// neither admin nor student (1 or 2)
			else {
				System.out.println("Invalid Entry");
			}

	}

	// method to serialize course list to a course list file
	public static void CourseSerialization(ArrayList <Course> CourseList){
		try{
			File f = new File("myfile");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);


	        	 	oos.writeObject(CourseList);

	       }catch(IOException ioe){
	            ioe.printStackTrace();
	        }

	}

	// deserialize courses from course file
	public static ArrayList<Course> CourseDeserialization() {
		try
	        {

			 File f = new File("myfile");
			 FileInputStream fis = new FileInputStream(f);
			 ObjectInputStream ois = new ObjectInputStream(fis);

		     ArrayList<Course> DeserializedCourses = (ArrayList<Course>) ois.readObject();
		     ois.close();
		     return DeserializedCourses;


	         }catch(IOException ioe){
	        	 ArrayList<Course> DeserializedCourses = null;
	             ioe.printStackTrace();
	             System.out.println("io exception");
	             return DeserializedCourses;
	          }catch(ClassNotFoundException c){
	        	  ArrayList<Course> DeserializedCourses = null;
	             System.out.println("Class not found");
	             c.printStackTrace();
	             return DeserializedCourses;
	          }

	}

	// serialize student list to student file
	public static void StudentSerialization(ArrayList <Student> StudentList){
		try{
			File f = new File("myStudentFile");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);


	        	 	oos.writeObject(StudentList);

	       }catch(IOException ioe){
	            ioe.printStackTrace();
	        }

	}
	// deserialize students from serialized student file
	public static ArrayList<Student> StudentDeserialization() {
		try
	        {

			 File f = new File("myStudentFile");
			 FileInputStream fis = new FileInputStream(f);
			 ObjectInputStream ois = new ObjectInputStream(fis);

		     ArrayList<Student> DeserializedCourses = (ArrayList<Student>) ois.readObject();
		     ois.close();
		     return DeserializedCourses;


	         }catch(IOException ioe){
	        	 ArrayList<Student> DeserializedCourses = null;
	             ioe.printStackTrace();
	             System.out.println("io exception");
	             return DeserializedCourses;
	          }catch(ClassNotFoundException c){
	        	  ArrayList<Student> DeserializedCourses = null;
	             System.out.println("Class not found");
	             c.printStackTrace();
	             return DeserializedCourses;
	          }

	}


}
