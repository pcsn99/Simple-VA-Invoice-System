package activities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
	
	private static void addInvoice(int client_id, java.util.Date dateEvent ) {
		try (Connection connection = getConnection()){
			String insertQuery = "insert into invoice (client_id, event_date) values (?,?)" ;
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	            preparedStatement.setInt(1, client_id);
	            preparedStatement.setTimestamp(2, new java.sql.Timestamp(dateEvent.getTime())); 
	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Invoice added");
	            } else {
	                System.out.println("Failed to add invoice");
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
						String date = resultSet.getString("event_date");
						System.out.println(id + ", " + firstName + " " + lastName + ", " + date);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void displayInvoice(int client_id) {
		try (Connection connection = getConnection()) {
	        String query = "select * from invoice where client_id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, client_id);

	            try (ResultSet resultSet = preparedStatement.executeQuery()){
					System.out.println("( Invoice_ID, Date)");
					while(resultSet.next()) {
						int id = resultSet.getInt("invoice_id");
						Date date = resultSet.getDate("event_date");
						System.out.println("  "+id + ", " + date);
					}
				}
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private static int getInvoiceID(String first_Name, String last_Name, java.util.Date date) {
		try(Connection connection = getConnection()){
			String query = "select * from invoice where first_name = ? and last_name = ? and event_date = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, first_Name);
	            preparedStatement.setString(2, last_Name);
	            preparedStatement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));

	            try (ResultSet resultSet = preparedStatement.executeQuery(query)){
					while(resultSet.next()) {
						int id = resultSet.getInt("invoice_id");
						
						return id;
					}
				}
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private static void addServiceToInvoice(int invoice_id, int service_id, int service_amount) {
		try (Connection connection = getConnection()){
			String insertQuery = "insert into invoice_services (invoice_id, service_id, service_amount) values (?,?,?)" ;
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
				preparedStatement.setInt(1,  invoice_id);
				preparedStatement.setInt(2, service_id);
				preparedStatement.setFloat(3, service_amount);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service added to invoice");
				} else {
					System.out.println("Failed to add Service to invoice");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	private static void updateInvoiceServiceAmount(int invoice_services_id, Float amount) {
		try (Connection connection = getConnection()){
			String query = "update invoice_services set service_amount = ? where invoice_services_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setFloat(1, amount);
				preparedStatement.setInt(2, invoice_services_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service amount updated");
				} else {
					System.out.println("no data updated");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	private static void displayInvoiceServices(int invoice_id) {
		try (Connection connection = getConnection()) {
	        String query = "SELECT invoice_services.invoice_services_id, service.service_name, invoice_services.service_amount, service.price " +
                   		   "FROM invoice_services  " +
                   		   "JOIN service  ON invoice_services.service_id = service.service_id " +
                   		   "JOIN invoice  ON invoice_services.invoice_id = invoice.invoice_id " +
	        			   "WHERE invoice.invoice_id = ?";
	        
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, invoice_id);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            	 System.out.println("--------------------------------");
	            	 System.out.println("|  Invoice Services  |");
	            	 System.out.println("(Invoice_service_ID, service name, price per, service amount)");
	            	 System.out.println("");
	                    while (resultSet.next()) {
	                        int invoiceServiceId = resultSet.getInt("invoice_services_id");
	                        float price = resultSet.getFloat("price");
	                        String serviceName = resultSet.getString("service_name");
	                        float serviceAmount = resultSet.getFloat("service_amount");
	                        
	                        System.out.println(invoiceServiceId + ", "+serviceName+", "+price+", "+serviceAmount);
	                    }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void removeInvoiceService(int invoice_services_id) {
		try(Connection connection = getConnection()){
			String query = "delete from invoice_services where invoice_services_id =?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setInt(1, invoice_services_id);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Service Removed");
				} else {
					System.out.println("no data removed");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static float totalSpendingOfClient(int client_id) {
		
		 try (Connection connection = getConnection()) {
		        String query = "select " +
		                	   "sum(service.price * invoice_services.service_amount) as total_spending " +
		                	   "from client_info " +
		                	   "join invoice on client_info.client_id = invoice.client_id " +
		                	   "join invoice_services on invoice.invoice_id = invoice_services.invoice_id " +
		                	   "join service on service.service_id = invoice_services.service_id " +
		                	   "where client_info.client_id = ?";

		        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		            preparedStatement.setInt(1, client_id);

		            try (ResultSet resultSet = preparedStatement.executeQuery()) {
		                while (resultSet.next()) {
		                    return resultSet.getFloat("total_spending");
		                }
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return 0;
	}
	
	 private static float displayInvoiceSpending(int invoice_id) {
	        try (Connection connection = getConnection()) {
	            String query = "select " +
	            			   "invoice.*, " +
	            			   "sum(service.price * invoice_services.service_amount) as total_spending "+
	            			   "from invoice "+
	            			   "join invoice_services  ON invoice.invoice_id = invoice_services.invoice_id "+
	            			   "join service  ON service.service_id = invoice_services.service_id "+
	            			   "where invoice.invoice_id = ?;";

	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		            preparedStatement.setInt(1, invoice_id);

		            try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            	 
		                    while (resultSet.next()) {
		                        float serviceAmount = resultSet.getFloat("total_spending");

		                        return serviceAmount;
		                    }
		            }
		        }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return 0;
	    }
	 
	 private static void deleteInvoice(int invoice_id) {
			try(Connection connection = getConnection()){
				String query = "delete from invoice where invoice_id =?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
					preparedStatement.setInt(1, invoice_id);
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0) {
						System.out.println("Invoice Deleted");
					} else {
						System.out.println("no data deleted");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	 
	 private static void incomePastThirtyDays() {
		 try (Connection connection = getConnection()) {
	            String sql = "SELECT SUM(service.price * invoice_services.service_amount) AS total_income " +
	                         "FROM invoice " +
	                         "JOIN invoice_services ON invoice.invoice_id = invoice_services.invoice_id " +
	                         "JOIN service ON invoice_services.service_id = service.service_id " +
	                         "WHERE invoice.event_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) AND CURDATE()";

	            try (PreparedStatement statement = connection.prepareStatement(sql);
	                 ResultSet resultSet = statement.executeQuery()) {

	                if (resultSet.next()) {
	                    double totalIncome = resultSet.getDouble("total_income");
	                    System.out.println("Total Income: " + totalIncome);
	                } else {
	                    System.out.println("No results found");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 private static void mostPopularServicePastThirtyDays() {
		 try (Connection connection = getConnection()) {
			 String sql = "SELECT service.service_name, SUM(invoice_services.service_amount) AS total_quantity " +
                     "FROM invoice " +
                     "JOIN invoice_services ON invoice.invoice_id = invoice_services.invoice_id " +
                     "JOIN service ON invoice_services.service_id = service.service_id " +
                     "WHERE invoice.event_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) AND CURDATE() " +
                     "GROUP BY service.service_name " +
                     "ORDER BY total_quantity DESC " +
                     "LIMIT 1";

			 try (PreparedStatement statement = connection.prepareStatement(sql);
					 ResultSet resultSet = statement.executeQuery()) {

				 if (resultSet.next()) {
					 String topServiceName = resultSet.getString("service_name");
					 int totalQuantity = resultSet.getInt("total_quantity");
					 System.out.println("Top Service: " + topServiceName + ", Total Quantity: " + totalQuantity);
				 } else {
					 System.out.println("No results found");
				 }
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
    
		 }
	 }
	 
	 private static void topClientPastThirtyDays() {
		 try (Connection connection = getConnection()) {
	            String query = "SELECT " +
	                    "client_info.client_id, " +
	                    "client_info.first_name, " +
	                    "client_info.last_name, " +
	                    "SUM(service.price * invoice_services.service_amount) AS total_spending " +
	                    "FROM client_info " +
	                    "JOIN invoice ON client_info.client_id = invoice.client_id " +
	                    "JOIN invoice_services ON invoice.invoice_id = invoice_services.invoice_id " +
	                    "JOIN service ON invoice_services.service_id = service.service_id " +
	                    "WHERE invoice.event_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) AND CURDATE() " +
	                    "GROUP BY client_info.client_id " +
	                    "ORDER BY total_spending DESC " +
	                    "LIMIT 1";

	            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	                 ResultSet resultSet = preparedStatement.executeQuery()) {

	                if (resultSet.next()) {
	                	String firstName = resultSet.getString("first_name");
	                	String lastName = resultSet.getString("last_name");
	                    float topClientSpending = resultSet.getFloat("total_spending");
	                    System.out.println("Top Client Past Thirty Days:");
	                    System.out.println("Client name: "+firstName + " "+lastName);
	                    System.out.println("Total Spending: "+topClientSpending);
	                } else {
						 System.out.println("No results found");
					 }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	 
	 
	 private static Date parseDate(String dateString) {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            return sdf.parse(dateString);
	        } catch (ParseException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	
	
	
	
	// MENUS ---------------------------------------------------------------------------------------------------------------------
	
	private static void MainMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("--------------------------------------");
		System.out.println("|  Virtual Assistant Invoice System  |");
		System.out.println("--------------------------------------");
		System.out.println("1. Invoice Management ");
		System.out.println("2. Client Management ");
		System.out.println("3. Service Management ");
		System.out.println("");
		System.out.println("4. EXIT");
		System.out.println("");
		System.out.print("Input integer of management system you want to access: ");
		try {
			int choice = scanner.nextInt();
			
			switch (choice) {
				
			case 1:
				System.out.println("---------------------------------------------------------");
				invoiceManagementMenu();
				break;
			
			case 2:
				System.out.println("---------------------------------------------------------");
				//client management menu
				break;
			
			case 3:
				System.out.println("---------------------------------------------------------");
				//service management menu
				break;
			
			case 4:
				System.out.println("GoodBye!");
				break;
			
			default:
				System.out.println("---------------------------------------------------------");
				System.out.println("Integer choice does not exist, please try again\n");
				MainMenu();
			}
			
			
		}catch(java.util.InputMismatchException e) {
			System.out.println("Error input, please try again\n");
			MainMenu();
		}
		
	}
	
	private static void clientInvoiceManagementMenu(int client_id) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("------------------");
		System.out.println("|  Invoice List  |");
		displayInvoice(client_id);
		System.out.println("");
		System.out.print("input Invoice_ID to display Invoice info: ");
			try {
				try {
					int choiceInvoice = scanner.nextInt();
					displayInvoiceServices(choiceInvoice);
					System.out.println("");
					System.out.println("");
					System.out.println("Total Invoice Spending: "+displayInvoiceSpending(choiceInvoice));
					
					System.out.println("-------------------------");
					System.out.println("1. Add Service");
					System.out.println("2. Edit Service Amount");
					System.out.println("3. Delete Service");
					System.out.println("0. BACK");
					
					try {
						System.out.println("-----------------------------");
						System.out.print("Input option of your choice: ");
						int choiceA = scanner.nextInt();
						
						switch (choiceA) {
						case 1:
							System.out.println("|  Available Services  |");
							displayAvailableService();
							System.out.println("-------------------");
							System.out.print("Input service_id of your choice: ");
							int choiceB = scanner.nextInt();
							System.out.print("Input amount: ");
							int choiceBAmount = scanner.nextInt();
							addServiceToInvoice(choiceInvoice,choiceB,choiceBAmount);
							clientInvoiceManagementMenu(client_id);
						
						case 2:
							
							System.out.print("Input invoice_service_id of your choice: ");
							int choiceC = scanner.nextInt();
							System.out.print("Input amount: ");
							float choiceCAmount = scanner.nextFloat();
							updateInvoiceServiceAmount(choiceC , choiceCAmount);
							clientInvoiceManagementMenu(client_id);
						
						case 3:
							
							System.out.print("Input service_id of your choice: ");
							int choiceD = scanner.nextInt();
							removeInvoiceService(choiceD);
							clientInvoiceManagementMenu(client_id);
						
						case 0:
							viewOrEditInvoiceMenu();
							
						default:
							System.out.println("---------------------------------------------------------");
							System.out.println("Integer choice does not exist, please try again\n");
							clientInvoiceManagementMenu(client_id);
							
						}
						
					}catch(java.util.InputMismatchException e) {
						System.out.println("Error input, please try again\n");
						clientInvoiceManagementMenu(client_id);
					}
					
				}catch(java.util.InputMismatchException e) {
					System.out.println("Error input, please try again\n");
					clientInvoiceManagementMenu(client_id);
				}
				
			
			}catch(java.util.InputMismatchException e) {
				System.out.println("Error input, please try again\n");
				clientInvoiceManagementMenu(client_id);
			}
	}
	
	private static void invoiceServicesOptions() {
		System.out.println("-------------------------");
		System.out.println("1. Add Service");
		System.out.println("2. Edit Service Amount");
		System.out.println("3. Delete Service");
		System.out.println("0. BACK");
	}
	
	private static void invoiceManagementMenu() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("------------------------");
		System.out.println("|  Invoice Management  |");
		System.out.println("------------------------");
		System.out.println(" 1. Add New Invoice");
		System.out.println(" 2. View and Edit Invoice");
		System.out.println(" 3. BACK");
		System.out.println("-------------------------------");
		System.out.print("input integer of your choice: ");
		try {
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				System.out.println("---------------------------");
				addNewInvoice();
				break;
			
			case 2:
				System.out.println("---------------------------");
				viewOrEditInvoiceMenu();
				break;
			
			case 3:
				System.out.println("---------------------------");
				MainMenu();
				break;
				
			default:
				System.out.println("Error, please try again\n");
				invoiceManagementMenu();
				
			}
				
			
		}catch(java.util.InputMismatchException e) {
			System.out.println("Error input, please try again\n");
			invoiceManagementMenu();
		}
	}
	
	private static void viewOrEditInvoiceMenu() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("--------------------------");
		System.out.println("|  View or Edit Invoice  |");
		System.out.println("--------------------------");
		displayClientNames();
		System.out.println("0, BACK");
		System.out.println("");
		System.out.print("input Client_ID of client to display their invoices: ");
		try {
			
			int choice = scanner.nextInt();
			if (choice == 0) {
				invoiceManagementMenu();
			} else {
				clientInvoiceManagementMenu(choice);
			}
			
		}catch(java.util.InputMismatchException e) {
			System.out.println("Error input, please try again\n");
			viewOrEditInvoiceMenu();
		}
		
	}
	
	private static void addServicesMenu(int invoice_id) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("|  Services  |");
		displayAvailableService();
		System.out.println("0. DONE");
		
		try {
			
			System.out.print("input service_id of service you want to add:");
			int service_id = scanner.nextInt();
			
			if (service_id == 0) {
				viewOrEditInvoiceMenu();
			}else {
				System.out.print("enter amount: ");
				int amount = scanner.nextInt();
				addServiceToInvoice(invoice_id, service_id, amount);
				addServicesMenu(invoice_id);
			}
			
		}catch(java.util.InputMismatchException e) {
			System.out.println("Error input, please try again\n");
			viewOrEditInvoiceMenu();
		}
		
	}
	
	private static void addNewInvoice() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("---------------------");
		System.out.println("|  Add New Invoice  |");
		System.out.println("---------------------");
		System.out.print("Input client first name: ");
		String firstName = scanner.nextLine();
		System.out.print("Input client last name: ");
		String lastName = scanner.nextLine();
		Boolean check = clientCheck(firstName, lastName);
		
		if (check == true) {
			System.out.println("**Client Info Exists**");
			int client_id = getClientID(firstName,lastName);
			System.out.print("input date of event (YYYY-MM-DD): ");
			String date = scanner.nextLine();
			Date dateEvent = parseDate(date);
			addInvoice(client_id, dateEvent );
			int invoice_ID = getInvoiceID(firstName, lastName, dateEvent);
			addServicesMenu(invoice_ID);
			
		}else {
			System.out.println("**New Client**");
			System.out.print("Input contact number of client: ");
			String contact = scanner.nextLine();
			addClient(firstName,lastName,contact);
			int client_id = getClientID(firstName,lastName);
			System.out.print("input date of event (YYYY-MM-DD): ");
			String date = scanner.nextLine();
			Date dateEvent = parseDate(date);
			addInvoice(client_id, dateEvent );
			int invoice_ID = getInvoiceID(firstName, lastName, dateEvent);
			addServicesMenu(invoice_ID);
		}
		
		System.out.println();
	}
	
	//MAIN -----------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		
		
		//displayAllInvoice();
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
		//Date userDate = parseDate("2024-07-05");
		//addInvoice(1,new java.sql.Date(userDate.getTime()));
		//displayAllInvoice();
		// displayInvoice(1);
		
		//addServiceToInvoice(2,2,1);
		//displayInvoiceServices(1);
		//removeInvoiceService(5);
		//
		//incomePastThirtyDays();
		//System.out.println("Total Spending: "+ displayTotalSpending(2));
		//System.out.println(totalSpendingOfClient(1));
		//mostPopularServicePastThirtyDays();
		//topClientPastThirtyDays();
		
		MainMenu();
		
		   }
	}
