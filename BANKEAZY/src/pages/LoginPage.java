package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.UserHelper;
import utilities.InputHelper;

public class LoginPage {
	private static Logger logger = BankEazyApp.logger;
	
	public static void run() throws CustomBankException {
		boolean continueProgram = true;
		while(continueProgram)
		try{
			logger.info("Enter User Id : ");
			int userId = InputHelper.getInt();
			logger.info("Enter password : ");
			String password = InputHelper.getString();
			
			UserHelper helper = new UserHelper();
			int type = helper.checkUserType(userId, password);
			if(type == 1) {
				EmployeePage.run(userId);
				break;
			}
			else if(type == 0) {
				CustomerPage.run(userId);
				break;
			}
		}
		catch(CustomBankException e) {
			logger.warning(e.getMessage());
		}
	}
	
	
}
