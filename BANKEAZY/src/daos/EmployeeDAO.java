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
import model.Employee;

public class EmployeeDAO implements EmployeeDaoInterface {
	private Connection connection = null;

	private final String selectEmployeeQuery = "select * from User join Employee on User.id = Employee.userId";
	private final String insertUserQuery = "insert into User(name, password, mobile, gender, dob, status, type) values(?, ?, ?, ?, ?, ?, ?)";
	private final String insertEmployeeQuery = "insert into Employee values(?, ?, ?, ?, ?)";

	public EmployeeDAO() throws CustomBankException {
		connection = JDBCConnector.getConnection();
	}

	@Override
	public Employee getEmployee(int userId) throws CustomBankException {
		StringBuilder query = new StringBuilder(selectEmployeeQuery).append(" where User.id = ?");
		try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
			statement.setInt(1, userId);

			Employee employee = null;
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					employee = new DAOHelper().mapResultSetToGivenClassObject(result, Employee.class);
					return employee;
				} else {
					throw new CustomBankException(CustomBankException.USER_NOT_EXISTS);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e.getCause());
		}
	}
	
	

	@Override
	public int addEmployee(Employee employee) throws CustomBankException {
		int newId = -1;
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement statement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {

				statement.setObject(1, employee.getName());
				statement.setObject(2, employee.getPassword());
				statement.setObject(3, employee.getMobile());
				statement.setObject(4, employee.getGender());
				statement.setObject(5, employee.getDob());
				statement.setObject(6, employee.getStatus(1));
				statement.setObject(7, employee.getType(1));

				int result = statement.executeUpdate();
				if (result == 0) {
					throw new CustomBankException(CustomBankException.ERROR_OCCURRED);
				}

				try (ResultSet genKeys = statement.getGeneratedKeys()) {
					while (genKeys.next()) {
						newId = genKeys.getInt(1);
					}
				}

				try (PreparedStatement statement1 = connection.prepareStatement(insertEmployeeQuery)) {
					statement1.setObject(1, newId);
					statement1.setObject(2, employee.getSalary());
					statement1.setObject(3, employee.getJoiningDate());
					statement1.setObject(4, employee.getBranchId());
					statement1.setObject(5, employee.getRole(1));

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

	@Override
	public Map<Integer, Employee> getAllEmployees() throws CustomBankException {
		Map<Integer, Employee> employeeList = new HashMap<>();
		try (Statement statement = connection.createStatement()) {
			
			try (ResultSet result = statement.executeQuery(selectEmployeeQuery)) {
				while (result.next()) {
					Employee employee = new DAOHelper().mapResultSetToGivenClassObject(result, Employee.class);

					employeeList.put(employee.getId(), employee);

				} 
				return employeeList;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e.getCause());
		}
	}

}
