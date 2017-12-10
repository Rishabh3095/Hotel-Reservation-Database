package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.Employee;
import model.Guest;
import model.Payment;
import model.Room;
import util.ConnectDB;

public class Queries {
	
	  static Scanner in = new Scanner(System.in);
	  static Connection connection = null;
	  private static PreparedStatement preparedStatement = null;
	  private static Statement statement = null;

	  public static int addPayment(Payment res) throws SQLException {
		    PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		        String guestPayment =
		                "INSERT INTO Payment (RNum, gId, NAME,  CardNumber, Expiration, CVC,  AmountDue) VALUES (? , ?, ?, ?, ?, ?, ?);";

		      pstmt = connection.prepareStatement(guestPayment);
		      pstmt.setInt(1, res.getReservationNum());
		      pstmt.setInt(2, res.getgId());
		      pstmt.setString(3, res.getName());
		      pstmt.setString(4, res.getCardNumber());
		      pstmt.setString(5, res.getExpiration());
		      pstmt.setInt(6, res.getCvc());
		      pstmt.setDouble(7, res.getAmountDue());
		      
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
	  }
	  	  
	  public static void employerLogin() throws SQLException
	  {
		  System.out.println("Please enter the employer ID: ");
		  
		  Scanner ini = new Scanner(System.in);
		  int eId = ini.nextInt();
		  
		  System.out.println("Please enter the password: ");
		  String pass = ini.next();
		  
		  PreparedStatement pstmt = null;
		  ResultSet result = null;
		  
		    try {
		        String signIn =
		                "SELECT eId, Password from EMPLOYEE where eId = (?) and Password = (?);";

		      pstmt = connection.prepareStatement(signIn);
		      pstmt.setInt(1, eId);
		      pstmt.setString(2, pass);
		      result = pstmt.executeQuery();
		      
		      while (result.next()) {
		          int eI = result.getInt("eId");
		          String pas = result.getString("Password");
		          System.out.print(eI + " " + pas + " ");
		          System.out.println(" ");
		        }
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
	  }
	  
	  public static void averageRoomPrice() throws SQLException
	  {
		  PreparedStatement pstmt = null;
		    ResultSet result = null;
		    try {
		        String avgPrice =
		                "SELECT roomNum, Type, price, smoke from Room R1 where price > (SELECT avg(price) from Room where R1.type = type) and rId in (SELECT rID from Reservations);";

		      pstmt = connection.prepareStatement(avgPrice);	      
		      result = pstmt.executeQuery();

		      while (result.next()) {
		          int roomNum = result.getInt("roomNum");
		          String Type = result.getString("Type");
		          Float pric = result.getFloat("price");
		          boolean bl = result.getBoolean("smoke");
		          System.out.print(roomNum + " " + Type + " " + " " + pric + " " + bl);
		          System.out.println(" ");
		        }

		      
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
	  }
	  
	  public static int markRoomUnav() throws SQLException {
		  PreparedStatement pstmt = null;
		  Scanner ini = new Scanner(System.in);
		  System.out.println("Enter the room ID to make it unavailable to guests: ");
		  int ro = ini.nextInt();
		  Room r = new Room(ro);
		    int result = 0;
		    try {
		      String roomInfo =
		          "INSERT INTO UNROOM (rId) VALUE (?);";

		      pstmt = connection.prepareStatement(roomInfo);
		      pstmt.setInt(1, r.getrId());
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
	  }
	  
	  public static void guestInfo() throws SQLException {
		  
		    PreparedStatement pstmt = null;
		    ResultSet result = null;
		    try {
		      String guestInfo =
		          "Select (*) from (Payment JOIN Guest);";

		      pstmt = connection.prepareStatement(guestInfo);
		      result = pstmt.executeQuery();
		      
		      while (result.next()) {
		    	  int gid = result.getInt("gId");
		    	  String name = result.getString("Name");
		    	  int cardnumber = result.getInt("CardNumber");
		    	  int expiration = result.getInt("Expiration");
		          System.out.print(gid + " " + name + " " + " " + cardnumber+ " " + expiration);
		          System.out.println(" ");
		        }


		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		  }
	}