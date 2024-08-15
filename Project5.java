import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project5 creates a hashtable of countries utilizing separate chaining to handle collisions and countries are based on a file, Countries5.csv. 
 * Countries are hashed using their Unicode value and inserted last if a collision occurs. 
 * A menu will be repeatedly printed providing the user with different tasks to perform based on their input. Or they may enter 6 to exit the program.
 * 
 * 
 * @author Brandon Rocha
 *@version December 7, 2022
 */
public class Project5 {

	public static void main(String[] args) {
		Scanner openFl = new Scanner(System.in);
		System.out.print("Enter filename: ");
		String filename = openFl.next();
		HashTable hashtable = new HashTable(293); 

		
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(filename));
		}
		catch(FileNotFoundException e ){
			System.out.println("Unable to open the file: "+filename);
			System.exit(1);
		}
		inFile.useDelimiter(",|\n");
		//get through the first line
		inFile.nextLine();
		String Name;
		long population;
		long cases;
		while(inFile.hasNext()) {
			Name = inFile.next();
			inFile.next(); //Capitol
			population = inFile.nextLong(); //Population
			inFile.nextDouble(); //GDP
			cases = inFile.nextLong(); //COVID Cases
			inFile.nextLong(); //COVID Deaths
			String a = inFile.next(); //Area
			
			hashtable.insert(Name, population, cases);
				
		} //close while loop
		
		Scanner menuOption = new Scanner(System.in);
		String input = "0";
		while(!input.equals("6")) {
			Project5.printMenu();
			input = menuOption.next();
			System.out.println();
			if (input.equals("1")) { // print hashtable
				hashtable.displayTable();
			}//end option 1
			
			else if (input.equals("2")) { //delete a country of a given name
				Scanner delete = new Scanner(System.in);
				System.out.print("Enter the name of the country you would like to delete: ");
				String deleteName = delete.nextLine();
				hashtable.delete(deleteName);
			}//end option 2
			
			else if (input.equals("3")) { //insert a country given: name, pop, cases
				Scanner couNameInput = new Scanner(System.in);
				String inputName;
				long userPop;
				long userCases;
				System.out.print("Enter the name of the country you would like to insert: ");
				inputName = couNameInput.nextLine();
					while(true) {
						try {
						System.out.print("Enter the population of the country you would like to insert: ");
						Scanner popInsert = new Scanner(System.in);
						userPop = popInsert.nextLong();
						}// end try
						catch(InputMismatchException e) {
							System.out.println();
							System.out.println("Be sure that your input is a valid number and try again. \n");
							continue;
						}//end catch
						break;
					}
					while(true) {
						try {
							System.out.print("Enter the number of COVID cases of the country you would like to insert: ");
							Scanner casesInsert = new Scanner(System.in);
							userCases = casesInsert.nextLong();
						}
						catch(InputMismatchException ee) {
							System.out.println();
							System.out.println("Be sure that your input is a valid number and try again. \n");
							continue;
						}
						break;
				}//end while
					hashtable.insert(inputName, userPop, userCases);
					System.out.printf("%s has been inserted into the hashtable. \n", inputName);
			}//end option 3
			
			else if (input.equals("4")) { //search for a given name and print it along with the case rate
				String foundCountry;
				while(true) {
					try {
					System.out.print("Enter the name of the country you would like to search for: ");
					Scanner searchFor = new Scanner(System.in);
					foundCountry = searchFor.nextLine();
					}// end try
					catch(InputMismatchException e) {
						System.out.println();
						System.out.println("Be sure that your input is valid and try again. \n");
						continue;
					}//end catch
					break;
				}
				double caseRate = hashtable.find(foundCountry);
				if (caseRate < 0) {
					System.out.printf("%s was not found.\n", foundCountry);
				}
				else {
					int hashValue = hashtable.hashFunction(foundCountry);
					System.out.printf("%s was found at index %d with a case rate of %.3f.\n", foundCountry, hashValue, caseRate);
				}
			}//end option 4
			
			else if (input.equals("5")) { //print empty and collided cells
					hashtable.collEmpty();
			}//end option 5
			
			else if (input.equals("6")) { // quit
				break;
			}//end option 6
			
			else {
				System.out.println("That is not a valid menu option, please enter a number between 1 and 8 or 9 to quit.");
			}
			System.out.println();
		}//end of while loop
		
		System.out.println("Goodbye!");
		openFl.close();
		menuOption.close();
		inFile.close();
	}//end of main
	/**
	 * This method is called to print a menu, each option allowing a different action to be performed or 6 to exit the program. 
	 * 
	 */
	public static void printMenu () {
		System.out.print("1) Print hash table\r\n"
				+ "2) Delete a country of a given name\r\n"
				+ "3) Insert a country of a given name\r\n"
				+ "4) Search and print a country and its case rate for a given name.\r\n"
				+ "5) Print numbers of empty cells and collided cells\r\n"
				+ "6) Exit\n"
				+ "Enter a menu option: ");
	} //end of printMenu

}
