package controller;

import java.io.*;
import java.sql.*;
import java.util.*;
import model.*;
import util.ConnectDB;

public class LaunchApp {
	
   static Scanner in = new Scanner(System.in);
   static Connection connection = null;
   private static PreparedStatement preparedStatement = null;
   
  public static void reserveRoom(){
	  
  }

  /*
   * helper method to get all available rooms
   */
  public static void getAvailableRooms() throws SQLException {
    System.out.println("Getting a list of all available rooms");
        
    PreparedStatement pstmt = null;
    
    Statement statement = connection.createStatement();    
    String getRooms = "Select * from Room where rid not in (select rid from Reservations)";
    ResultSet rs = statement.executeQuery(getRooms);
    System.out.printf("%-5s%-25s%-20s%-25s%n", "Room No.", "Type", "Price (per night)", "Smoke");

    while(rs.next())
    {
       int id = rs.getInt("rId"); 
       int rNum = rs.getInt("roomNum");
       String type = rs.getString("Type");
       float price = rs.getFloat("price");
       boolean cleaned = rs.getBoolean("cleaned");
       boolean smoke = rs.getBoolean("smoke");
       System.out.printf("%-5s%-25s%-25s%-25s%n", rNum+"", type+"", price+"", smoke+"");
    }
    try { 
 
    preparedStatement = connection.prepareStatement(getRooms);
    }catch (SQLException e) {
    	e.printStackTrace();
    }
    finally { 
    	preparedStatement.close(); 
    } 
    
  }

  public static void cancelBooking() throws SQLException {
	  	System.out.println("Please enter your reservation number to proceed:");
	  	int resNum = in.nextInt();
	    PreparedStatement pstmt = null;

	  try { 
		  String deleteBooking = "Delete from Reservations where RNum = ?";

		    preparedStatement = connection.prepareStatement(deleteBooking);
		    preparedStatement.setInt(1, resNum);
		    preparedStatement.executeUpdate();
		    }catch (SQLException e) {
		    	e.printStackTrace();
		    }
		    finally { 
		    	preparedStatement.close(); 
		 } 
  }

  public static void manageBooking() {}

  public static void checkIn() {}

  public static void checkOut() {}

  public static void searchRoom() {}

  public static void main(String[] args) throws SQLException {
    ConnectDB connect = new ConnectDB();
    //open a connection
    connection = connect.initConnection();
    Statement statement = connection.createStatement();
    statement.executeQuery("USE HOTEL");
    
    System.out.println("Welcome to Hyatt!");
    System.out.println("Please select a number for the corresponding option or enter q to quit:");
    while (true) {
      System.out.println("====================");
      System.out.println("|1. Book a room    |");
      System.out.println("|2. Cancel Booking |");
      System.out.println("|3. Manage Booking |");
      System.out.println("|4. Search Room    |");
      System.out.println("|5. Check In       |");
      System.out.println("|6. Check Out      |");
      System.out.println("====================");

      int selection = 0;
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
      //dispatch request based on selection
      if (selection != 0) {
        if (selection == 1) {
          reserveRoom();
        } else if (selection == 2) {
          cancelBooking();
        } else if (selection == 3) {
          manageBooking();
        } else if (selection == 4) {
          searchRoom();
        } else if (selection == 5) {
          checkIn();
        } else if (selection == 6) {
          checkOut();
        } else {
          System.out.println("Invalid option! Please choose a valid option from the menu below");
        }
      }
    }

    in.close();

    connect.closeConnection();
  }
}
