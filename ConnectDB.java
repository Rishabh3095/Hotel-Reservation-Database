import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.sql.DataSource;

// Source Professor Kim's Lecture Notes
class DataSourceFactory {
  public DataSource getMySQLDataSource() {

    Properties props = new Properties();
    InputStream fis = null;
    MysqlDataSource mysqlDS = null;
    try {

      fis =
          getClass()
              .getResourceAsStream(
                  "config/db.properties"); // swtiched to InputStream to read db.propertied from  relative path
                                          
      props.load(fis);
      mysqlDS = new MysqlDataSource();
      mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
      mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
      mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));

    } catch (IOException e) {
      System.out.println("db.properties is not found");
      e.printStackTrace();
    }
    return mysqlDS;
  }
}

public class ConnectDB {

  public  Connection connection = null;
  
  
  public  Connection initConnection(){
	  
	  DataSource ds = new DataSourceFactory().getMySQLDataSource();

      try {
        connection = ds.getConnection();
      } catch (SQLException e) {
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
      }

      if (connection != null) {
        System.out.println("You made it, take control your database now!");
        return connection;
      } else {
        System.out.println("Failed to make connection!");
        return null;
      }
    
  }
  
  public  void closeConnection(){
	  if(connection != null){
		  try {
			connection.close();
			System.out.println("DB connection close! Bye Bye then");
		} catch (SQLException e) {
			System.out.println("Error closing database connection!");
			e.printStackTrace();
		}
	  }
  }

  public static void main(String[] args) {
	  ConnectDB conn = new ConnectDB();
	  Connection connection = conn.initConnection();
	  
	  conn.closeConnection(); 
	 
  }
}
