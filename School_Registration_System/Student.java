import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;

public class Student extends User implements StudentInterface, Serializable{

	static Scanner input = new Scanner (System.in);
	// list of students not put into a class
	private ArrayList <Student> StudentList = new ArrayList<Student>();
	// list of courses
	private ArrayList<Course> CourseList = new ArrayList<Course>();
	
	
	public Student() { // default user constructor
		this.username = "Student";
		this.password = "Student001";
		this.first = "First";
		this.last = "Last";
	}
	
	public Student(String first, String last) {
		this.username = "Student";
		this.password = "Student001";
		this.first = first;
		this.last = last;
	}
	
	public Student(String username, String password, String first, String last) { 
		this.username = username;
		this.password = password;
		this.first = first;
		this.last = last;
	}
	 // see all courses
	public void ViewAll() {
		for (int i=0; i < this.CourseList.size(); i++) {
			Course c = this.CourseList.get(i);
			System.out.println(c.getCourseName() + c.getId() + c.getTotal() + c.getMax());
		}
	}
	
	// max > total means not full
	public void ViewNotFull() {
		for (int i=0; i < this.CourseList.size(); i++) {
			Course c = this.CourseList.get(i);
			if (c.getMax() > c.getTotal()) {
				System.out.println(c.getCourseName() + c.getId() + c.getMax());
			}
		}
	}
	
	public int Register() {

		// ask for course
		System.out.println("What is the course section? ");
		int CourseSection = input.nextInt();
		input.nextLine();
		System.out.println("What is the course name? ");
		String CourseName = input.nextLine();	
		

		// look for course
		for (int i=0; i < this.CourseList.size(); i++) {
			Course c = this.CourseList.get(i);
			if(c.getCourseName().equals(CourseName) && c.getSection() == CourseSection) {
				// if course not full
				if (c.getMax() > c.getTotal()) {
					System.out.println("What is your first name? ");
					String first = input.nextLine();
					System.out.println("What is your last name? ");
					String last = input.next();
					
					// add student to student list for the course
					CourseList.get(i).getStudentNames().add(new Student(first, last));
					// increment total
					CourseList.get(i).setTotal(CourseList.get(i).getTotal() + 1);
					System.out.println("Added");

					return 1;
				}
			}
		}
		System.out.print("Course full or not found");
		input.reset();
		return 0;
	}
		
	// withdraw from course
	public int Withdraw() {

		System.out.println("What is the course section? ");
		int CourseSection = input.nextInt();
		input.nextLine();
		System.out.println("What is the course name? ");
		String CourseName = input.nextLine();	
		
		
		// look through course list to find student name and course which corresponds to inputed name and section
		for (int i=0; i < this.CourseList.size(); i++) {
			Course c = this.CourseList.get(i);

			if(c.getCourseName().equals(CourseName) && c.getSection() == CourseSection) {
				
				System.out.println("What is your first name? ");
				String first = input.nextLine();
				System.out.println("What is your last name? ");
				String last = input.next();
				
				// remove from student name Array list of that course
				for (int j = 0; i<CourseList.get(i).getStudentNames().size(); i ++)
					if (CourseList.get(i).getStudentNames().get(j).getFirst().equals(first) && CourseList.get(i).getStudentNames().get(j).getLast().equals(last)) {
						CourseList.get(i).getStudentNames().remove(j);
						// decrement 
						CourseList.get(i).setTotal(CourseList.get(i).getTotal() - 1);
						System.out.println("Withdrawn");
						return 1;
					}	
			}
		}
		System.out.println("Student or course not found");
		return 0;
	}
	
	public void ViewCoursesWithStudent(String first, String last) {
		// for every course
		for (int i = 0; i<this.CourseList.size(); i ++) {
			
			Course c = this.CourseList.get(i);
			
				for (int j = 0; j<c.getStudentNames().size(); j ++){
					Student s = c.getStudentNames().get(j);

					// if student in course
					if (s.getFirst().equals(first) && s.getLast().equals(last)) {
						// print course name
						System.out.println(c.getCourseName());
					}
				}
		}
		
	}
	
	public void showMenu() {
		 System.out.println("Here are your options:");
		 System.out.println("1. View all courses");
		 System.out.println("2. View courses that are not full");
		 System.out.println("3. register on a course");
		 System.out.println("4. Withdraw");
		 System.out.println("5. View courses registered in");
		 System.out.println("6. Quit");
		 
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
