import java.util.ArrayList;

public class User {
	protected String username;
	protected String password;
	protected String first;
	protected String last;
	
	// list of students not put into a class
	private ArrayList <Student> StudentList = new ArrayList<Student>();
	// list of courses
	private ArrayList<Course> CourseList = new ArrayList<Course>();
	
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

	public User() { // default user constructor
		this.username = "username";
		this.password = "password";
		this.first = "First";
		this.last = "Last";
	}
	
}
