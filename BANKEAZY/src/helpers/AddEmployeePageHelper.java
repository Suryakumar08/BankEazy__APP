package helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import daos.EmployeeDAO;
import daos.EmployeeDaoInterface;
import exception.CustomBankException;
import model.Employee;
import utilities.Sha_256;

public class AddEmployeePageHelper {
	public long getDobInMillis(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return zonedDateTime.toInstant().toEpochMilli();
	}

	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public int addEmployee(Employee employee) throws CustomBankException {
		String password = employee.getPassword();
		employee.setPassword(Sha_256.getHashedPassword(password));
		EmployeeDaoInterface employeeDao = new EmployeeDAO();
		return employeeDao.addEmployee(employee);
	}
}
