package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.CustomerHelper;

public class ShowCustomerProfilePage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int customerId) throws CustomBankException {
		CustomerHelper helper = new CustomerHelper();
		logger.fine(helper.getCustomer(customerId).toString());
	}
}
