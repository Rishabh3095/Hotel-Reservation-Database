package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import model.Valet;
import model.WakeUpCall;



public class ValetFunctions {


	static Scanner in = new Scanner(System.in);
	static Connection connection = null;

	public static void wakeUpGuests() throws SQLException 
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		String time = dateFormat.format(cal.getTime());

		String sql = "DELETE FROM wakeupcall Where Time = ?";

		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, time);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pstmt.close();
		}

		String msg = result == 1 ? "Wake up calls have been issued" : "Something went wrong please try again";
		System.out.println(msg);

	}


	public static void viewBookings() throws SQLException {
		String sql = "SELECT Guest.Name, roomNum, partyCount, " + 
				"checkedIn, paymentReceived,  " + 
				"Reservations.checkOut from Reservations LEFT OUTER JOIN Guest ON Reservations.gID " 
				+ "= Guest.gID where Reservations.checkedIn = true";
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			System.out.printf("%-15s%-30s%-20s%-20s%-10s\n", "Room number", "name", "Party count", "Payment Recieved", "check out");
			while (rs.next()) 
			{
				// int id = rs.getInt("rId");
				String name = rs.getString("Guest.Name");
				int rNum = rs.getInt("roomNum");
				int partyCount = rs.getInt("partyCount");
				boolean paymentReceived = rs.getBoolean("paymentReceived");
				Date checkOut = rs.getDate("Reservations.checkOut");
				System.out.printf("%-15s%-30s%-20s%-20s%-10s\n", rNum + "", name + "", partyCount + "", paymentReceived + "", checkOut.toString());

			}
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
		} finally{
			rs.close();
			statement.close();

		}





	}

	public static void requestCar() throws SQLException 
	{
		String sql = "UPDATE valet SET requested = ? WHERE parkingNum = ?";
		System.out.println("Please provide your ticket Number: ");
		int ticNum = Integer.parseInt(in.nextLine().trim());
		PreparedStatement pstmt = null;
		int result = 0;

		try {

			pstmt = connection.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, ticNum);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
		}

		String msg = result == 1 ? "Your car has been requested, go to the lobby to get the keys." : "Something went wrong please try again";
		System.out.println(msg);



	}


	public static void wakeUpCall() throws SQLException 
	{

		System.out.println("Please enter your email: ");
		String email = in.nextLine();
		System.out.println("Please enter your room Number: ");
		int roomNum = Integer.parseInt(in.nextLine().trim());
		int guestID = LaunchApp.getGuestId(roomNum, email);
		System.out.println("Please enter the time you will liked to be waked up on (HH:MM)(In military time):");
		String time = in.nextLine();
		WakeUpCall wuc = new WakeUpCall(guestID, time);
		int result = addWakeupCall(wuc);
		String msg =
				result == 1
				? "You have successfully requested a wake up Call!"
						: "Something went wrong! Try again";
		System.out.println(msg);


	}



	public static int addWakeupCall(WakeUpCall wuc) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String addGuest =
					"INSERT INTO WakeupCall (gId, Time) VALUES (? , ?);";

			pstmt = connection.prepareStatement(addGuest);
			pstmt.setInt(1, wuc.getgID());
			pstmt.setString(2, wuc.getTime());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
		}
		return result;
	}

	public static void carValet() throws SQLException
	{
		System.out.println("Please enter the guest Id of the customer:");
		int gID = Integer.parseInt(in.nextLine().trim());
		System.out.println("Please enter the name of the car to be added:");
		String car = in.nextLine();
		Valet v = new Valet(car, gID, false, -1);
		int result = addCar(v);
		String msg = result == 1
				? "The car has been succesfully added to the valet!"
						: "Something went wrong! Try again";
		if(result == 1)
		{
			String sql = "SELECT * FROM VALET WHERE gID = ?";
			int ticketId = 0;
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try {
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, gID);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					ticketId = rs.getInt("parkingNum");
				}



			}catch(SQLException e)
			{
				System.out.println(e.getMessage());
			} finally{
				rs.close();
				pstmt.close();

			}

			msg += "your ticket id is " + ticketId;


		}
		System.out.println(msg);

	}


	public static int addCar(Valet v) throws SQLException
	{
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String addCar = "INSERT INTO Valet (gID, Car, Requested) VALUES (?, ? , ?);";
			pstmt = connection.prepareStatement(addCar);
			pstmt.setInt(1, v.getgID());
			pstmt.setString(2, v.getCar());
			pstmt.setBoolean(3, false);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
		}

		return result;
	}

	public static void removeCar() throws SQLException
	{
		System.out.println("Please provide the ticket number:");
		int ticId = Integer.parseInt(in.nextLine().trim());
		String sql = "DELETE FROM Valet Where parkingNum = ?";

		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, ticId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pstmt.close();
		}

		String msg = result == 1 ? "The car is removed from the valet and returned to the owner" : "Something went wrong please try again";
		System.out.println(msg);


	}

	public static void returnCar() throws SQLException
	{
		String sql = "UPDATE valet SET requested = ? WHERE parkingNum = ?";
		System.out.println("Please provide the ticket number:");
		int ticId = Integer.parseInt(in.nextLine().trim());

		PreparedStatement pstmt = null;
		int result = 0;

		try {

			pstmt = connection.prepareStatement(sql);
			pstmt.setBoolean(1, false);
			pstmt.setInt(2, ticId);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
		}

		String msg = result == 1 ? "Your car has been sent to the valet." : "Something went wrong please try again";
		System.out.println(msg);


	}
}