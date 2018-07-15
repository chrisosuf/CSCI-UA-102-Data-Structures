
public interface StudentInterface {

	public void ViewAll(); // override with admin
	public void ViewNotFull();
	
	public int Register(); // 1 if successful
	public int Withdraw();
	
	public void ViewCoursesWithStudent(String first, String last);
	
	public void showMenu();
	
}
