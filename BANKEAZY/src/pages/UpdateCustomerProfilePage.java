package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.CustomerHelper;
import model.Customer;
import utilities.InputHelper;
import utilities.Validators;

public class UpdateCustomerProfilePage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int customerId) throws CustomBankException {
		CustomerHelper helper = new CustomerHelper();
		try {
			boolean continueProgram = true;
			Customer editCustomer = new Customer();
			int noOfChanges = 0;
			while (continueProgram) {
				logger.info("1 -> Name\n2 -> Password\n3 -> Mobile\n4 -> EDIT");
				int choice = InputHelper.getInt();
				switch (choice) {
				case 1: {
					logger.info("Enter new name : ");
					String newName = InputHelper.getString();
					Validators.validateInput(newName);
					editCustomer.setName(newName);
					noOfChanges++;
					break;
				}
				case 2: {
					logger.info("Enter new password : ");
					String newPassword = InputHelper.getString();
					Validators.validatePassword(newPassword);
					editCustomer.setPassword(newPassword);
					noOfChanges++;
					break;
				}
				case 3: {
					logger.info("Enter new mobile : ");
					String newMobile = InputHelper.getString();
					Validators.validateMobile(newMobile);
					editCustomer.setMobile(newMobile);
					noOfChanges++;
					break;
				}
				case 4:{
					continueProgram = false;
					break;
				}
				}
			}
			if(noOfChanges < 1) {
				logger.fine("No Change required!");
			}
			else {
				boolean isEdited = helper.updateCustomer(editCustomer, customerId);
				logger.fine(isEdited ? "Edit Successful!" : "Edit Failed!!!");
			}
		} catch (CustomBankException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}
}
