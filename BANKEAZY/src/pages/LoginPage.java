package pages;

import java.util.InputMismatchException;
import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.LoginHelper;
import utilities.InputHelper;
import utilities.Validators;

public class LoginPage {
	private static Logger logger = BankEazyApp.logger;
	
	public static void run() throws CustomBankException {
		try{
			
			logger.info("Enter User Id : ");
			int userId = InputHelper.getInt();
			logger.info("Enter password : ");
			String password = InputHelper.getString();
			Validators.validateInput(password);
			
			LoginHelper helper = new LoginHelper();
			int result = helper.checkUser(userId, password);
			if(result == -1) {
				logger.warning("User not exists! Provide correct details!!\n" + "-".repeat(100));
			}
			else{
				if(result == 0) {
					logger.warning("Incorrect Password! Enter correct password!!!\n" + "-".repeat(100));
				}
				else {
					if(result == 1) {
						logger.warning("User Inactive!");
					}
					else {
						if(result == 2) {
							EmployeePage.run(userId);
						}
						else if(result == 3){
							CustomerPage.run(userId);
						}
					}
				}
			}
			
		}
		catch(InputMismatchException e) {
			throw new CustomBankException(CustomBankException.GIVE_PROPER_INPUT, e);
		}
	}
	
	
}
