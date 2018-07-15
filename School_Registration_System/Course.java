import java.util.ArrayList;
import java.io.Serializable;


public class Course implements Comparable <Course>, Serializable{

	private String courseName;
	private String id; // course id
	private int max; // max amount of students in course
	private int total; // total amount of students registered in course
	private ArrayList<Student>studentNames; // list of students in the course
	private String instructor;
	private int section; // course section
	private String location;
	

	// default Course constructor
	public Course() {
		this.courseName = "Course_Name";
		this.id = "CLASS101";
		this.max = 30;
		this.total = 25;
		this.studentNames = new ArrayList<Student>(); // empty arraylist
		this.instructor = "Course_Instructor";
		this.section = 0;
		this.location = "Course Location";
	}
	
	// constructor for existing course
	public Course(String courseName, String id, int max, int total, ArrayList<Student>studentNames, String instructor, int section, String location){
		this.courseName = courseName;
		this.id = id;
		this.max = max;
		this.total = total;
		this.studentNames = studentNames;
		this.instructor = instructor;
		this.section = section;
		this.location = location;
	}
	
	// constructor for new course with null student names list
	public Course(String courseName, String id, int max, int total, String instructor, int section, String location){
		this.courseName = courseName;
		this.id = id;
		this.max = max;
		this.total = total;
		this.studentNames = new ArrayList<Student>(); // empty arraylist
		this.instructor = instructor;
		this.section = section;
		this.location = location;
	}
	
	// constructor for new Course
	public Course(String courseName, String id, int max, String instructor, int section, String location){
		this.courseName = courseName;
		this.id = id;
		this.max = max;
		this.instructor = instructor;
		this.section = section;
		this.location = location;
		
		// set by default, new course doesn't have students yet
		this.total = 0;
		this.studentNames = new ArrayList<Student>(); // empty arraylist
	}
	
	// sorting by course name
	@Override
    public int compareTo(Course other) {
        if(this.getCourseName().compareTo(other.getCourseName()) > 0){
        		return 1;
        }
        else if (this.getCourseName().compareTo(other.getCourseName()) < 0) {
        		return -1;
        }
        else {
        	return 0;
        }
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<Student> getStudentNames() {
		return studentNames;
	}

	public void setStudentNames(ArrayList<Student> studentNames) {
		this.studentNames = studentNames;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

	
}
