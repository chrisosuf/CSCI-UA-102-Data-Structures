
/**
 * 
 * @author chrisosufsen
 * Worked with Connor Brady
 * Citing Code: Tree implementation similar to code Professor Bari has in tree.java file on website
 */

public class CommunityTree {
	//binary search tree based on Person ID's
	private Node root;
	
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}


	class Node{
		// data is person object
		public Person person;
		// children
		public Node left;
		public Node right;
		
		public Node(Person s) {
			this.person = s;
		}
	}
	
	
	// add new person to the tree based on SS
	public void add(Person p) {
		// make new Node
		Node newNode = new Node(p);
		
		if (root == null) {
			root = newNode;
		}
		else {
			// start at root
			Node current = root;
			Node parent;
			while(true) {
				parent = current;
				// go left if ss is less
				if (newNode.person.getSs()<current.person.getSs()) {
					current = current.left;
					// make parent child the new node
					if (current == null) {
						parent.left = newNode;
						return;
					}
				}
				else {
					// if greater than, go left
					current = current.right;
					// make parent child the new node
					if (current == null) {
						// set new child node and return
						parent.right = newNode;
						return;
					}
				}
				
			}
		}
	}
	
	
	// transpose in order to view the elements in the tree
	// use inOrder traversal for most of these methods so that the output lists the full names in increasing order by SS
	public void DisplayInOrder(Node root){
	    if(root != null){
	    		// first recurive call, followed by print and second call to preserve order
	    		// from left subtree to right
	    		DisplayInOrder(root.left);
	        System.out.print(root.person.getSs() + " ");
	        DisplayInOrder(root.right);
	    }
    }
	
	// check if half siblings through transposing in order
	// if one of their parents are the same, they are half sibling
	public void halfSiblings(Person p, Node root){
		if(root != null){
			// check left side of tree and root
			halfSiblings(p, root.left);
			// if mother or father are the same and the person is not the same person we are passing
	        if ((root.person.getFather() == p.getFather() || root.person.getMother() == p.getMother()) && p != root.person) {
	        		//print their name
	        		System.out.print(root.person.getFullname() + ", ");
	        }
	        // right recursive call
	        halfSiblings(p, root.right);
		 }
		
	}
	
	// same logic as half siblings, but check if both parents are the same
	public void fullSiblings(Person p, Node root){
		if(root != null){
			// check left side of tree and root
			fullSiblings(p, root.left);
			// if mother and father are the same and the person is not the same person we are passing
	        if ((root.person.getFather() == p.getFather() && root.person.getMother() == p.getMother()) && p != root.person) {
	        		//print their name
	        		System.out.println(root.person.getFullname()+ ", ");
	        }
	        // right recursive call
	        fullSiblings(p, root.right);
		 }
		
	}
	
		// same logic as 2 above, but check if person passing is the mother or father of current person looking at
		public void Children(Person p, Node root){
			if(root != null){
				// check left side of tree and root
				Children(p, root.left);
				// if mother and father are the same and the person is not the same person we are passing
		        if ((root.person.getMother() == p.getSs() || root.person.getFather() == p.getSs()) && p != root.person) {
		        		//print their name
		        		System.out.print(root.person.getFullname()+ ", ");
		        }
		        // right recursive call
		        Children(p, root.right);
			 }
		}
	
	
	// loop through each, search by SS
	public Person find(int ss) {
		Node current = root;
		while  (current.person.getSs() != ss) {
			if (ss < current.person.getSs()) {
				current= current.left;
			}
			else {
				current = current.right;
			}
			// node has no child
			if (current == null) {
				return null;
			}
		}
		return current.person;
	}
	
	public void InverseFriends(Person p, Node root) {
		if(root != null){
			// check left side of tree and root
			InverseFriends(p, root.left);
			// if mother and father are the same and the person is not the same person we are passing
			if (root.person.getFriends().find(p.getSs())) {
				System.out.print(root.person.getFullname()+ ", ");
			}
	        // right recursive call
			InverseFriends(p, root.right);
		 }
		
	}
	
	public void MututalFriends(Person p, Node root) {
		if(root != null){
			// check left side of tree and root
			MututalFriends(p, root.left);
			// if both are present in each others friends lists
			if (root.person.getFriends().find(p.getSs()) && p.getFriends().find(root.person.getSs())) {
				System.out.print(root.person.getFullname()+ ", ");
				//p.setMutualFriends(p.getMutualFriends() + 1);
			}
	        // right recursive call
			MututalFriends(p, root.right);
		 }	
	}
	

	
	// method to set how many mutual friends they have to the correct amount
	public void CountMutualFriends(Person p, Node root) {
		if(root != null){
			// check left side of tree and root
			CountMutualFriends(p, root.left);
			// if both are present in each others friends lists
			if (root.person.getFriends().find(p.getSs()) && p.getFriends().find(root.person.getSs())) {
				p.setMutualFriends(p.getMutualFriends() + 1);
			}
	        // right recursive call
			CountMutualFriends(p, root.right);
		 }
	}
	
	// apply the above method for all, updating their total mutual friends correctly
	public void ApplyAllMutualFriends(Node root) {
		if(root != null){
			ApplyAllMutualFriends(root.left);
			CountMutualFriends(root.person, root);
			ApplyAllMutualFriends(root.right);
		}
	}
	
	// find which has the greatest amount of friends{
	public int maxMutualFriends(Node root) {
		if(root != null){
			int mutual = root.person.getMutualFriends();	
			// retrun the max of the max of each sub tree and the current mutual friends amount 
			return Math.max(Math.max(maxMutualFriends(root.left), maxMutualFriends(root.right)), mutual);
		}
		else {
			
			return -1;
		}
	}
	
	// assumes mutual friends amount method has been applied
	// so that mutual friends count is updated for all persons
	// where max is the max amount of mututal friends
	// use this method to find the corresponding person(s) with that many mututal friends
	// start off with boolean value set to false, so that it can only find person with smallest ID if multiple people have the max
	// In order traversal makes sure we go in order of values, so this works^
	public void maxMutualSS(int max, Node root, boolean Found) {
		if(root != null){
			// check left side of tree and root
			maxMutualSS(max, root.left, Found);
			// if mutual friends amount is same
			if (root.person.getMutualFriends() == max) {
				if (Found == false) {
					System.out.print(root.person.getFullname()+ ", ");
					Found = true;
				}
				
			}
			
	        // right recursive call
			maxMutualSS(max, root.right,Found);
		 }
	}
	
	
}
