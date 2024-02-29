package daos;

import java.util.Map;

import exception.CustomBankException;
import model.Employee;

public interface EmployeeDaoInterface {

	public Employee getEmployee(int userId) throws CustomBankException;

	public int addEmployee(Employee employee) throws CustomBankException;

	public Map<Integer, Employee> getAllEmployees() throws CustomBankException;

}
