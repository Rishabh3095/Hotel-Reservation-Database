package controller;

import java.io.*;
import java.util.*;
import model.*;
import util.ConnectDB;

public class LaunchApp {

  public static void reserveRoom() {
    System.out.println("Populating rooms");
  }

  public static void cancelBooking() {}

  public static void manageBooking() {}

  public static void checkIn() {}

  public static void checkOut() {}

  public static void searchRoom() {}

  public static void main(String[] args) {
    ConnectDB connect = new ConnectDB();
    // Connection connection = connect.initConnection();

    Scanner in = new Scanner(System.in);

    System.out.println("Welcome to Hyatt!");
    System.out.println("Please select a number for the corresponding option or q to quit:");
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

    // connect.closeConnection();
  }
}
