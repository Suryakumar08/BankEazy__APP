package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.CustomerHelper;
import utilities.InputHelper;

public class DepositAmountPage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(long accountNo, int userId) throws CustomBankException{
		CustomerHelper helper = new CustomerHelper();
		try {
			double amount = -1;
			while(true) {
				logger.info("Enter Amount to deposit : ");
				amount = InputHelper.getDouble();
				if(amount > 0) {
					break;
				}
			}

			logger.info("Enter your password : ");
			String password = InputHelper.getString();

			if (helper.checkPassword(userId, password)) {
				CustomerHelper customerHelper = new CustomerHelper();
				boolean isDeposited = customerHelper.depositAmount(accountNo, amount);
				if(isDeposited) {
					logger.fine("Amount deposited successfully!!!");
				}
				else {
					logger.warning("Deposit failed.. Please try after sometime...");
				}
			}
			else {
				logger.warning("Password Incorrect!!!");
			}
		} catch (CustomBankException e) {
			e.printStackTrace();
		}

	}
}
