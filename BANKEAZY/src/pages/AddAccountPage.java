package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.EmployeeHelper;
import model.Account;
import utilities.InputHelper;

public class AddAccountPage {

	private static Logger logger = BankEazyApp.logger;

	public static long run(int branchId) throws CustomBankException{
		EmployeeHelper helper = new EmployeeHelper();
		while (true) {
			try {
				logger.fine("Create Account for customer : ");
				logger.info("Enter Customer Id : ");
				int customerId = InputHelper.getInt();
				
				logger.info("Enter Initial balance : ");
				double balance = InputHelper.getDouble();
				
				if(branchId == 0) {
					logger.info("Enter branch Id : ");
					branchId = InputHelper.getInt();
				}
				
				Account account = new Account();
				account.setCustomerId(customerId);
				account.setBalance(balance);
				account.setBranchId(branchId);
				account.setStatus(1);
				
				return helper.addAccount(account);
				
			}catch(CustomBankException e) {
				logger.warning(e.getMessage());
			}
		}
	}
}
