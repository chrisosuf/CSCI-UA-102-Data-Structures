import java.io.*;
import java.util.ArrayList;


/**
 *
 * @author chrisosufsen
 *
 * Worked with Connor Brady
 * Citing code: Osufsen_HW4: File and argument reading similar to how I did in homework 4
 *
 * Run this program by compiling Program.java, then writing java Program communityfile.txt queriesfile.txt
 *
 */


public class Program {

	public static void main(String[] args) {

		//Loading the data into Person Objects

		String communityFile = (System.getProperty("user.dir")+"/"+args[0]);
		String queryFile = (System.getProperty("user.dir")+"/"+args[1]);

		ArrayList<String> communityList = new ArrayList<String>();
		ArrayList<String> queryList = new ArrayList<String>();

		//References one line at a time
		// community file
		String line = null;
		try{
			FileReader CommfileReader = new FileReader(communityFile);
			BufferedReader bufferedReader = new BufferedReader(CommfileReader);
			while((line = bufferedReader.readLine()) != null) {
					if (!(line.equals(""))) {
						// add the line of text to our list
						communityList.add(line);

					}
			}
			bufferedReader.close();
		}
		// catch
		catch(FileNotFoundException ex){
			System.out.println( "Unable to open file '" + communityFile + "'");
			ex.printStackTrace();
		}
		catch (IOException ex) {
			System.out.println( "Error reading file '" + communityFile + "'");
			ex.printStackTrace();
		}

		// query file
		String queryLine = null;
		try{
			FileReader QueryfileReader = new FileReader(queryFile);
			BufferedReader bufferedReaderQ = new BufferedReader(QueryfileReader);
			while((queryLine = bufferedReaderQ.readLine()) != null) {
					if (!(queryLine.equals(""))) {
						// add the line of text to our list
						queryList.add(queryLine);

					}
			}
			bufferedReaderQ.close();
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



		// creating the person objects and putting them into an arrayList
		ArrayList<Person> personList = new ArrayList<Person>();

		for (int i = 0; i<communityList.size(); i+=1) {
			// parse just the values
			communityList.set(i, communityList.get(i).replaceAll(".*: ", ""));
		}

		// assume format is consistent: friends are separated by commas with no spaces coming after a space following the colon
		// for every 6 elements in community list
		for (int i = 0; i<communityList.size(); i+=6) {

			// if they have any friends
			// assuming if they have no freinds, community list data field for friends is "FRIENDS:"
			if (!communityList.get(i+5).equals("FRIENDS:")) {
				// make an array of the friends social security numbers, then make a new array where they are integers
				String[] friendsListStr = communityList.get(i+5).split(",");

				// converting the string SS of friends to integer
				// and make linked list (friendList) of friends

				FriendsList friendsList = new FriendsList();
				for (int j = 0; j<friendsListStr.length; j++) {
					friendsList.addFirst(Integer.parseInt(friendsListStr[j]));
				}

				// create the person object
				Person p = new Person(communityList.get(i), communityList.get(i+1), Integer.parseInt(communityList.get(i+2)), Integer.parseInt(communityList.get(i+4)),Integer.parseInt(communityList.get(i+3)), friendsList);
				personList.add(p);
			}
			// if they have no friends, pass an empty linked list as friends list
			else {
				FriendsList noFriends = new FriendsList();
				Person p = new Person(communityList.get(i), communityList.get(i+1), Integer.parseInt(communityList.get(i+2)), Integer.parseInt(communityList.get(i+4)),Integer.parseInt(communityList.get(i+3)), noFriends);
				personList.add(p);
			}

		}

		// ------------------------------ Program ---------------------------------

		CommunityTree ct = new CommunityTree();
		for (int i = 0; i < personList.size(); i++){
			// add person objects to the Community tree
			ct.add(personList.get(i));
		}

		// update all the mututal friends amounts
		ct.ApplyAllMutualFriends(ct.getRoot());

		// -------------------Take in Queries ---------------------


		// preparing to take the whole output as byte stream, so we can replace any empty queries, with "UNAVAILABLE"
		// byte stream to hold data in that we output
		ByteArrayOutputStream hold = new ByteArrayOutputStream();
	    PrintStream print = new PrintStream(hold);
	    // save the current console output
	    PrintStream old = System.out;
	    // prepare to take in next output
	    System.setOut(print);

		// taking in querys and print result

		for (int i = 0; i<queryList.size(); i ++) {

			if (queryList.get(i).contains("NAME-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				// convert into an integer
				int ss = Integer.parseInt(str);
				// makes sure the query is available
				if (ct.find(ss)!= null) {
					System.out.println(queryList.get(i) + ": " + ct.find(ss).getFullname());
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}

			}
			else if (queryList.get(i).contains("FATHER-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				int ss = Integer.parseInt(str);
				// find father Id, and find first name of that ID
				if (ct.find(ss)!= null) {
					System.out.println(queryList.get(i) + ": " + ct.find(ct.find(ss).getFather()).getFullname());
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}
			}
			else if (queryList.get(i).contains("MOTHER-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				int ss = Integer.parseInt(str);
				// find mother Id, and find first name of that ID
				if (ct.find(ss)!= null) {
					System.out.println(queryList.get(i) + ": " + ct.find(ct.find(ss).getMother()).getFullname());
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}
			}
			else if (queryList.get(i).contains("HALF-SIBLINGS-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				int ss = Integer.parseInt(str);
				// call half siblings method on node with that ss and root
				if (ct.find(ss)!= null) {
					System.out.print(queryList.get(i) + ": ");
					ct.halfSiblings(ct.find(ss), ct.getRoot());
					// print an empty string for display space
					System.out.println("");
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}

			}
			else if (queryList.get(i).contains("FULL-SIBLINGS-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				int ss = Integer.parseInt(str);
				// call full siblings method on node with that ss and root
				if (ct.find(ss)!= null) {
					System.out.print(queryList.get(i) + ": ");
					ct.fullSiblings(ct.find(ss), ct.getRoot());
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}
			}
			else if (queryList.get(i).contains("CHILDREN-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				int ss = Integer.parseInt(str);
				// call Children method on node with that ss and root
				if (ct.find(ss)!= null) {
					System.out.print(queryList.get(i) + ": ");
				    // find children
					ct.Children(ct.find(ss), ct.getRoot());

					System.out.println("");
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}
			}
			else if (queryList.get(i).contains("MUTUAL-FRIENDS-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				int ss = Integer.parseInt(str);
				// call mutual friends method on node with that ss and root
				if (ct.find(ss)!= null) {
					System.out.print(queryList.get(i) + ": ");
					ct.MututalFriends(ct.find(ss), ct.getRoot());
					System.out.println("");
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}
			}
			else if (queryList.get(i).contains("INVERSE-FRIENDS-OF")) {
				String str = queryList.get(i).replaceAll("[^0-9]", "");
				int ss = Integer.parseInt(str);
				// call inverse friends method on node with that ss and root
				if (ct.find(ss)!= null) {
					System.out.print(queryList.get(i) + ": ");
					ct.InverseFriends(ct.find(ss), ct.getRoot());
					System.out.println("");
				}
				else {
					System.out.println(queryList.get(i) + ": ");
				}
			}
			else if (queryList.get(i).contains("WHO-HAS-MOST-MUTUAL-FRIENDS")) {
				System.out.print(queryList.get(i) + ": ");
				// find the max
				int max = ct.maxMutualFriends(ct.getRoot());
				// call method to find corresponding person(s) with most mutual friends

				// set found to false because not found yet, ensures only one with smallest SS is printed if both have max
				//(see method in communityTree class file)
				boolean found = false;
				ct.maxMutualSS(max, ct.getRoot(),found);
			}
			else {
				System.out.println("INVALID QUERY");
			}
		}

	    // Put new output back into hold
	    System.out.flush();
	    System.setOut(old);
	    // now go thru new output and look for any line where a semicolon followed by a space is the last string
	    // this signifies that nothing was printed, meaning it was unavailable
	    // store the output in an array, where the whole string is split by new line
	    String [] myOutput = hold.toString().split("\n");
	    for (int i = 0; i < myOutput.length; i ++) {
	    		// if last 2 characters are together a string that is ": ", the query was unavailable
	    		if(myOutput[i].substring(myOutput[i].length()-2).equals(": ")){
	    			// add unavailable to the end of it
	    			myOutput[i] = myOutput[i] + "UNAVAILABLE";
	    		}
	    		// print the new line
	    		System.out.println(myOutput[i]);
	    }

	}

}
