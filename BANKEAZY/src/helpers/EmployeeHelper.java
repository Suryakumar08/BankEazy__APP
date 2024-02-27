package helpers;

import daos.CustomerDAO;
import daos.CustomerDaoInterface;
import daos.EmployeeDAO;
import daos.EmployeeDaoInterface;
import exception.CustomBankException;
import model.Customer;
import model.Employee;
import utilities.Sha_256;

public class EmployeeHelper {

	public Employee getEmployee(int userId) throws CustomBankException {
		EmployeeDaoInterface employeeDao = new EmployeeDAO();
		Employee employee = employeeDao.getEmployee(userId);
		return employee;
	}
	
	public int addEmployee(Employee employee) throws CustomBankException {
		String password = employee.getPassword();
		employee.setPassword(Sha_256.getHashedPassword(password));
		employee.setStatus(1);
		employee.setType("employee");
		EmployeeDaoInterface employeeDao = new EmployeeDAO();
		return employeeDao.addEmployee(employee);
	}
	
	public int addCustomer(Customer customer) throws CustomBankException {
		String password = customer.getPassword();
		customer.setPassword(Sha_256.getHashedPassword(password));
		customer.setStatus(1);
		customer.setType("customer");
		CustomerDaoInterface customerDao = new CustomerDAO();
		return customerDao.addCustomer(customer);
	}
	
}
