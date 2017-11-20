package controller;

import java.io.*;
import java.sql.*;
import java.sql.Date;
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
    System.out.println("Please select a room from the list below to proceed:");
    getAvailableRooms();
    int roomNum = Integer.parseInt(in.nextLine().trim());
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
    Date checkOutDate = new Date(date.getTime());

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
  public static void getAvailableRooms() throws SQLException {
    System.out.println("Populating available rooms...");

    PreparedStatement pstmt = null;

    Statement statement = connection.createStatement();
    String getRooms = "Select * from Room where rid not in (select rid from Reservations)";
    ResultSet rs = statement.executeQuery(getRooms);
    System.out.printf("%-10s%-30s%-20s%-25s%n", "Room No.", "Type", "Price ($/per night)", "Smoke");

    while (rs.next()) {
      // int id = rs.getInt("rId");
      int rNum = rs.getInt("roomNum");
      String type = rs.getString("Type");
      float price = rs.getFloat("price");
      //  boolean cleaned = rs.getBoolean("cleaned");
      boolean smoke = rs.getBoolean("smoke");
      System.out.printf("%-10s%-30s%-20s%-25s%n", rNum + "", type + "", price + "", smoke + "");
    }
    try {

      preparedStatement = connection.prepareStatement(getRooms);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      preparedStatement.close();
    }
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
    int resNum = in.nextInt();
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

  public static void callStoredProcedure() throws SQLException {
    Statement stmtDrop = connection.createStatement();

    String dropProcedure = "DROP PROCEDURE IF EXISTS archiveReservations";
    int dropResult = stmtDrop.executeUpdate(dropProcedure);

    System.out.println("Procedure dropped: " + dropResult);
    Statement delimiter = connection.createStatement();

    String createProcedure =
        "DELIMITER //"
            + "CREATE PROCEDURE archiveReservations(IN lastUpdated timestamp) "
            + "BEGIN "
            + "INSERT INTO Archive (RNum,gID,rid,roomNum,NAME,PartyCount,CheckIN,CheckOut,updatedAt) "
            + " Select RNum,gID,rid,roomNum,NAME,PartyCount,CheckIN,CheckOut,updatedAt from Reservations where updatedAt <= lastUpdated;"
            + " Delete from Reservations where updatedAt <= lastUpdated;"
            + "END //";
    try {
      int result = statement.executeUpdate(createProcedure);
      String msg =
          result == 1 ? "Procedure successfully created!" : "Procedure could not be defined";
      System.out.println(msg);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void manageBooking() {}

  public static void checkIn() {}

  public static void checkOut() {}

  public static void main(String[] args) throws SQLException, InterruptedException, ParseException {
    ConnectDB connect = new ConnectDB();
    // open a connection
    connection = connect.initConnection();
    statement = connection.createStatement();
    statement.executeQuery("USE HOTEL");
    // callStoredProcedure();

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
      // dispatch request based on selection
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
        System.out.println(
            "Please select a number for the corresponding option or enter q to quit:");
      }
    }

    in.close();

    connect.closeConnection();
  }
}
