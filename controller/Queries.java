package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.Employee;
import model.Guest;
import model.Payment;
import model.Room;

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
	  
	  public static int employerLogin(Employee em) throws SQLException
	  {
		  PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		        String signIn =
		                "SELECT eId, Password from EMPLOYEE where EiD = (?) and Password = (?);";

		      pstmt = connection.prepareStatement(signIn);
		      pstmt.setInt(1, em.geteId());
		      pstmt.setString(2, em.getPass());
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
	  }
	  
	  /*
	  public static int userLogin(Guest gst) throws SQLException
	  {
		  PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		        String signIn =
		                "SELECT rId, Password from GUEST where rId = (?) and Password = (?);";

		      pstmt = connection.prepareStatement(signIn);	      
		      pstmt.setInt(1, gst.getrId());
		      pstmt.setString(2, gst.get);
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
	  }
	  */

	  public static int averageRoomPrice(Room rm) throws SQLException
	  {
		  PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		        String avgPrice =
		                "SELECT roomNum, Type, price, smoke from Room R1 where price > (SELECT avg(price) from Room where R1.type = type) and rId in (SELECT rID from Reservations);";

		      pstmt = connection.prepareStatement(avgPrice);	      
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
	  }
	  
	  public static int markRoomUnav(Room rm) throws SQLException {
		  PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		      String roomInfo =
		          "\"INSERT INTO UNROOM (rId) VALUE (?);";

		      pstmt = connection.prepareStatement(roomInfo);
		      pstmt.setInt(1, rm.getrId());
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
	  }
	  
	  public static int guestInfo(Guest guest) throws SQLException {
		  
		    PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		      String guestInfo =
		          "Select (*) from (Payment JOIN Guest);";

		      pstmt = connection.prepareStatement(guestInfo);
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
		  }
	  
	  /*
	   * Display all the room details whose price is greater than average price for that type and is currently reserved. (ADMIN : for SALES purposes)
	   */
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
}