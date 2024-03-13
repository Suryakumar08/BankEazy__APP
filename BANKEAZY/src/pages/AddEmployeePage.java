package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.EmployeeHelper;
import model.Employee;
import utilities.InputHelper;
import utilities.Utilities;
import utilities.Validators;

public class AddEmployeePage {
	private static Logger logger = BankEazyApp.logger;

	public static int run() throws CustomBankException{
		int newlyAddedId = -1;
		EmployeeHelper helper = new EmployeeHelper();
		while (true) {
			try {
				logger.fine("Add a employee : ");
				logger.info("Enter name :");
				String name = InputHelper.getString();
				Validators.validateInput(name);

				logger.info("Enter password :");
				String password = InputHelper.getString();
				Validators.validatePassword(password);

				logger.info("Enter mobile no :");
				String mobile = InputHelper.getString();
				Validators.validateMobile(mobile);

				logger.info("Enter gender :");
				String gender = InputHelper.getString();
				Validators.validateInput(gender);

				logger.info("Enter Date of Birth in format(dd-MM-yyyy) :");
				String dobString = InputHelper.getString();
				Validators.validateInput(dobString);
				long dob = Utilities.getDobInMillis(dobString);

				logger.info("Enter Employee salary : ");
				int salary = InputHelper.getInt();

				logger.info("Enter branch Id : ");
				int branchId = InputHelper.getInt();

				logger.info("Enter Employee role(1 -> Admin 2 -> Employee) : ");
				int role = InputHelper.getInt();

				Employee employee = new Employee();
				employee.setName(name);
				employee.setPassword(password);
				employee.setMobile(mobile);
				employee.setGender(gender);
				employee.setDob(dob);
				employee.setSalary(salary);
				employee.setJoiningDate(Utilities.getCurrentTime());
				employee.setBranchId(branchId);

				newlyAddedId = helper.addEmployee(employee);
				break;
				
			} catch (CustomBankException e) {
				logger.warning(e.getMessage());
			}
		}
		return newlyAddedId;
	}
}
