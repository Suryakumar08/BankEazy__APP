package pages;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.EmployeeHelper;
import model.Customer;
import model.Employee;
import model.Transaction;
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
					"1) Add Employee\n2) Add Customer\n3) Add Customer Account\n4) Edit User\n5) View Employee List\n6) View Employee\n7) View Customer List\n8) View Customer\n9) View Transaction Details\n10) View Profile\n11) Edit Profile\n12) Logout\n\nEnter your choice : \n");
			try {
				int employeeChoice = InputHelper.getInt();
				switch (employeeChoice) {
				case 1: {
					if (empRole == 1) {
						int newUserId = AddEmployeePage.run();
						logger.fine("Employee Added Successfully!\nAdded User's Id : " + newUserId + "\n\n");
					} else {
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
					if (empRole == 1) {
						accNo = AddAccountPage.run(0);
					} else {
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
					if (empRole == 1) {
						Map<Integer, Employee> employees = helper.getEmployees();
						printObjects(employees);
					} else {
						logger.fine("Only Admins have the permission!");
					}
					logger.info("Admin view Employee list");
					break;
				}
				case 6:{
					logger.info("Enter Employee Id : ");
					int employeeId = InputHelper.getInt();
					Employee employee = helper.getEmployee(employeeId);
					logger.fine(employee.toString());
					break;
				}
				case 7: {
					Map<Integer, Customer> customers = helper.getCustomers();
					printObjects(customers);
					break;
				}
				case 8:{
					logger.info("Enter Customer Id : ");
					int customerId = InputHelper.getInt();
					Customer customer = helper.getCustomer(customerId);
					logger.fine(customer.toString());
					break;
				}
				case 9: {
					List<Transaction> transactions = helper.getAllTransactions();
					printTransactions(transactions);
					break;
				}
				case 10: {
					Employee employee = helper.getEmployee(userId);
					logger.fine(employee.toString());
					break;
				}
				case 11: {
					logger.info("Edit Profile");
					break;
				}
				case 12: {
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
				if(e.getCause() != null) {
					logger.warning(e.getCause() + "");
				}
			}

		}
	}

	private static <K, V> void printObjects(Map<K, V> map) {
		for(V value : map.values()) {
			logger.fine(value.toString());
		}
	}

	private static <T> void printTransactions(List<T> objects) {
		for(T obj : objects) {
			logger.fine(obj.toString());
		}
	}
}
