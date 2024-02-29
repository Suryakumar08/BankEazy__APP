package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.EmployeeHelper;
import utilities.InputHelper;
import utilities.Validators;

public class UpdateCustomerProfilePage {
	
	private static Logger logger = BankEazyApp.logger;
	public static void run(int customerId) throws CustomBankException{
		EmployeeHelper helper = new EmployeeHelper();
		try {
			logger.info("Edit : 1 -> Name\n2 -> Password\n3 -> Mobile\n");
			int choice = InputHelper.getInt();
			
			switch(choice) {
			case 1:{
				logger.info("Enter new name : ");
				String newName = InputHelper.getString();
				Validators.validateInput(newName);
				
				helper.updateCustomerName(customerId, newName);
			}
			}
		}catch(CustomBankException e) {
			logger.warning(e.getMessage());
		}
	}
}
