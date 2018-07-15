
public class FriendsList {
	private Friend first;
	
	// friend class are nodes of FriendsList Linked List
	class Friend{
		// data held is ss of friends
		public int ss;
		public Friend next;
		
		public Friend(int ss) {
			this.ss = ss;
		}
		
		// Friend node getters and setter
		public int getSs() {
			return ss;
		}
		public void setSs(int ss) {
			this.ss = ss;
		}
		public Friend getNext() {
			return next;
		}
		public void setNext(Friend next) {
			this.next = next;
		}
		
	}
	
	// add first method
	public void addFirst(int ss) {
		Friend temp = new Friend(ss);
		// if empty list
		if (first == null) {
			first = temp;
		}
		// make it point to first and update first
		else {
			temp.next = first;
			first = temp;
		}
	}
	
	// display friends SS numbers
	public void displayList() {
		Friend current = first;
		// while not at end of list
		while (current != null) {
			// print and move to next friend
			System.out.print(current.getSs() + ", ");
			current = current.next;
		}
	}
	
	// see if ss exists in friends list
	public boolean find(int ss) {
		// if has no friends, return false
		if (first == null) {
			return false;
		}
		Friend current = first;
		while (current!= null) {
			// while iterating, if a match occurs, return true
			if (current.getSs() == ss) {
				return true;
			}
			current = current.next;
		}
		// if get to end, not found, return false
		return false;
	}
	
	
	
	
}
