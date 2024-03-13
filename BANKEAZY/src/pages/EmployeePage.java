package pages;

import java.util.Map;
import java.util.logging.Logger;

import enums.UserType;
import exception.CustomBankException;
import helpers.BranchHelper;
import helpers.CustomerHelper;
import helpers.EmployeeHelper;
import helpers.TransactionHelper;
import model.Branch;
import model.Customer;
import model.Employee;
import model.Transaction;
import utilities.InputHelper;
import utilities.Utilities;

public class EmployeePage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int userId) throws CustomBankException {
		EmployeeHelper helper = new EmployeeHelper();
		CustomerHelper customerHelper = new CustomerHelper();
		TransactionHelper transactionHelper = new TransactionHelper();
		BranchHelper branchHelper = new BranchHelper();
		
		Employee currEmployee = helper.getEmployee(userId);
		int empRole = currEmployee.getType();
		boolean continueProgram = true;
		while (continueProgram) {
			logger.info(
					"1) Add Employee\n2) Add Customer\n3) Add Customer Account\n4) Edit User\n5) View Employee List\n6) View Employee\n7) View Customer List\n8) View Customer\n9) View Transaction Details of customer\n10) View Transaction details of a Account\n11) View Profile\n12) Edit Profile\n13) Add Branch\n14) Edit branch details\n15) Show all branches\n16) Logout\n\nEnter your choice : \n");
			try {
				int employeeChoice = InputHelper.getInt();
				switch (employeeChoice) {
				case 1: {
					if (empRole == UserType.Admin.getType()) {
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
					if (empRole == UserType.Admin.getType()) {
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
					if (empRole == UserType.Admin.getType()) {
						Map<Integer, Employee> employees = helper.getEmployees(new Employee(), 50, 0);
						Utilities.printObjects(employees);
					} else {
						logger.fine("Only Admins have the permission!");
					}
					break;
				}
				case 6:{
					if(empRole == UserType.Admin.getType()) {
						logger.info("Enter Employee Id : ");
						int employeeId = InputHelper.getInt();
						Employee employee = helper.getEmployee(employeeId);
						logger.fine(employee.toString());
					}
					else {
						logger.warning("Only admins have the permission!");
					}
					break;
				}
				case 7: {
					Customer customer = new Customer();
					Map<Integer, Customer> customers = customerHelper.getCustomers(customer, 50, 0);
					Utilities.printObjects(customers);
					break;
				}
				case 8:{
					logger.info("Enter Customer Id : ");
					int customerId = InputHelper.getInt();
					Customer customer = customerHelper.getCustomer(customerId);
					logger.fine(customer != null ? customer.toString() : "No Customer Exists");
					break;
				}
				case 9: {
					logger.info("Enter customer Id : ");
					int customerId = InputHelper.getInt();
					long currOffset = 0;
					boolean continuePrintTransaction = true;
					do{
						Map<Long, Transaction> transactions = transactionHelper.getCustomerTransactions(customerId, (Utilities.getCurrentTime() - (30l * 3600000l * 24)), Utilities.getCurrentTime(), 50, currOffset);
						if(transactions.size() == 0) {
							logger.warning("No more Transactions!!!");
							continuePrintTransaction = false;
							continue;
						}
						Utilities.printObjects(transactions);
						logger.info("1. Next\n2. Back");
						int choice = InputHelper.getInt();
						if(choice == 1) {
							currOffset += 50;
						}
						else {
							currOffset -= 50;
						}
					}while(continuePrintTransaction);
					break;
				}
				case 10:{
					logger.info("Enter Account Number : ");
					long accountNo = InputHelper.getLong();
					long currOffset = 0;
					boolean continuePrintTransaction = true;
					do{
						Map<Long, Transaction> transactions = transactionHelper.getAccountTransactions(accountNo, (Utilities.getCurrentTime() - (30l * 3600000l * 24l)), Utilities.getCurrentTime(), 50, currOffset);
						if(transactions.size() == 0) {
							logger.warning("No more Transactions!!!");
							continuePrintTransaction = false;
							continue;
						}
						Utilities.printObjects(transactions);
						logger.info("1. Next\n2. Back");
						int choice = InputHelper.getInt();
						if(choice == 1) {
							currOffset += 50;
						}
						else {
							currOffset -= 50;
						}
					}while(continuePrintTransaction);
					break;
				}
				case 11: {
					Employee employee = helper.getEmployee(userId);
					logger.fine(employee.toString());
					break;
				}
				case 12: {
					logger.info("Edit Profile");
					break;
				}
				case 13:{
					if(empRole == UserType.Admin.getType()) {
						AddBranchPage.run();
					}
					else {
						logger.warning("Only Admins have this permission!");
					}
					break;
				}
				case 14:{
					if(empRole == UserType.Admin.getType()) {
						EditBranchPage.run();
					}
					else {
						logger.warning("Only Admins have this permission!");
					}
					break;
				}
				case 15:{
					Map<Integer, Branch> branches = branchHelper.getAllBranches();
					Utilities.printObjects(branches);
					break;
				}
				case 16: {
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

}
