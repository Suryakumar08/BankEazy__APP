package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.UserHelper;

public class ShowCustomerProfilePage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int customerId) throws CustomBankException {
		UserHelper helper = new UserHelper();
		logger.fine(helper.getCustomer(customerId).toString());
	}
}
