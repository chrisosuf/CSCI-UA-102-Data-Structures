
public class Customer {
	
	
	private int id;
	// time got there
	private int arrivalTime;
	// waiting time before being served
	private int waitingTime;
	
	// The time they got served
	private int TimeGotServed;
	// time they finished being served: Wait time + T (time it takes to serve)
	private int TimeFinished;
	
	public Customer() {
		// customer variables and times initialized at 0
		id = 0;
		arrivalTime =  0;
		waitingTime =  0;
		TimeGotServed = 0;
		TimeFinished = 0;
	}
	
	public Customer(int a, int ID) {
		arrivalTime =  a;
		id = ID;
		waitingTime =  0;
		TimeGotServed = 0;
		TimeFinished = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getTimeGotServed() {
		return TimeGotServed;
	}

	public void setTimeGotServed(int timeGotServed) {
		TimeGotServed = timeGotServed;
	}

	public int getTimeFinished() {
		return TimeFinished;
	}

	public void setTimeFinished(int timeFinished) {
		TimeFinished = timeFinished;
	}	
	
}
