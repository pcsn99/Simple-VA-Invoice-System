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
	
	//MYSQL QUERIES ---------------------------------------------------------------------------------------------------
	
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
	
	private static boolean clientCheck(String firstName, String lastName) {
		try (Connection connection = getConnection()){
			String query = "select count(*) from client_info where first_name =? and last_name =?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				try(ResultSet resultSet = preparedStatement.executeQuery()){
					resultSet.next();
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static void updateClientName(int client_id, String firstName, String lastName) {
		try (Connection connection = getConnection()){
			String query = "update client_info set first_name = ?, last_name = ? where client_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setInt(3, client_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Client name updated");
				} else {
					System.out.println("no data updated");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void updateClientContact(int client_id, String contact) {
		try (Connection connection = getConnection()){
			String query = "update client_info set contact_info = ? where client_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setString(1, contact);
				preparedStatement.setInt(2, client_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Client contact info updated");
				} else {
					System.out.println("no data updated");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// MENUS ---------------------------------------------------------------------------------------------------------------------
	
	private static void MainMenu() {
		
		
		
	}
	
	
	
	
	
	//MAIN -----------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		 
		//addClient("Luke","Skywalker","09167946748");
		//System.out.println(clientCheck("Luke","Skywalker"));
		//updateClientName(3, "Luke", "Darnok");	
		//updateClientContact(3,"09090909");
		 
		 
		 
		 
		 
		   }
	}
