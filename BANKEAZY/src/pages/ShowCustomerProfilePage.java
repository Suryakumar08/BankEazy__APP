package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.UserHelper;
import model.Customer;
import utilities.Utilities;

public class ShowCustomerProfilePage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int customerId) throws CustomBankException {
		UserHelper helper = new UserHelper();
		printDetails(helper.getCustomer(customerId));
	}

	public static void printDetails(Customer customer) {
		logger.info("Id : " + customer.getId() + "\nName : " + customer.getName() + "\nMobile : " + customer.getMobile()
				+ "\nGender : " + customer.getGender() + "\nDoB : " + Utilities.getDateString(customer.getDob())
				+ "\nStatus : " + customer.getStatus("") + "\nPan : " + customer.getPan() + "\nAadhar : " + customer.getAadhar());
	}
}
