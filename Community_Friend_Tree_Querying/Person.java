import java.util.ArrayList;
public class Person {

	// member variables
	private String firstname;
	private String lastname;
	private String fullname;
	private int ss;
	private int father;
	private int mother;
	private FriendsList friends;
	// to count the amount of mutual friends they have
	private int mutualFriends = 0;

	// constructor
	// full name is first and last name together
	public Person(String first, String last, int ss,  int mother, int father, FriendsList friends) {
		this.firstname = first;
		this.lastname = last;
		this.ss= ss;
		this.father = father;
		this.mother = mother;
		this.friends = friends;
		this.fullname = firstname + " " + lastname;
	}

	// getters and setters
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getSs() {
		return ss;
	}

	public void setSs(int ss) {
		this.ss = ss;
	}

	public int getFather() {
		return father;
	}

	public void setFather(int father) {
		this.father = father;
	}

	public int getMother() {
		return mother;
	}

	public void setMother(int mother) {
		this.mother = mother;
	}

	public FriendsList getFriends() {
		return friends;
	}

	public void setFriends(FriendsList friends) {
		this.friends = friends;
	}

	public int getMutualFriends() {
		return mutualFriends;
	}

	public void setMutualFriends(int mutualFriends) {
		this.mutualFriends = mutualFriends;
	}


}
