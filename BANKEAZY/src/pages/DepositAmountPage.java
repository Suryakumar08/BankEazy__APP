package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.TransactionHelper;
import helpers.UserHelper;
import utilities.InputHelper;

public class DepositAmountPage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(long accountNo, int userId) throws CustomBankException{
		UserHelper helper = new UserHelper();
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

			if (helper.checkPassword(password, helper.getUser(userId))) {
				TransactionHelper transactionHelper = new TransactionHelper();
				long refNo = transactionHelper.depositAmount(accountNo, amount);
				if(refNo != -1) {
					logger.fine("Amount deposited successfully!!!");
					logger.fine("Transaction reference No : " + refNo);
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
