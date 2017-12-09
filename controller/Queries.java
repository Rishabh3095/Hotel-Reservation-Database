package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.Guest;

public class Queries {
	
	  static Scanner in = new Scanner(System.in);
	  static Connection connection = null;
	  private static PreparedStatement preparedStatement = null;
	  private static Statement statement = null;


	  public 
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
