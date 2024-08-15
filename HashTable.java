/**
 * HashTable class creates a hashtable that utilizes separate chaining to handle collisions. 
 * It includes three nested classes to help with the implementation of separate chaining, these classes include Node, Link and SingleLinkList.
 * 
 * @author Brandon Rocha
 *@version December 7, 2022
 */
public class HashTable {
	
	private SingleLinkList[] hasharray;
	private int arraySize;
	
/**
 * Constructor that creates an array of empty SingleLinkList to become the hashtable.
 * 	
 * @param size of type int to indicate the size of the array 
 */
	HashTable (int size) {
		arraySize = size;
		hasharray = new SingleLinkList[arraySize];
		for (int j = 0; j<arraySize; j++) {
			hasharray[j] = new SingleLinkList();
		}
	}//end constructor
/**
 * Method to display the contents of the hashtable, including empty indexes.
 * 	
 */
	public void displayTable() {
		for(int j = 0; j < arraySize; j++ ) {
			System.out.printf("%3d. ", j);
			hasharray[j].displayList();
		}
	}//end displayTable
/**
 * Method that calculates the hash value of a country to be placed into the hashtable based on the Unicode value of its name. 
 * 	
 * @param key of type String that is the name of the country to be hashed
 * @return keyValue % 293 of type int is the hash value of the given countries name
 */
	public int hashFunction(String key) {
		int keyValue = 0;
		
		for(int i = 0; i < key.length(); ++i) {
			keyValue += (int)key.codePointAt(i);
		}
		return keyValue % 293;
	}//end hashFunction
/**
 * Method that uses the calculated hash value to insert a country into the hashtable, placing it last in the list for that index if a collision occurs.
 * It also includes the country's COVID case rate. 
 * 
 * @param name of type String is the name of the Country to be inserted
 * @param population of type long that is the population of the country
 * @param cases of type long that is the number of COVID cases in that country
 */
	public void insert(String name, long population, long cases) {
		String key = name;
		int hashValue = hashFunction(key);
		hasharray[hashValue].insertLast(name, population, cases);
	}//end insert
/**
 * Method that searches the hashtable for a country of a user defined name to be deleted from the hashtable. 
 * 	
 * @param countryName of type String is the name of the country to be deleted. 
 */
	public void delete(String countryName) {
		int hashValue = hashFunction(countryName);
		hasharray[hashValue].delete(countryName);
	}//end delete
/**
 * Method that finds a country in the hashtable using a user defined name and prints the index of the country as well as its COVID case rate.
 * 	
 * @param countryName of type String is the name of the country to be searched for
 * @return caseRate of type double is the COVID case rate of the country that was found
 */
	public double find(String countryName) {
		int hashValue = hashFunction(countryName);
		double caseRate = hasharray[hashValue].find(countryName);
		return caseRate;
	}
/**
 * Method that prints the total number of empty cells that are in the hashtable as well as how many collided cells there are. 	
 */
	public void collEmpty() {
		int empty = 0;
		int collision = 0;
		for(int j = 0; j < arraySize; j++ ) {
			if (hasharray[j].isEmpty()) {
				++empty;
			}
			else if (hasharray[j].emptyCol() > 1){
				collision += 1;
			}
		}
		System.out.printf("Empty: %d\n", empty);
		System.out.printf("Collisions: %d\n", collision);
	}
	
/**
 * Node class is a nested class within the HashTable class that holds all pertinent data related to a country. 
 * This class was provided by the professor and was not altered. 
 * 
 * @author Liu Xudong
 *
 */
	private class Node {
		String name;
		long population;
		long cases;
		Node nextNode;
/**
 * Constructor that creates a node to hold pertinent data of a country. 
 * 
 * @param name of type String that is the name of a country
 * @param population of type long that is the population of a country
 * @param cases of type long that is the COVID cases of a country
 */
		public Node(String name, long population, long cases) {
			this.name = name;
			this.population = population;
			this.cases = cases;
			}
/**
 * Method that prints the name and COVID case rate of a node. 
 * 		
 */
		public void printNode() {
			System.out.printf("%-30s %-20.3f\n", name,
					(double)cases/population*100000);
		 	}
	}//end Node
/**
 * Link class is a nested class within the HashTable class that creates a link and the data within the link is a node. 
 * It is also used to help implement a SingleLinkList to implement separate chaining when collisions occur. 
 * 	
 * @author Brandon Rocha
 * @version December 7, 2022
 *
 */
	private class Link {
		private Node countryData;
		public Link next;
/**
 * Constructor that creates a link with a node as its data contents. 
 * 		
 * @param data of type Node that holds the country name, population and COVID cases.
 */
		public Link(Node data) {
			countryData = data;
		}
/**
 * Method that returns the name of a country within a link.
 * 		
 * @return of type string that is the name of a country
 */
		public String getKey() {
			return countryData.name;
		}
	}//end Link
/**
 * SingleLinkList is a nested class within HashTable that creates a double-ended singly linked list.
 * 	
 * @author Brandon Rocha
 * @version December 7, 2022
 *
 */
	private class SingleLinkList {
		private Link first;
		private Link last;
/**
 * Constructor that creates an empty double-ended singly linked list.		
 */
		SingleLinkList() {
			first = null;
			last = null;
		}
/**
 * Method that indicates if a linked list within the hashtable is empty. 
 * 	
 * @return of type boolean returns true if the linked list is empty, otherwise it returns false. 
 */
		public boolean isEmpty() {
			return first==null;
		}
/**
 * Method that inserts a country at the end of a linked list. 
 * 		
 * @param name of type String that is the name of the country to be inserted
 * @param population of type long that is the population of the country to be inserted
 * @param cases of type long that is the COVID cases of the country to be inserted
 */
		public void insertLast(String name, long population, long cases) {
			Node newNode = new Node(name, population, cases);
			Link newLink = new Link(newNode);
			
			if ( isEmpty() ) {
				first = newLink;
			}
			else {
				last.next = newLink;
			}
			last = newLink;
		
		}
/**
 * Method that searches the linked list for a country based on its name
 * 		
 * @param key of type String that is the name of the country to be searched for
 * @return of type double that is the COVID case rate of the country that was found
 */
		public double find(String key) {
			Link current = first;

			while( !current.countryData.name.equals(key) ) {
				if (current.next == null) {
					return -1;
				}
				else {
					current = current.next;
				}
			}
			
			return (double)current.countryData.cases/current.countryData.population*100000;
		}//end find
/**
 * Method that deletes a country from the linked list based on its name
 * 		
 * @param key of type String is the name of the country to be deleted
 */
		public void delete(String key) {
			Link previous = null;
			Link current = first;
			
			while (current!= null && !key.equals(current.getKey()) ) {
				previous = current;
				current = current.next;
			}
			if (current == null) {
				System.out.printf("%s does not exist in hashtable.\n", key);
				return;
			}
			
			if(previous == null) {
				first = first.next;
			}
			else {
				previous.next = current.next;
			}
			System.out.printf("%s was deleted from the hashtable.\n", key);
			
		}//end delete
/**
 * Method that displays the contents of a linked list or prints "Empty" if the linked list is empty. 
 * 		
 */
		public void displayList() {
			Link current = first;
			
			if(current == null) {
				System.out.printf("Empty");
				System.out.println();
			}
			
			while (current != null) {
				current.countryData.printNode();
				current = current.next;
				if(current != null) {
					System.out.printf("     ");
				}
			}
		}
/**
 * Method that returns the amount of countries present in a linked list. 
 * 		
 * @return coll of type int that indicates the amount of countries present in a linked list
 */
		public int emptyCol() {
			int coll = 0;
			Link current = first;
			
			while (current != null) {
				current = current.next;
				++coll;
			}
			return coll;
		}

	}//end SingleLinkList
	
	
	
}//end HashTable
