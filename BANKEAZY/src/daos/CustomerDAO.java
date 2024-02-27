package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import model.Customer;

public class CustomerDAO implements CustomerDaoInterface{
	
	private Connection connection = null;
	
	StringBuilder insertUserQuery = new StringBuilder("insert into User(name, password, mobile, gender, dob, status, type) values(?, ?, ?, ?, ?, ?, ?)");
	StringBuilder insertCustomerQuery = new StringBuilder("insert into Customer values(?, ?, ?)");
	
	public CustomerDAO() throws CustomBankException{
		connection = JDBCConnector.getConnection();
	}
	
	public int addCustomer(Customer customer) throws CustomBankException{
		int newId = -1;
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement statement = connection.prepareStatement(insertUserQuery.toString(), Statement.RETURN_GENERATED_KEYS)) {

				statement.setObject(1, customer.getName());
				statement.setObject(2, customer.getPassword());
				statement.setObject(3, customer.getMobile());
				statement.setObject(4, customer.getGender());
				statement.setObject(5, customer.getDob());
				statement.setObject(6, customer.getStatus(1));
				statement.setObject(7, customer.getType(1));

				int result = statement.executeUpdate();
				if (result == 0) {
					throw new CustomBankException(CustomBankException.ERROR_OCCURRED);
				}

				try (ResultSet genKeys = statement.getGeneratedKeys()) {
					while (genKeys.next()) {
						newId = genKeys.getInt(1);
					}
				}

				try (PreparedStatement statement1 = connection.prepareStatement(insertCustomerQuery.toString())) {
					statement1.setObject(1, newId);
					statement1.setObject(2, customer.getPan());
					statement1.setObject(3, customer.getAadhar());

					int noOfRows = statement1.executeUpdate();
					connection.commit();
					if (noOfRows == 0) {
						return -1;
					}
				}
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.addSuppressed(e1);
			}
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
		return newId;

	}
	
}
