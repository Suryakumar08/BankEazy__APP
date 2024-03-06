package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.CustomerHelper;
import model.Customer;
import utilities.InputHelper;
import utilities.Utilities;
import utilities.Validators;

public class AddCustomerPage {
	private static Logger logger = BankEazyApp.logger;

	public static int run() throws CustomBankException{
		int returnAns = -1;
		CustomerHelper helper = new CustomerHelper();
		while (true) {
			try {
				logger.fine("Add a customer : ");
				logger.info("Enter name :");
				String name = InputHelper.getString();
				Validators.validateInput(name);

				logger.info("Enter password :");
				String password = InputHelper.getString();
				Validators.validatePassword(password);

				logger.info("Enter mobile no :");
				String mobile = InputHelper.getString();
				Validators.validateMobile(mobile);

				logger.info("Enter gender :");
				String gender = InputHelper.getString();
				Validators.validateInput(gender);

				logger.info("Enter Date of Birth in format(dd-MM-yyyy) :");
				String dobString = InputHelper.getString();
				Validators.validateInput(dobString);
				long dob = Utilities.getDobInMillis(dobString);

				logger.info("Enter your Pan number : ");
				String pan = InputHelper.getString();
				Validators.validateInput(pan);

				logger.info("Enter your aadhar No : ");
				String aadhar = InputHelper.getString();
				Validators.validateInput(aadhar);

				Customer customer = new Customer();
				customer.setName(name);
				customer.setPassword(password);
				customer.setMobile(mobile);
				customer.setGender(gender);
				customer.setDob(dob);
				customer.setPan(pan);
				customer.setAadhar(aadhar);

				returnAns = helper.addCustomer(customer);
				break;

			} catch (CustomBankException e) {
				logger.warning(e.getMessage());
			}

		}
		return returnAns;
	}
}
