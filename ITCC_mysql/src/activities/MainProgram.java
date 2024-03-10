package activities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainProgram {
	
	//database connection
	private static final String URL = "jdbc:mysql://localhost:3306/va_invoice_system?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
	private static final String USERNAME = "myuser";
	private static final String PASSWORD = "1234";
	
	
	
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	private static void addClient(String firstName, String lastName, String contact ) {
		try (Connection connection = getConnection()){
			String insertQuery = "insert into client_info (first_name,last_name,contact_info) values (?,?,?)" ;
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
				preparedStatement.setString(1,  firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, contact);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Client info added");
				} else {
					System.out.println("Failed to add Client info");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		 
		addClient("Luke","Skywalker","09167946748");
		 
		 		
		 
		 
		 
		 
		 
		   }
	}
