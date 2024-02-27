package daos;

import exception.CustomBankException;
import model.Employee;

public interface EmployeeDaoInterface {

	public Employee getEmployee(int userId) throws CustomBankException;

	public int addEmployee(Employee employee) throws CustomBankException;


}
