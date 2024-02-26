package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.AddEmployeePageHelper;
import model.Employee;
import utilities.InputHelper;
import utilities.Validators;

public class AddEmployeePage {
	private static Logger logger = BankEazyApp.logger;

	public static int run() {
		int newlyAddedId = -1;
		AddEmployeePageHelper helper = new AddEmployeePageHelper();
		while (true) {
			try {
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
				long dob = helper.getDobInMillis(dobString);

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
				employee.setStatus(1);
				employee.setType("employee");
				employee.setSalary(salary);
				employee.setJoinedDate(helper.getCurrentTime());
				employee.setBranchId(branchId);
				employee.setRole(role);

				newlyAddedId = helper.addEmployee(employee);
				break;
				
			} catch (CustomBankException e) {
				logger.warning(e.getMessage());
			}
		}
		return newlyAddedId;
	}
}
