import java.util.*;
import java.sql.*;


public class TestDbConnection{
	
	public static Connection conn = null;
	public static Statement stmt = null;
	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	   static final String PORT = "3306";
	   static final String DB_URL = "jdbc:mysql://127.0.0.1";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	
	
	public static void main(String[] args){
		try {
			Class.forName(JDBC_DRIVER).newInstance();
		} catch (Exception ex) {
			System.out.println("JDBC driver not found");
		}
		try {
			System.out.println("Establishing connection to database...");
			conn = DriverManager.getConnection(DB_URL+":"+PORT, USER, PASS);
			conn.close();

		} catch (SQLException ex) {
			System.out.println("SQL exeption thrown");
		}
		System.out.println("Connected to database!" + conn.toString());
		

	}
}