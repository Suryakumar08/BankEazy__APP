package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import model.Employee;

public class EmployeeDAO implements EmployeeDaoInterface {
	private Connection connection = null;

	StringBuilder selectEmployeeQuery = new StringBuilder(
			"select * from User join Employee on User.id = Employee.id where ");
	StringBuilder insertUserQuery = new StringBuilder("insert into User(name, password, mobile, gender, dob, status, type) values(?, ?, ?, ?, ?, ?, ?)");
	StringBuilder insertEmployeeQuery = new StringBuilder("insert into Employee values(?, ?, ?, ?, ?)");

	public EmployeeDAO() throws CustomBankException {
		connection = JDBCConnector.getConnection();
	}

	@Override
	public Employee getEmployee(int userId) throws CustomBankException {
		StringBuilder query = new StringBuilder(selectEmployeeQuery).append("User.id = ?");
		try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
			statement.setInt(1, userId);

			Employee employee = new Employee();
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					int id = result.getInt("id");
					String name = result.getString("name");
					String password = result.getString("password");
					String mobile = result.getString("mobile");
					String gender = result.getString("gender");
					long dob = result.getLong("dob");
					int status = result.getInt("status");
					int type = result.getInt("type");
					int salary = result.getInt("salary");
					long joiningDate = result.getLong("joiningDate");
					int branchId = result.getInt("branchId");
					int role = result.getInt("role");

					employee.setId(id);
					employee.setName(name);
					employee.setPassword(password);
					employee.setMobile(mobile);
					employee.setGender(gender);
					employee.setDob(dob);
					employee.setStatus(status);
					employee.setType(type);
					employee.setSalary(salary);
					employee.setJoinedDate(joiningDate);
					employee.setBranchId(branchId);
					employee.setRole(role);

					return employee;

				} else {
					return null;
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
			try (PreparedStatement statement = connection.prepareStatement(insertUserQuery.toString(), Statement.RETURN_GENERATED_KEYS)) {

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
						System.out.println("In EmployeeDao : " + newId);
					}
				}

				try (PreparedStatement statement1 = connection.prepareStatement(insertEmployeeQuery.toString())) {
					statement1.setObject(1, newId);
					statement1.setObject(2, employee.getSalary());
					statement1.setObject(3, employee.getJoinedDate());
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
}
