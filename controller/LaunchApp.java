package controller;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.*;
import util.ConnectDB;

public class LaunchApp {

  static Scanner in = new Scanner(System.in);
  static Connection connection = null;
  private static PreparedStatement preparedStatement = null;
  private static Statement statement = null;

  public static int addGuest(Guest guest) throws SQLException {
    PreparedStatement pstmt = null;
    int result = 0;
    try {
      String addGuest =
          "INSERT INTO Guest (rId, Name, Address, Email, checkIn, checkOut) VALUES (? , ?, ?, ?, ?, ?);";

      pstmt = connection.prepareStatement(addGuest);
      pstmt.setInt(1, guest.getrId());
      pstmt.setString(2, guest.getName());
      pstmt.setString(3, guest.getAddress());
      pstmt.setString(4, guest.getEmail());
      pstmt.setDate(5, guest.getCheckIn());
      pstmt.setDate(6, guest.getCheckOut());
      result = pstmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      pstmt.close();
    }
    return result;
  }

  public static int getGuestId(int rId, String email) throws SQLException {
    PreparedStatement pstmt = null;
    int gId = -1;
    ResultSet result = null;
    try {
      String getGuest = "Select * from Guest where rId = ? and email = ?";

      pstmt = connection.prepareStatement(getGuest);
      pstmt.setInt(1, rId);
      pstmt.setString(2, email);

      result = pstmt.executeQuery();

      while (result.next()) {
        gId = result.getInt("gID");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      pstmt.close();
    }
    return gId;
  }

  public static int getReservationNum(int roomNum) throws SQLException {
    PreparedStatement pstmt = null;
    int resNum = -1;
    ResultSet result = null;
    try {
      String getGuest = "Select * from Reservations where roomNum = ?";

      pstmt = connection.prepareStatement(getGuest);
      pstmt.setInt(1, roomNum);

      result = pstmt.executeQuery();

      while (result.next()) {
        resNum = result.getInt("rNum");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      pstmt.close();
    }
    return resNum;
  }

  public static int addReservation(Reservations res) throws SQLException {
    PreparedStatement pstmt = null;
    int result = 0;
    try {
      String addGuest =
          "INSERT INTO Reservations (gId, roomNum, name,  partyCount, checkIn, checkOut) VALUES (? , ?, ?, ?, ?, ?);";

      pstmt = connection.prepareStatement(addGuest);
      pstmt.setInt(1, res.getgId());
      pstmt.setInt(2, res.getRoomNumber());
      pstmt.setString(3, res.getName());
      pstmt.setInt(4, res.getPartyCount());
      pstmt.setDate(5, res.getCheckIn());
      pstmt.setDate(6, res.getCheckOut());
      result = pstmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      pstmt.close();
    }
    return result;
  }

  public static void reserveRoom() throws SQLException, InterruptedException, ParseException {
	boolean validRoom = false;
    HashSet<Integer> availableRooms = getAvailableRooms();
    System.out.println("Please select a room from the list below to proceed:");
    int roomNum = Integer.parseInt(in.nextLine().trim());
    while(!validRoom){
    if(!availableRooms.contains(roomNum)){
    	System.out.println("Please select a room from the available list! \n The room you selected is not available");
    	roomNum = Integer.parseInt(in.nextLine().trim());
    	}else{
    		validRoom = true;
    	}
    }
    int rId = roomNum;
    System.out.println("Please enter your details:");
    System.out.println("Name:");

    String name = in.nextLine();

    System.out.println("Address:");
    String addr = in.nextLine();

    System.out.println("Email:");
    String email = in.nextLine();

    System.out.println("Party Count:");
    int partyCount = Integer.parseInt(in.nextLine().trim());

    System.out.println("Check In Date (MM-DD-YYYY)");
    String checkIn = in.nextLine();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

    java.util.Date date = dateFormatter.parse(checkIn);
    Date checkInDate = new Date(date.getTime());

    System.out.println("Check Out Date (MM-DD-YYYY)");
    String checkOut = in.nextLine();
    java.util.Date date2 = dateFormatter.parse(checkOut);
    Date checkOutDate = new Date(date2.getTime());

    Guest guest =
        new Guest(
            -1,
            rId,
            name,
            addr,
            email,
            checkInDate,
            checkOutDate); // use guest id -1 because it is assigned by Database

    int guestAdded = addGuest(guest);

    if (guestAdded == 1) { // guest successfully added to database
      int gId = getGuestId(rId, email);
      // System.out.println("Extracting Guest ID: " + gId);
      Reservations res =
          new Reservations(
              -1,
              gId,
              roomNum,
              name,
              partyCount,
              checkInDate,
              checkOutDate,
              false,
              false,
              false,
              null);

      int confirmation = addReservation(res);

      String msg =
          confirmation == 1
              ? "Hi " + name + ", You have successfully reserved a room!"
              : "Room could not be reserved! Try again";
      System.out.println(msg);
      System.out.println("Details: ");
      System.out.println("Reservation No:" + getReservationNum(roomNum));
      System.out.println("Name: " + name);
      System.out.println("Check In " + checkInDate);
    }
  }

  /*
   * helper method to get all available rooms
   */
  public static HashSet<Integer> getAvailableRooms() throws SQLException {
    System.out.println("Populating available rooms...");

    //PreparedStatement pstmt = null;
    HashSet<Integer> set = new HashSet<>();

    Statement statement = connection.createStatement();
    String getRooms = "Select * from Room where roomNum not in (select roomNum from Reservations)";
    
    try {
    	ResultSet rs = statement.executeQuery(getRooms);
        System.out.printf("%-10s%-30s%-20s%-25s%n", "Room No.", "Type", "Price ($/per night)", "Smoke");
        
        
        while (rs.next()) {
          // int id = rs.getInt("rId");
          int rNum = rs.getInt("roomNum");
          set.add(rNum);
          String type = rs.getString("Type");
          float price = rs.getFloat("price");
          //  boolean cleaned = rs.getBoolean("cleaned");
          boolean smoke = rs.getBoolean("smoke");
          System.out.printf("%-10s%-30s%-20s%-25s%n", rNum + "", type + "", price + "", smoke + "");
        }

     // preparedStatement = connection.prepareStatement(getRooms);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      
    }
    return set;
  }

  public static String getReservationDetails(int resNum) throws SQLException {

    PreparedStatement pstmt = null;
    String name = "";
    ResultSet result = null;
    try {
      String getInfo = "Select * from Reservations where rNum = ?";

      pstmt = connection.prepareStatement(getInfo);
      pstmt.setInt(1, resNum);

      result = pstmt.executeQuery();

      while (result.next()) {
        name = result.getString("Name");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      pstmt.close();
    }
    return name;
  }

  public static void cancelBooking() throws SQLException {
    System.out.println("Please enter your reservation number to proceed:");

    String input  = in.nextLine().trim();
  	int resNum = Integer.parseInt(input);
    PreparedStatement pstmt = null;
    String name = getReservationDetails(resNum);
    try {
      String deleteBooking = "Delete from Reservations where RNum = ?";

      preparedStatement = connection.prepareStatement(deleteBooking);
      preparedStatement.setInt(1, resNum);
      int result = preparedStatement.executeUpdate();

      String msg =
          result == 1
              ? "Hi " + name + ", your reservation has been cancelled!"
              : "Error cancelling reservation";
      System.out.println(msg);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      preparedStatement.close();
    }
  }

  public static void searchRoom() throws SQLException {
    System.out.println("Please enter room details that you are looking for:");

    System.out.println("Type (single | double | king)");
    String type = in.nextLine();

    System.out.println("Min Price:");
    double minPrice = Double.parseDouble(in.nextLine().trim());

    System.out.println("Max Price:");
    double maxPrice = Double.parseDouble(in.nextLine().trim());

    System.out.println("Smoke: true | false");
    boolean smoke = Boolean.parseBoolean(in.nextLine());

    PreparedStatement pstmt = null;
    try {
      String getRooms =
          "Select * from Room where rId not in (select rId from Reservations) and type = ? and price >= ? and price <= ? and smoke = ?";

      pstmt = connection.prepareStatement(getRooms);
      pstmt.setString(1, type);
      pstmt.setDouble(2, minPrice);
      pstmt.setDouble(3, maxPrice);
      pstmt.setBoolean(4, smoke);

      ResultSet result = pstmt.executeQuery();
      System.out.printf(
          "%-10s%-30s%-20s%-25s%n", "Room No.", "Type", "Price ($/per night)", "Smoke");

      while (result.next()) {
        int rNum = result.getInt("roomNum");
        String t = result.getString("Type");
        float price = result.getFloat("price");
        boolean s = result.getBoolean("smoke");
        System.out.printf("%-10s%-30s%-20s%-25s%n", rNum + "", t + "", price + "", s + "");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      pstmt.close();
    }
  }


  public static void callStoredProcedure(String lastUpdated) throws SQLException, ParseException {
	   System.out.println("\nArchiving relations from Rervations table");
	   SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

	   java.util.Date date2 = dateFormatter.parse(lastUpdated);
	   Date lastUpdatedTimeStamp = new Date(date2.getTime());
	   
	   java.sql.Timestamp lastUpdate = new java.sql.Timestamp(lastUpdatedTimeStamp.getTime());

	   CallableStatement cs = connection.prepareCall("{CALL archiveReservations(?)}");
	   cs.setTimestamp(1, lastUpdate);
	   
	   

  }
  
  public static int getGID(int resNum) throws SQLException{
	  PreparedStatement pstmt = null;
	    int gId = -1;
	    ResultSet result = null;
	    try {
	      String getGuestId = "select guest.gid as gID from reservations inner join guest on guest.gid = reservations.gid where reservations.rnum = ?";
	      
	      pstmt = connection.prepareStatement(getGuestId);
	      pstmt.setInt(1, resNum);
	      result = pstmt.executeQuery();
	      
	      while (result.next()) {
		        gId = result.getInt("gID");
		      }
	      	      
	      

	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	    	pstmt.close();
	    }
	    return gId;
	    
	   
  }

//checks in guest and adds payment information for a guest
  public static void checkIn() throws SQLException {
	  
	  	System.out.println("Please enter your reservation number to check in!");
	  	String input  = in.nextLine().trim();
	  	int resNum = Integer.parseInt(input);
	  	
	  	int gID = getGID(resNum);
	  	
	  	
	  	System.out.println("Please enter credit card information to proceed to check in:");
	  	System.out.println("Name:");
	  	String name = in.nextLine().trim();
	  	
	  	System.out.println("Credit Card Number:");
	  	String cardNum = in.nextLine().trim();
	  	
	  	System.out.println("Expiration: (MM/YY)");
	  	String exp = in.nextLine().trim();
	  	
	  	System.out.println("CVC:");
	  	int cvc = Integer.parseInt(in.nextLine().trim());
	  	
	  	
	  	double amtDue = 0.0;
	  	
	  	Payment pay = new Payment(0, resNum, gID, name, cardNum, exp, cvc, amtDue);
	  	
	  	Queries.addPayment(pay);
	  	
	  	PreparedStatement pstmt = null;
	    
	    int result = 0;
	    try {
	      String getInfo = "Update Reservations SET checkedIn = true where rNum = ?";

	      pstmt = connection.prepareStatement(getInfo);
	      pstmt.setInt(1, resNum);

	      result = pstmt.executeUpdate();
	      
	      String msg = result == 1? "Check in successful": "Check in failed!";
	      System.out.println(msg);

	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      pstmt.close();
	    }
  }

  public static void checkOut() throws SQLException {
	  System.out.println("Please enter your reservation number to check out!");
	  	String input  = in.nextLine().trim();
	  	int resNum = Integer.parseInt(input);
	  	
	  	PreparedStatement pstmt = null;
	    
	    int result = 0;
	    try {
	      String getInfo = "Update Reservations SET checkedOut = true where rNum = ?";

	      pstmt = connection.prepareStatement(getInfo);
	      pstmt.setInt(1, resNum);

	      result = pstmt.executeUpdate();
	      
	      String msg = result == 1? "Check out successful": "Check out failed!";
	      System.out.println(msg);

	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      pstmt.close();
	    }
  }
  
  
  public static void allPeopleInHotel() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			String getRecord = "select * from ((select name from employee) union (select name from guest)) as B;";

			pstmt = connection.prepareStatement(getRecord);
			result = pstmt.executeQuery();

	        while (result.next()) {
	        	String name = result.getString("name");
	            System.out.println(name);
	        }
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
          System.out.println(" ");
			pstmt.close();
		}
	}

  
  
  
  public static void displayGuestInterface(){
	  System.out.println("Please select an option from the menu:");
	  System.out.println("=======================");
      System.out.println("|1. Book a room        |");
      System.out.println("|2. Cancel Booking     |");
      System.out.println("|3. Search Room        |");
      System.out.println("|4. Check In           |");
      System.out.println("|5. Check Out          |");
      System.out.println("|6. Request Valet      |");
      System.out.println("|7. Request Wakeup     |");
      System.out.println("|8. Main Menu          |");
      System.out.println("=======================");
  }
  
  public static void displayEmployeeInterface(){
	  	  System.out.println("Please select an option from the menu:");
	      System.out.println("================================");
	      System.out.println("|1. Current Reservations        |");
	      System.out.println("|2. Average Room Prices (SALES) |");
	      System.out.println("|3. Purchase History            |");
	      System.out.println("|4. Mark Room Unavailable       |");
	      System.out.println("|5. Available rooms             |");
	      System.out.println("|6. Add Valet                   |");
	      System.out.println("|7. Notify Guest (Wake-up call) |");
	      System.out.println("|8. Remove Car Valet            |");
	      System.out.println("|9. Display all guests          |");
	      System.out.println("|10.Main Menu                   |");
	      System.out.println("================================");
  }
 

  public static void main(String[] args) throws SQLException, InterruptedException, ParseException {
    ConnectDB connect = new ConnectDB();
    // open a connection
    connection = connect.initConnection();
    statement = connection.createStatement();
    
    //instantiate connection for Queries and ValetFunctions
    Queries.connection = connection;
    ValetFunctions.connection  = connection;
    
    //selecting database to perform queries on
    statement.executeQuery("USE HOTEL");
    
    String archiveDate = "12-30-2017"; //set archive date
    
    //calling stored procedure to archive reservations older than certain date
    callStoredProcedure(archiveDate); //pass in last updated date to archive relation before that date

    System.out.println("Welcome to Hyatt!");
    System.out.println("Please select a number for the corresponding option or enter q to quit:");
    while (true) {
      System.out.println("================================");
      System.out.println("| 1. Guest    2. Employee/Admin |");
      System.out.println("================================");

      boolean guest = false;
      boolean employee = false;
      
      int selection = 0; //initialize selection to prevent null pointer exception
      String option = in.nextLine().trim();

      if (option.equals("q") || option.equals("q")) {
        System.out.println("Thank you for visiting Hyatt!");
        break;
      }
      try {
    	  
        selection = Integer.parseInt(option);
      } catch (NumberFormatException e) {
        System.out.println("Invalid option! Please select a valid option!");
        continue;
      }
      // dispatch request based on selection
      if (selection != 0) {
        if (selection == 1) {
            guest = true;
            int guestChoice = 0;
            while(guest){
                displayGuestInterface();
            	String choice = in.nextLine().trim();
            	//parsing use input //handling invalid input format exception
            	try {
                    guestChoice = Integer.parseInt(choice);
                  } catch (NumberFormatException e) {
                    System.out.println("Invalid option! Please select a valid option!");
                    continue;
                  }
            	
            	//process the options = dispatch functions based on selected option
            	
            	if(guestChoice == 1){
            		reserveRoom();
            	}else if(guestChoice == 2){
            		cancelBooking();
            	}else if(guestChoice == 3){
            		searchRoom();
            	}else if(guestChoice == 4){
            		checkIn();
            	}else if(guestChoice == 5){
            		checkOut();
            	}else if(guestChoice == 6){
            		ValetFunctions.carValet();
            	}else if(guestChoice == 7){
            		ValetFunctions.wakeUpGuests();
            	}else if(guestChoice == 8){
            		guest = false; //exit guest view, goes back to main menu
            	}else{
            		System.out.println("Not a valid option");
            	}
       
            }
        } else if (selection == 2) {
        	employee = true;
        	System.out.println("Hint: Id: 3, password: 2");
        	if(!Queries.employerLogin())
            {
                employee = false;
                System.out.println("Please enter valid ID and Password");
            }
             int empChoice = 0;
             while(employee){
                 displayEmployeeInterface();
             	String choice = in.nextLine().trim();
             	//parsing use input //handling invalid input format exception
             	try {
             		empChoice = Integer.parseInt(choice);
                   } catch (NumberFormatException e) {
                     System.out.println("Invalid option! Please select a valid option!");
                     continue;
                   }
             	
             	
             	//process the options = dispatch functions based on selected option
       
             	if(empChoice == 1){
             		ValetFunctions.viewBookings();
             	}else if(empChoice == 2){
             		Queries.averageRoomPrice();
             	}else if(empChoice == 3){
             		Queries.guestInfo();
             	}else if(empChoice == 4){
             		Queries.markRoomUnav();
             	}else if(empChoice == 5){
             		getAvailableRooms();
             	}else if(empChoice == 6){
             		 ValetFunctions.carValet();
             	}else if(empChoice == 7){
             		ValetFunctions.wakeUpGuests();
             	}else if(empChoice == 8){
             		ValetFunctions.removeCar();
             	}else if(empChoice == 9){
             		allPeopleInHotel();
             	}else if(empChoice == 10){
             		employee = false; //exit employee view, goes back to main menu
             	}
             	else {
             		System.out.println("Not a valid option");	             		
             	}
        
             }
        } else {
          System.out.println("Invalid option! Please choose a valid option from the menu below");
        }
        System.out.println(
            "Please select a number for the corresponding option or enter q to quit:");
      }
    }
    in.close();

    connect.closeConnection();
  }

  	
}