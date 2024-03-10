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
	
	private static void displayClientNames() {
		try(Connection connection = getConnection()){
			String query = "select * from client_info";
			try (Statement statement = connection.createStatement()){
				try (ResultSet resultSet = statement.executeQuery(query)){
					System.out.println("( Client ID, Client Name )");
					while(resultSet.next()) {
						int id = resultSet.getInt("Client_id");
						String firstName = resultSet.getString("first_name");
						String lastName = resultSet.getString("last_name");
						System.out.println(id + ", " + firstName + " " + lastName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteClient(int client_id) {
		try(Connection connection = getConnection()){
			String query = "delete from client_info where client_id =?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setInt(1, client_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Client Deleted");
				} else {
					System.out.println("no data deleted");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void addService(String serviceName, Float price ) {
		String status = "available";
		try (Connection connection = getConnection()){
			String insertQuery = "insert into service (service_name, status, price) values (?,?,?)" ;
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
				preparedStatement.setString(1,  serviceName);
				preparedStatement.setString(2, status);
				preparedStatement.setFloat(3, price);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service added");
				} else {
					System.out.println("Failed to add Service");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void updateServiceStatus(int service_id, String status) {
		try (Connection connection = getConnection()){
			String query = "update service set status = ? where service_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setString(1, status);
				preparedStatement.setInt(2, service_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service status updated");
				} else {
					System.out.println("no data updated");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	private static void updateServicePrice(int service_id, Float price) {
		try (Connection connection = getConnection()){
			String query = "update service set price = ? where service_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setFloat(1, price);
				preparedStatement.setInt(2, service_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service price updated");
				} else {
					System.out.println("no data updated");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	private static void displayAllService() {
		try(Connection connection = getConnection()){
			String query = "select * from service";
			try (Statement statement = connection.createStatement()){
				try (ResultSet resultSet = statement.executeQuery(query)){
					System.out.println("( Service ID, Service Name )");
					while(resultSet.next()) {
						int id = resultSet.getInt("service_id");
						String serviceName = resultSet.getString("service_name");
						System.out.println(id + ", " + serviceName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void displayAvailableService() {
		try(Connection connection = getConnection()){
			String query = "select * from service where status = 'available' ";
			try (Statement statement = connection.createStatement()){
				try (ResultSet resultSet = statement.executeQuery(query)){
					System.out.println("( Service ID, Service Name )");
					while(resultSet.next()) {
						int id = resultSet.getInt("service_id");
						String serviceName = resultSet.getString("service_name");
						System.out.println(id + ", " + serviceName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void displayService(int service_id) {
	    try (Connection connection = getConnection()) {
	        String query = "select * from service where service_id = ?";
	        
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, service_id);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    String serviceName = resultSet.getString("service_name");
	                    float servicePrice = resultSet.getFloat("price");
	                    String status = resultSet.getString("status");
	                    
	                    System.out.println(service_id + ": " + serviceName);
	                    System.out.println("   Price: " + servicePrice);
	                    System.out.println("   Status: " + status);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/*
	private static void deleteService(int service_id) {
		try(Connection connection = getConnection()){
			String query = "delete from service where service_id =?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setInt(1, service_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service Deleted");
				} else {
					System.out.println("no data deleted");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	*/
	
	private static int getClientID(String firstName, String lastName) {
		 try (Connection connection = getConnection()) {
		        String query = "select client_id from client_info where first_name = ? and last_name = ?";
		        
		        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		            preparedStatement.setString(1, firstName);
		            preparedStatement.setString(2, lastName);

		            try (ResultSet resultSet = preparedStatement.executeQuery()) {
		                while (resultSet.next()) {
		                    int client_id = resultSet.getInt("client_id");
		                    
		                    return client_id;
		                }
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
		return 0;
		
	}
	
	private static String getClientFirstName(int client_id) {
		 try (Connection connection = getConnection()) {
		        String query = "select first_name from client_info where client_id = ?";
		        
		        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		            preparedStatement.setInt(1, client_id);

		            try (ResultSet resultSet = preparedStatement.executeQuery()) {
		                while (resultSet.next()) {
		                    String firstName = resultSet.getString("first_name");
		                    
		                    return firstName;
		                }
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
		return "";
		
	}
	
	private static String getClientLastName(int client_id) {
		 try (Connection connection = getConnection()) {
		        String query = "select last_name from client_info where client_id = ?";
		        
		        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		            preparedStatement.setInt(1, client_id);

		            try (ResultSet resultSet = preparedStatement.executeQuery()) {
		                while (resultSet.next()) {
		                    String lastName = resultSet.getString("last_name");
		                    
		                    return lastName;
		                }
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
		return "";
		
	}
	
	
	
	private static void addInvoice(int client_id, String date ) {
		try (Connection connection = getConnection()){
			String insertQuery = "insert into invoice (client_id, status, price) values (?,?)" ;
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
				preparedStatement.setInt(1,  client_id);
				preparedStatement.setString(2, date);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service added");
				} else {
					System.out.println("Failed to add Service");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void displayAllInvoice() {
		try(Connection connection = getConnection()){
			String query = "select * from invoice";
			try (Statement statement = connection.createStatement()){
				try (ResultSet resultSet = statement.executeQuery(query)){
					System.out.println("( Invoice ID, Client Name, Date)");
					while(resultSet.next()) {
						int id = resultSet.getInt("invoice_id");
						String firstName = getClientFirstName(id);
						String lastName = getClientLastName(id);
						String date = resultSet.getString("date");
						System.out.println(id + ", " + firstName + " " + lastName + ", " + date);
					}
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
		
		displayAllInvoice();
		//System.out.println(getClientFirstName(1));
		//System.out.println(getClientLastName(1));
		//int x = getClientID("Paul","Atreidis");
		//System.out.println(x);
		//addClient("Luke","Skywalker","09167946748");
		//System.out.println(clientCheck("Luke","Skywalker"));
		//updateClientName(3, "Luke", "Darnok");	
		//updateClientContact(3,"09090909");
		//displayClientNames();
		//deleteClient(3);
		//addService("General Purpose", 1000f);
		//addService("Wedding", 6000f);
		//displayAvailableService();
		//updateServiceStatus(1,"available");
		//updateServicePrice(2, 8000f);
		
		//displayAvailableService();
		//System.out.println("");
		//displayAllService();
		
		//displayService(1);
		 
		 
		   }
	}
