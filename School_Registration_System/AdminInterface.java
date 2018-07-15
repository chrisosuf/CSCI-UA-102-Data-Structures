import java.util.ArrayList;

public interface AdminInterface {
	
	public Course CreateCourse();
	public int Delete(String id, int section); // return 1 if successful, 0 if not
	public int Edit(String id, int section); // 1 if successful
	public void DisplayInfo(String id, int section); // search by id, section
	public int RegisterStudent(Student s); 

	
	// display
	public void ViewAll(); // override with student
	public void ViewFull();
	
	public void Write(); 
	public void ViewStudentsInCourse(String id, int section); // find course based on id and return student names
	public void ViewCoursesWithStudent(String first, String last);
	public void SortCourses();

	// search for a course, to be used in some of the methods above
	public Course Search(String id, int section, ArrayList<Course> CourseList);
	
	// show menu for system
	public void showMenu();
}
