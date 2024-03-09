package activities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainProgram {
	public static void main(String[] args) {
		 try (   // Step 1: Construct a database 'Connection' object called 'conn'
		         Connection conn = DriverManager.getConnection(
		               "jdbc:mysql://localhost:3306/va_invoice_system?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
		               "myuser", "1234");   // For MySQL only
		               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"
		         // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
		         Statement stmt = conn.createStatement();
		      ) {
		         // Step 3: Write a SQL query string. Execute the SQL query via the 'Statement'.
		         //  The query result is returned in a 'ResultSet' object called 'rset'.
		         String strSelect = "select client_id, first_name, last_name, total_spending from client_info";
		         System.out.println("The SQL statement is: " + strSelect + "\n"); // Echo For debugging
		         ResultSet rset = stmt.executeQuery(strSelect);
		         // Step 4: Process the 'ResultSet' by scrolling the cursor forward via next().
		         //  For each row, retrieve the contents of the cells with getXxx(columnName).
		         System.out.println("The records selected are:");
		         int rowCount = 0;
		         // Row-cursor initially positioned before the first row of the 'ResultSet'.
		         // rset.next() inside the whole-loop repeatedly moves the cursor to the next row.
		         // It returns false if no more rows.
		         while(rset.next()) {   // Repeatedly process each row
		            String firstName = rset.getString("first_name");  // retrieve a 'String'-cell in the row
		            String lastName = rset.getString("last_name");
		            float totalSpending = rset.getFloat("total_spending");  // retrieve a 'double'-cell in the row
		            System.out.println(firstName + " " + lastName + ", " + totalSpending);
		            ++rowCount;
		         }
		         System.out.println("Total number of clients = " + rowCount);

		      } catch(SQLException ex) {
		         ex.printStackTrace();
		      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
		   }
	}
