import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class Admin extends User implements AdminInterface{

	Scanner input = new Scanner(System.in);
	
	// list of students not put into a class
	private ArrayList <Student> StudentList = new ArrayList<Student>();
	// list of courses
	private ArrayList<Course> CourseList = new ArrayList<Course>();
	

	
	public Admin() { // default user constructor
		this.username = "Admin";
		this.password = "Admin001";
		this.first = "First";
		this.last = "Last";
	}
	
	public Course CreateCourse() {
		System.out.println("please use underscores to separate input rather than spaces");
		System.out.println("What is the course name? ");
		String name = input.next();
		System.out.println("What is the course id? ");
		String id = input.next();
		System.out.println("What is the course max students? ");
		int max = input.nextInt();
		System.out.println("Who is the course instructor? ");
		String instructor = input.next();
		System.out.println("What is the course location? ");
		String location = input.next();
		System.out.println("What is the course section? ");
		int section = input.nextInt();
		
		System.out.println("Creating new course");
		return new Course(name, id, max, instructor, section, location);
	}
	
	// search for course by id and section
	public Course Search(String id, int section, ArrayList<Course> CourseList) {
		for (int i = 0; i<CourseList.size(); i ++) {
			if(CourseList.get(i).getId().equals(id) && CourseList.get(i).getSection() == section) {
				return CourseList.get(i);
			}
		}
		// return default course if not found
			System.out.print("Course not found, returning default course");
			return new Course();
	}
	
	// delete based on the course id and section
	public int Delete(String id, int section) {
		for (int i = 0; i<CourseList.size(); i ++) {
			if(CourseList.get(i).getId().equals(id) && CourseList.get(i).getSection() == section) {
				CourseList.remove(i);
				return 1;
			}
		}
		// return default course if not found
			System.out.print("Course not found, returning default course");
			return 0;
		
	}
	
	// search for course using id and prompt to edit
	public int Edit(String id, int section) {
		
		for (int i = 0; i<CourseList.size(); i ++) {
			if(CourseList.get(i).getId().equals(id) && CourseList.get(i).getSection() == section) {
				// ask user what they want to edit
				System.out.println("What course aspect whould you like to edit? ");
				System.out.println("1: Course name");
				System.out.println("2: Course ID");
				System.out.println("3: Instructor");
				
				// assume the user enters the correct type of input for corresponding choice
				
				// start a while loop just in case user enters invalid field
				while (true) {
					int dataField = input.nextInt(); 
					
					// assume the user enters in a integer value corresponding 
					// to the field they want to edit
					if (dataField == 1){
						System.out.println("What would you like the new course name to be? ");
						String newName = input.next();
						CourseList.get(i).setCourseName(newName);
						return 1;
					}
					else if (dataField == 2){
						System.out.println("What would you like the new ID to be? ");
						String newID = input.next();
						CourseList.get(i).setId(newID);
						return 1;
					}
					else if (dataField == 3){
						System.out.println("Who would you like the new Instructor to be? ");
						String newInstructor = input.next();
						CourseList.get(i).setInstructor(newInstructor);
						return 1;
					}
					// if user enters something other than choices 1-3
					else {
						return 0;
					}
				}
				
			}
		}
		// if get through whole loop, course doesnt exist
		System.out.println("Course not found");
		return 0;
		
	}
	
	public void DisplayInfo(String id, int section) {
		Course c = Search(id, section, this.CourseList);
		System.out.println("Course Name: " + c.getCourseName());
		System.out.println("ID: " + c.getId());
		System.out.println("Instructor Name: " + c.getInstructor());
		System.out.println("Location: " + c.getLocation());
		System.out.println("Max Students: " + c.getMax());
		System.out.println("Total Students: " + c.getTotal());
		System.out.println("Course Section: " + c.getSection());
		
	}
	// show all courses
	public void ViewAll() {
		for (int i=0; i < this.CourseList.size(); i++) {
			Course c = this.CourseList.get(i);
			System.out.println(c.getCourseName() + c.getId() + c.getTotal() + c.getMax());
		}
	}
	
	// view courses where max > total
	public void ViewFull() {
		int full = 0;
		for (int i=0; i < this.CourseList.size(); i++) {
			Course c = this.CourseList.get(i);
			// course is full
			if (c.getMax() == c.getTotal()) {
				System.out.println(c.getCourseName() + c.getId() + c.getMax());
				full += 1;
			}
		}
		if (full == 0) {
			System.out.println("No courses are full");
		}
	}
	
	// display students in course after searching for course in menu, and then printing all students in the course
	public void ViewStudentsInCourse(String id, int section) {
		Course c = Search(id, section, this.CourseList);
		int students = 0;
		for (int i=0; i < c.getStudentNames().size(); i++) {
			// print student first and last name
			System.out.print(c.getStudentNames().get(i).getFirst()+ " " +c.getStudentNames().get(i).getLast() + ", ");
			students += 1;
		}
		if (students == 0) {
			System.out.println("No students in course");
		}
		
	}
	
	// view courses a student is registered in by searching through each course and finding students name
	public void ViewCoursesWithStudent(String first, String last) {
		// for every course
		for (int i = 0; i<this.CourseList.size(); i ++) {
			Course c = this.CourseList.get(i);
			
			// for every student in course
			for (int j = 0; i<c.getStudentNames().size(); j ++){
				Student s = c.getStudentNames().get(j);
				
				// if student in course
				if (s.getFirst() == first && s.getLast() == last) {
					// print course name
					System.out.print(c.getCourseName());
				}
			}
			
		}
		
	}
	 
	
	// sort by course name
	public void SortCourses() {
		Collections.sort(this.CourseList);
	}
	
	// add student to unregistered student list
	public int RegisterStudent(Student s) {
		this.StudentList.add(s);
		return 1;
	}
	
	// write full courses to a file
	public void Write() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("CoursesFull.txt", "UTF-8");
			
			for (int i=0; i < this.CourseList.size(); i++) {
				Course c = this.CourseList.get(i);
				if (c.getMax() == c.getTotal()) {
					writer.println((c.getCourseName() + c.getId() + c.getMax()));
				}
			}
			System.out.println("File written success");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showMenu() {
		 System.out.println("Here are your options:");
		 System.out.println("1. Create new course");
		 System.out.println("2. Delete a course");
		 System.out.println("3. Edit a course");
		 System.out.println("4. Display information about a course");
		 System.out.println("5. Register a Student");
		 System.out.println("6. View All courses");
		 System.out.println("7. View Full courses");
		 System.out.println("8. Write to Course List");
		 System.out.println("9. View Students in a specific course");
		 System.out.println("10. View courses that a student is registered in");
		 System.out.println("11: Sort courses");
		 System.out.println("12. Quit.");
	}

	
	// getters and setters 
	public ArrayList<Student> getStudentList() {
		return StudentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		StudentList = studentList;
	}

	public ArrayList<Course> getCourseList() {
		return CourseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		CourseList = courseList;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	
	
	
}
