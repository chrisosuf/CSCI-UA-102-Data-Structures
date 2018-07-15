import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chrisosufsen
 * (Worked with Connor Brady)
 * To run this from the command prompt: include the arguments on the command line after Program.java
 */



public class Program {
	public static void main(String[] args) {

		// ----------------- Loading the data into objects/variables -----------------

		ArrayList<String> myList = new ArrayList<String>();

		String fileName = (System.getProperty("user.dir")+"/"+args[0]);
		//References one line at a time
		String line = null;
		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
					if (!(line.equals(""))) {
						// add the line of text to our list
						myList.add(line);
					}
			}
			bufferedReader.close();
		}
		// catch
		catch(FileNotFoundException ex){
			System.out.println( "Unable to open file '" + fileName + "'");
			ex.printStackTrace();
		}
		catch (IOException ex) {
			System.out.println( "Error reading file '" + fileName + "'");
			ex.printStackTrace();
		}

		// ----------------- Loading the data into objects/variables -----------------

		// arrayLists to add arrivalTime and ID
		ArrayList<Integer> IDlist = new ArrayList<Integer>();
		ArrayList<Date> dateList = new ArrayList<Date>();
		ArrayList<Long> timeInSeconds = new ArrayList<Long>();

		// making a date object for when the day started
		SimpleDateFormat myFormat = new SimpleDateFormat("hh:mm:ss");
		Date beginning = null;
		try {
			beginning = myFormat.parse("00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// time it takes to serve one customer
		int serveTime = Integer.parseInt(myList.get(0));

		// starting at first customer ID, parse the ID and the time after it, skip by 2 to next ID
		for (int i = 1; i<myList.size(); i+=2) {
			// convert ID into int to be assigned to customers
			// remove anything that is not number, parsing the ID
			String str = myList.get(i).replaceAll("[^0-9]", "");
			// convert into an integer
			int ID = Integer.parseInt(str);
			IDlist.add(ID);

			// parsing the date
			// parse anything that doesnt include a number or :, parsing the time of arrival
			String str2 = myList.get(i+1).replaceAll("[^0-9]+:", "");

			//converting that into a datetime object
		    Date date = null;
		    try {
				date = myFormat.parse(str2);
				// add the date objects to an ArrayList
				dateList.add(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    // convert into seconds
		    Long seconds = (date.getTime() - beginning.getTime()) / 1000;
		    timeInSeconds.add(seconds);

		}

		// SPECIFYING AM AND PM
		// we are guaranteed that the data is in order
		// all date objects are AM time to begin with
		// go through the dates and look for a point where the time is no longer increasing
		// It has to cross noon in order to go to PM
		int start = 0;
		// go through all dates
		for (int i = 0; i < timeInSeconds.size() -1; i ++) {
			// see if the next date is smaller
			if (timeInSeconds.get(i+1) < timeInSeconds.get(i)) {
				// if so, save where that happened, and exit
				start = i + 1;
				break;
			}
		}
		// if there were any PM customer arrivals in the first place
		if (start != 0){
			// add 12 hours to them (43200 seconds)
			for (int i = start; i < timeInSeconds.size(); i ++) {
				timeInSeconds.set(i, timeInSeconds.get(i) + 43200);
			}
		}

		// array of our customer objects
		Customer [] customers = new Customer[timeInSeconds.size()];
		for (int i = 0; i < customers.length; i ++) {
			// load the arrival time and ID into a customer object and add to the array
			// use intValue() to get the int values for the customer member variables
			customers[i] = new Customer(timeInSeconds.get(i).intValue(), IDlist.get(i).intValue());
		}

		// ----------------- Starting the Program -----------------


		// MAYBE DONT NEED PREVIOUS VARIABLE IN LINKED LIST
		Queue queue = new Queue();
		queue.setServeT(serveTime);
		// utilize iterator for testing purposes:
		Queue.QueueIterator iter = queue.new QueueIterator();

		// Make early customers initial waiting time the open time - arrival time
		for (int i = 0; i < customers.length; i++) {
			if (customers[i].getArrivalTime() < queue.getOpen()) {
				// waiting time
				customers[i].setWaitingTime(queue.getOpen() - customers[i].getArrivalTime());
			}
		}

		int MaxQueueLength = 0;
		// Program to run the entire day automatically
		for (int i = 0; i < customers.length; i++) {
			// put iterator in the first position for removal testing
			iter.setPosition(null);
			iter.setPrevious(null);

			// what if arrives at a time where someone in the middle of the line will get served

			// specify removals
			// if person arrives after open
			if (customers[i].getArrivalTime() >= queue.getOpen()) {
				// if they arrive after the last person is finished (and the queue isnt empty)
				if (queue.getMaxQueueLength() != 0 && queue.getLast().getData().getTimeFinished() < customers[i].getArrivalTime()) {

					// clear the queue, everyone before them has finished getting served
					while (queue.getQueueSize() != 0) {
						queue.removeFirst();
					}
				}
				// if arrive before last person finishes, check how many people would be done by then
				else {
					while (queue.getQueueSize() != 0) {
						// check if this person in line would finish before they arrived
						// iterator starts at null, checks the next node
						if (queue.getFirst().getData().getTimeFinished() < customers[i].getArrivalTime()) {
							queue.removeFirst();
						}
						else {
							break;
						}
					}
					// iterate through queue to see which people in line will be finished by the time this person arrives
				}
			}

			// if nobody is ahead of the customer
			if (queue.getQueueSize() == 0) {
				// waiting time += 0
				// time they get served is arrival time + waiting time (add waiting time in case they got there early)
				customers[i].setTimeGotServed(customers[i].getArrivalTime() + customers[i].getWaitingTime());
				// Finish Time: time they got served + time it takes to get served
				customers[i].setTimeFinished(customers[i].getTimeGotServed() + queue.getServeT());
				queue.addLast(customers[i]);
			}
			else {
				if (queue.getQueueSize() == 1) {

					if (customers[i].getArrivalTime() < queue.getOpen()) {
						// if one person is ahead, and they arrived early:
						// they get served right after the person before them (who also arrived early), after the time it takes to get served
						customers[i].setWaitingTime(customers[i].getWaitingTime() + queue.getServeT());
					}
					// customer did not arrive early
					else {
						// if didnt arrive early (waiting time initialized at zero)
						// customers waiting time increments by the time left of person ahead (the first person's finish time - the time the next customer arrived)
						customers[i].setWaitingTime(queue.getFirst().getData().getTimeFinished() - customers[i].getArrivalTime());
					}
					// get the finish time of customer in front is start time of next person (first because queue size is 1)
					customers[i].setTimeGotServed(queue.getFirst().getData().getTimeFinished());
					customers[i].setTimeFinished(customers[i].getTimeGotServed() + queue.getServeT());
					queue.addLast(customers[i]);

				}

				// multiple people in line already
				else if (queue.getQueueSize() > 1) {

					// if arrived early, but multiple people arrived even EARLIER than this person
					if (customers[i].getArrivalTime() < queue.getOpen()) {
						// for all customers ahead of this one, this person will wait the serve time for each
						for (int k = 0; k<queue.getQueueSize(); k++) {
							customers[i].setWaitingTime(customers[i].getWaitingTime() + queue.getServeT());
						}
					}
					else {

						// for all customers ahead of this one EXCEPT the first one, this person will wait the serve time for each
						// do this by setting k = 1 in the loop
						for (int k = 1; k<queue.getQueueSize(); k++) {
							customers[i].setWaitingTime(customers[i].getWaitingTime() + queue.getServeT());
						}
						// they will also wait the amount of time the first one has left (on top of the time it takes to serve all those people behind the first one)
						customers[i].setWaitingTime(customers[i].getWaitingTime() + queue.getFirst().getData().getTimeFinished() - customers[i].getArrivalTime());
					}
					// time they got served is when last person in line is finished
					customers[i].setTimeGotServed(queue.getLast().getData().getTimeFinished());
					customers[i].setTimeFinished(customers[i].getTimeGotServed() + queue.getServeT());
					queue.addLast(customers[i]);
				}
			}

			// if arrive before 5, but serve Time > 5pm, wait time is 5pm - arrival time, and they dont get served
			if (customers[i].getArrivalTime() < queue.getClose() && customers[i].getTimeGotServed() > queue.getClose()) {
				customers[i].setWaitingTime(queue.getClose() - customers[i].getArrivalTime());
			}
			// if arrival time > 5pm, wait time is 0, dont get served
			else if(customers[i].getArrivalTime() > queue.getClose()) {
				customers[i].setWaitingTime(0);
			}
			// else, they did get served, add one to the queue total customers served variable
			else {
				queue.setTotalServed(queue.getTotalServed() + 1);
			}

			// if queue exceeded Queue Length
			if (queue.getQueueSize()>MaxQueueLength){
				MaxQueueLength = queue.getQueueSize();
				queue.setMaxQueueLength(MaxQueueLength);
				// if first is getting served, they will NOT be included in the queue line length (if they are about to get served, they will be)
				// ^ if the next person arrived right as the first person is about to get served, first will be included in the line length
				if (queue.getFirst() != null && queue.getFirst().getData().getTimeFinished() - customers[i].getArrivalTime() < queue.getServeT()) {
					// set max number of people in queue to new length if exceeds prior max
					MaxQueueLength -= 1;
					queue.setMaxQueueLength(MaxQueueLength);
				}
			}
		}


		int breakLength = 0;
		// Determining max and total break lengths

		for (int l = 0; l < queue.getTotalServed() - 1; l++) {
			if (customers[l].getTimeFinished() < customers[l + 1].getTimeGotServed()) {
				breakLength = customers[l+1].getTimeGotServed() - customers[l].getTimeFinished();
				queue.setTotalBreak(queue.getTotalBreak() + breakLength);
			}
			if (breakLength > queue.getMaxBreakLength()) {
				queue.setMaxBreakLength(breakLength);
			}
		}

		// if last customer served finishes before 5pm, add that break to total (from when they finished to 5pm)
		if (customers[queue.getTotalServed()-1].getTimeFinished() < queue.getClose()) {
			breakLength = queue.getClose() - customers[queue.getTotalServed()-1].getTimeFinished();
			queue.setTotalBreak(queue.getTotalBreak() + breakLength);

			if (breakLength > queue.getMaxBreakLength()) {
				queue.setMaxBreakLength(breakLength);
			}

		}

		// ----------------- Taking in the Queries -----------------

		ArrayList<String> queries = new ArrayList<String>();

		String queryFile = (System.getProperty("user.dir")+"/"+args[1]);
		//References one line at a time
		String queryLine = null;
		try{
			FileReader fileReader = new FileReader(queryFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((queryLine = bufferedReader.readLine()) != null) {
					if (!(queryLine.equals(""))) {

						// add the line of text to our list
						queries.add(queryLine);
					}
			}
			bufferedReader.close();
		}
		// catch
		catch(FileNotFoundException ex){
			System.out.println( "Unable to open file '" + queryFile + "'");
			ex.printStackTrace();
		}
		catch (IOException ex) {
			System.out.println( "Error reading file '" + queryFile + "'");
			ex.printStackTrace();
		}

		// print the queries
		for (int i =0; i<queries.size(); i++) {
			if (queries.get(i).equals("NUMBER-OF-CUSTOMERS-SERVED")) {
				System.out.println(queries.get(i) + ": " +queue.getTotalServed());
			}
			else if (queries.get(i).equals("LONGEST-BREAK-LENGTH")) {
				System.out.println(queries.get(i) + ": " +queue.getMaxBreakLength());
			}
			else if (queries.get(i).equals("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME")) {
				System.out.println(queries.get(i) + ": " + queue.getMaxQueueLength());
			}
			else if (queries.get(i).equals("TOTAL-IDLE-TIME")) {
				System.out.println(queries.get(i) + ": " +queue.getTotalBreak());
			}
			else if (queries.get(i).contains("WAITING-TIME-OF")) {
				String str = queries.get(i).replaceAll("[^0-9]", "");
				// convert into an integer
				int ID = Integer.parseInt(str);
				for (int j = 0; j < customers.length; j++)
					// if this the ID's match
					if (customers[j].getId() == ID) {
						System.out.println(queries.get(i) + ": " + customers[j].getWaitingTime());
					}

			}
		}

	}
}
