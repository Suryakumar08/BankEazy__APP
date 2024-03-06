package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.TransactionHelper;
import helpers.UserHelper;
import utilities.InputHelper;

public class WithdrawAmountPage {
	private static Logger logger = BankEazyApp.logger;
	public static void run(long accountNo, int customerId) throws CustomBankException{
		UserHelper helper = new UserHelper();
		try {
			double amount;
			while(true) {
				logger.info("Enter Amount to Withdraw : ");
				amount = InputHelper.getDouble();
				if(amount > 0) {
					break;
				}
			}
			

			logger.info("Enter your password : ");
			String password = InputHelper.getString();

			if (helper.checkPassword(password, helper.getUser(customerId))) {
				TransactionHelper transactionHelper = new TransactionHelper();
				long refNo = transactionHelper.withdrawAmount(accountNo, amount);
				if(refNo != -1) {
					logger.fine("Amount withdrawn successfull!!! You can collect your cash.!");
				}
				else {
					logger.warning("Insufficient Balance!!!");
				}
			}
			else {
				logger.warning("Password Incorrect!!!");
			}
		} catch (CustomBankException e) {
			logger.warning(e.getMessage());
		}

	}
}
