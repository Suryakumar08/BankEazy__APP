package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.EmployeeHelper;
import model.Employee;
import utilities.InputHelper;

public class EmployeePage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int userId) throws CustomBankException {
		EmployeeHelper helper = new EmployeeHelper();
		Employee currEmployee = helper.getEmployee(userId);
		int empRole = currEmployee.getRole(1);
			boolean continueProgram = true;
			while (continueProgram) {
				logger.info(
						"Hey Admin!!!\n1) Add Employee\n2) Add Customer\n3) Add Customer Account\n4) Edit User\n5) View Employee List\n6) View Customer List\n7) View Transaction Details\n8) View Profile\n9) Edit Profile\n10) Logout\n\nEnter your choice : \n");
				try {
					int employeeChoice = InputHelper.getInt();
					switch (employeeChoice) {
					case 1: {
						if(empRole == 1) {
							int newUserId = AddEmployeePage.run();
							logger.fine("Employee Added Successfully!\nAdded User's Id : " + newUserId + "\n\n");
						}
						else {
							logger.fine("Only Admins Have the Permission to add Employees!");
						}
						break;
					}
					case 2: {
						int newUserId = AddCustomerPage.run();
						logger.fine("Customer created successfully!\nCreated Customer Id : " + newUserId + "\n\n");
					}
					case 3: {
						long accNo = 0l;
						if(empRole == 1) {
							accNo = AddAccountPage.run(0);
						}
						else {
							accNo = AddAccountPage.run(currEmployee.getBranchId());
						}
						logger.fine("Account created successfully!\nAccount number : " + accNo);
						break;
					}
					case 4: {
						logger.info("Admin edit User");
						break;
					}
					case 5: {
						logger.info("Admin view Employee list");
						break;
					}
					case 6: {
						logger.info("Admin view Customer list");
						break;
					}
					case 7: {
						logger.info("View Transaction details");
						break;
					}
					case 8: {
						logger.info("View Profile");
						break;
					}
					case 9: {
						logger.info("Edit Profile");
						break;
					}
					case 10: {
						logger.info("Logout");
						continueProgram = false;
						break;
					}
					default: {
						logger.severe("Give valid choice!");
						break;
					}
					}
				} catch (CustomBankException e) {
					logger.warning(e.getMessage());
				}

			}
	}
}
