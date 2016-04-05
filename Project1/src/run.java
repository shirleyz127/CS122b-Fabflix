import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class run {
    final static String menu = "1 = print out the movies given a star\n" +
            "2 = insert a new star into the database\n" +
            "3 = insert a new customer into database\n" +
            "4 = delete a customer from database\n" +
            "5 = provide metadata of database\n" +
            "6 = enter valid select/update/insert/delete SQL command\n" +
            "7 = exit the menu\n" +
            "8 = exit the program\n" +
            "input: ";
    final static Set<String> validInput = new HashSet<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));

    public static void main(String[] args) throws Exception {
        
        // Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        //TODO ask user for db, username, and pw
         // Connect to the test database
       
        Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb","mytestuser", "mypassword");

        Scanner s = new Scanner(System.in);
        
        String input = "-1";
        
        
        while(!input.equals("8")) 
        {
                //makes sure that the user selects a valid input from the menu
            System.out.println(menu);
            input = s.next();
            if(!validInput.contains(input)) {
            	
                System.out.println("invalid input");
                continue;
            }
            if(input.equals("1"))
            {
                //TODO
                //print out neatly, and labeled
                //star can be queried through first name and/or last name
                    //if name is more than two words, assume is first and last
                    //if name is only one word, return those that contain the word as first or last name
//                printMoviesWithStar();
                System.out.print("Enter name of movie star: ");
                String nameInput = s.nextLine();
                String[] nameSplit = nameInput.split(" ");
                String first_name = null;
                String last_name = null;
                Statement select = connection.createStatement();
                ResultSet result = null;
                //if name is one word, then use that name as the first or last name
                if(nameSplit.length == 1) {
                	last_name = nameSplit[0]; 
                	//TODO change to take in last name
                    result = select.executeQuery(
                            "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url" +
                            "from stars as a, stars_in_movies as b, movies as m" + 
                            "where a.id = b.star_id and a.last_name = 'Kreuk' and m.id = b.movie_id");
                }
                //if the name is more than one word, split it up.
                //if the name is two words, assume that the first word is the first name
                    //and the second word is the last name
                else if(nameSplit.length == 2) {
                	first_name = nameSplit[0];
                	last_name = nameSplit[1];
                	//TODO change to take in first and last name
                	result = select.executeQuery(
                            "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url" +
                            "from stars as a, stars_in_movies as b, movies as m" + 
                            "where a.id = b.star_id and a.last_name = 'Kreuk' and a.first_name = 'input' and m.id = b.movie_id");
                }
                //if the name is more than two words, split it at all the spaces
                //and try all the combinations of names?
                	//i guess just try to add anything
                else {
                    
                }
                //TODO print out things from movies
                System.out.println("Movie names");
                while(result.next()) {
                	//TODO not sure about index part
                    System.out.println(result.getString(1));
                }
            }
            else if (input.equals("2"))
            {                
                //if only one word, insert as last name and assign "" as first name
            	System.out.print("Enter name of star: ");
            	String nameInput = s.nextLine();
            	String[] nameSplit = nameInput.split(" ");
            	String first_name = null;
            	String last_name = null;
            	if(nameSplit.length == 1) {
            		first_name = "";
            		last_name = nameSplit[0];
            	}
            	else {
            		//assumes that only the last word is the last name
            		//everything else is included in the first name, including the middle name(s)
            		last_name = nameSplit[nameSplit.length-1];
            		nameSplit[nameSplit.length-1] = "";
            		//TODO change to java 1.8 for this one and uncomment
//            		first_name = String.join(" ", Arrays.asList(nameSplit));
            	}
            	System.out.print("Enter date of birth(YYYY-MM-DD) or enter null: ");
            	String dob = s.nextLine();
            	
            	System.out.print("Enter photo url or enter null: ");
            	String photo_url = s.nextLine();
            	Statement insertStar = connection.createStatement();
            	//TODO see what it actually returns
            	int retID = insertStar.executeUpdate(String.format("insert into stars (first_name, last_name, dob, photo_url)" +
               			"values ('%s', '%s', Date '%s', '%s')", first_name, last_name, dob, photo_url));
            	System.out.println("Entered star into database with ID " + Integer.toString(retID));
            }
            else if (input.equals("3"))
            {
                //TODO
                //do not allow insertion of a customer if credit card does not exit in cc table
                //if customer's name is one word, just make it the last name; first name is ""
            	System.out.print("Enter name of customer: ");
            	System.out.print("Enter name of star: ");
            	String nameInput = s.nextLine();
            	String[] nameSplit = nameInput.split(" ");
            	String first_name = null;
            	String last_name = null;
            	if(nameSplit.length == 1) {
            		first_name = "";
            		last_name = nameSplit[0];
            	}
            	else {
            		//assumes that only the last word is the last name
            		//everything else is included in the first name, including the middle name(s)
            		last_name = nameSplit[nameSplit.length-1];
            		nameSplit[nameSplit.length-1] = "";
            		//TODO change to java 1.8 for this one and uncomment
//            		first_name = String.join(" ", Arrays.asList(nameSplit));
            	}
            	System.out.print("Give me your credit card number (as one number): ");
            	int cc_id = s.nextInt();
            	System.out.print("Enter address on one line: ");
            	String address = s.nextLine();
            	System.out.print("Enter email: ");
            	String email = s.nextLine();
            	System.out.print("Enter enter password: ");
            	String password = s.nextLine();
            	//TODO
            	//check if credit card is valid.
            	//if it is not, print invalid stuff
            	
            	//then do insert
            	Statement insertCustomer = connection.createStatement();
            	//TODO see what it actually returns
            	int customerID = insertCustomer.executeUpdate(String.format("insert into customers (first_name, last_name, cc_id, address, email, password)" +
               			"values ('%s', '%s', '%d', '%s', '%s', '%s')", first_name, last_name, cc_id, address, email, password));
            	System.out.println("Entered customer into database with ID " + Integer.toString(customerID));
   	
            }
            else if (input.equals("4"))
            {
                deleteCustomer();                
                //deletion queried by credit card
                System.out.print("Enter credit card number(one number): ");
                int cc_id = s.nextInt();
                Statement deleteCustomer = connection.createStatement();
                int retID = deleteCustomer.executeUpdate(String.format("delete from customers where cc_id=%d", cc_id));
            }
            else if (input.equals("5"))
            {
                insertMetadata();
                //TODO print out the name of each table, and for each table, each attribute and its type
            }
            else if (input.equals("6"))
            {
                executeMySQL();
                //TODO
                /*
                 * enter a valid select, update, insert, delete mysql query
                 * for select, diplay the answers
                 * for update, show how many records have changed,
                 * for others, give enough information about status 
                 */
            }
            else if (input.equals("7"))
            {
                //TODO
                //go back to the incorporate mysql driver and redo the connection
                //i'm not sure if there's a logout function somewhere.
                exitMenu();
            }
            else if (input.equals("8"))
            {
                System.out.println("kbaithx");
                s.close();
            }
        }        
    }


    private static void exitMenu() {
        // TODO Auto-generated method stub
        
    }

    private static void executeMySQL() {
        // TODO Auto-generated method stub
        
    }

    private static void insertMetadata() {
        // TODO Auto-generated method stub
        
    }

    private static void deleteCustomer() {
        // TODO Auto-generated method stub
        
    }

    private static void insertCustomer() {
        // TODO Auto-generated method stub
        
    }

    private static void insertStar() {
        // TODO Auto-generated method stub
        
    }


    private static String getMenuInput() {
        //makes sure that the user selects a valid input from the menu
        Scanner s = new Scanner(System.in);
        String input = "0";
        while(!validInput.contains(input)) {
            System.out.println(menu);
            input = s.next();
            if(!validInput.contains(input))
                System.out.println("invalid input");
        }
        s.close();
        return input;
    }
    
}



