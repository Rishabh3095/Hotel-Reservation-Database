package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.Guest;
import model.Payment;

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
	  
	  public static int guestInfo(Guest guest) throws SQLException {
		  
		    PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		      String addGuest =
		          "Select (*) from (Payment JOIN Guest);";

		      pstmt = connection.prepareStatement(addGuest);
		      result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		    }
		    return result;
		  }
	  
	  /*
	  public static int makeRoomUnavailable() throws SQLException {
		    PreparedStatement pstmt = null;
		    int result = 0;
		    try {
		      String addGuest =
		          "Select (*) from (room) where rId = (?);";

		      pstmt = connection.prepareStatement(addGuest);
		      result = pstmt.executeUpdate();

	  }
		  
	  } 
	  
	   */
}