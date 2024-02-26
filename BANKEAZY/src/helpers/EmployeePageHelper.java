package helpers;

import daos.EmployeeDAO;
import exception.CustomBankException;
import model.Employee;

public class EmployeePageHelper {

	public int getEmployeeRole(int userId) throws CustomBankException {
		EmployeeDAO employeeDao = new EmployeeDAO();
		Employee employee = employeeDao.getEmployee(userId);
		int result = employee.getRole(1);
		return result;
	}
}
