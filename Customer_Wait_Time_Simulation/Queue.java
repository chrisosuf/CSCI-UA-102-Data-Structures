import java.util.NoSuchElementException;



// counter member variable for keeping track of queue length, put into the ad and remove methods

public class Queue {

	// node obj
	public class Node{
		// data is a customer object
		private Customer data;
		private Node next;
		
		// getters and setters for node
		public Customer getData() {
			return data;
		}
		public void setData(Customer data) {
			this.data = data;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
	}

	// queue keeps track of last and first element so we can do first in first out
	private Node first;
	private Node last;
	
	
	
	// open time = 9:00:00 in seconds
	private int open = 32400;
	// close time = 5pm in seconds
	private int close = 61200;
	// initialize time it takes to serve a customer as zero
	private int serveT = 0;
	// size of Queue starts at 0
	private int QueueSize = 0;
	// total people served
	private int totalServed = 0;
	// longest the line/queue ever got
	private int maxQueueLength = 0;
	// total and max breakLength
	int maxBreakLength = 0;
	int totalBreak = 0;

	
	public void removeFirst() {
		// cant remove from empty queue
		if (first == null) {
			throw new NoSuchElementException();
		}
		// make first the one that it is pointing to
		else {
			first = first.next;
			// Queue size is lowered by 1
			QueueSize -= 1;
		}
	}
	
	public void addLast(Customer obj) {
		// make the new node
		Node newNode = new Node();
		newNode.data = obj;
		
		// empty linked list
		if (first == null) {
			// last element points to null
			newNode.next = null;
			// with one element, first and last are both that element
			first = newNode;
			last = newNode;
		}
		else {
			// point last to new node
			last.next = newNode;
			// make last the new node
			last = newNode;
			// make it now point to null
			last.next = null;
		}
		// QueueSize is incremented by 1
		QueueSize += 1;
	}
	
	public QueueLinkedListIterator listIterator() {
		return new QueueIterator();
	}
	
	public class QueueIterator implements QueueLinkedListIterator{
		
		// position and previous variables
		private Node position;
		private Node previous;
		
		
		public QueueIterator() {
			position = null;
			previous = null;
		}
		
		
		// methods
		// if check if next by seeing if null
		public boolean hasNext() {
			if (position == null) 
				// When position is null, iterator is either at beginning or list is empty
				return first != null;
			else {
				// see if iterator is just at the end of the list
				return position.next != null;
			}
		}
		
		//iterator next method, move to next
		public Object next() {
			// if no next element, dont go next, cant
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			previous = position; // remember for remove
			// if iterator hasn't moved yet, at the beginning now, previous still at null
			// null doesnt have a next variable, so must manually make its position after the first node
			// iterator position is before the first node at this point: it's the FIRST time calling next
			if (position == null) {
				position = first; // make it first if only one element 
				// iterator position is at null
				// previous STAYS there
			}
			// make position node, the next node
			// not changing any nodes, just changing the position ur looking at
			//by changing position, a member variable of iterators
			else {
				position = position.next;
			}
			return position.data;
		}


		// getters and setters
		public Node getPosition() {
			return position;
		}
		public void setPosition(Node position) {
			this.position = position;
		}
		public Node getPrevious() {
			return previous;
		}
		public void setPrevious(Node previous) {
			this.previous = previous;
		}
		
	}
	
	// queue getters and setters
	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}

	public Node getLast() {
		return last;
	}

	public void setLast(Node last) {
		this.last = last;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public int getClose() {
		return close;
	}

	public void setClose(int close) {
		this.close = close;
	}

	public int getServeT() {
		return serveT;
	}

	public void setServeT(int serveT) {
		this.serveT = serveT;
	}

	public int getQueueSize() {
		return QueueSize;
	}

	public void setQueueSize(int queueSize) {
		QueueSize = queueSize;
	}

	public int getTotalServed() {
		return totalServed;
	}

	public void setTotalServed(int totalServed) {
		this.totalServed = totalServed;
	}

	public int getMaxQueueLength() {
		return maxQueueLength;
	}

	public void setMaxQueueLength(int maxQueueLength) {
		this.maxQueueLength = maxQueueLength;
	}

	public int getMaxBreakLength() {
		return maxBreakLength;
	}

	public void setMaxBreakLength(int maxBreakLength) {
		this.maxBreakLength = maxBreakLength;
	}

	public int getTotalBreak() {
		return totalBreak;
	}

	public void setTotalBreak(int totalBreak) {
		this.totalBreak = totalBreak;
	}
	
	

}
