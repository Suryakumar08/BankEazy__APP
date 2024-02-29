package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import model.Customer;

public class CustomerDAO implements CustomerDaoInterface{
	
	private Connection connection = null;
	
	private String insertUserQuery = "insert into User(name, password, mobile, gender, dob, status, type) values(?, ?, ?, ?, ?, ?, ?)";
	private String insertCustomerQuery = "insert into Customer values(?, ?, ?)";
	private String getCustomerQuery = "select User.id as userId, name, password, mobile, gender, dob, status, type, pan, aadhar from User join Customer on User.id = Customer.userId";
	
	public CustomerDAO() throws CustomBankException{
		connection = JDBCConnector.getConnection();
	}
	
	public int addCustomer(Customer customer) throws CustomBankException{
		int newId = -1;
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement statement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {

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

				try (PreparedStatement statement1 = connection.prepareStatement(insertCustomerQuery)) {
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
	
	
	public Customer getCustomer(int customerId) throws CustomBankException {
		Customer customer = null;
		StringBuilder query = new StringBuilder(getCustomerQuery).append(" where User.id = ?");
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			statement.setInt(1, customerId);
			try(ResultSet customerSet = statement.executeQuery()){
				if(customerSet.next()) {
					customer = new DAOHelper().mapResultSetToGivenClassObject(customerSet, Customer.class);
				}
				return customer;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}
	
	
	public Map<Integer, Customer> getCustomers() throws CustomBankException{
		Map<Integer, Customer> customerMap = null;
		try(Statement statement = connection.createStatement()){
			try(ResultSet records = statement.executeQuery(getCustomerQuery)){
				customerMap = new HashMap<>();
				while(records.next()) {
					Customer customer = new DAOHelper().mapResultSetToGivenClassObject(records, Customer.class);
					customerMap.put(customer.getUserId(), customer);
				}
				return customerMap;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	@Override
	public boolean updateField(Customer customer) throws CustomBankException {
//		StringBuilder updateQuery = new StringBuilder("update Customer ");
		return false;
	}
	
	public void addSets(String updateQuery, Customer customer) {
		if(customer.getName() != null) {
			
		}
	}
	
}
