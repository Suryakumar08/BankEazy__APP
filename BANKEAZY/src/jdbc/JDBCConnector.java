package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.CustomBankException;

public class JDBCConnector {
	private static final String URL = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Surya@131419@sS";
	
	private static Connection connection = null;

	private JDBCConnector() {
		
	}


	private static void establishConnection(String dbName) throws CustomBankException{
		if(connection == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(URL + dbName, USERNAME, PASSWORD);
			} catch (ClassNotFoundException e) {
				throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e.getCause());
			} catch (SQLException e) {
				throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e.getCause());
			}			
		}
	}

	public static Connection getConnection() throws CustomBankException{
		if (connection == null) {
			establishConnection("BankEazy");
		}
		return connection;
	}
	
	public static void closeConnection() {
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			
		}
	}

}
