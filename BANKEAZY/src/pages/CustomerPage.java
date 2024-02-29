package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import utilities.InputHelper;

public class CustomerPage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int userId) throws CustomBankException{
		logger.fine("------------Hello Customer!!!-------------");
		boolean continueProgram = true;		
		while (continueProgram) {
			try {
				logger.info("1 -> View Profile\n2 -> Change Password\n 3 -> Accounts\n 4 -> logout");
				int customerChoice = InputHelper.getInt();
				switch(customerChoice) {
				case 1:{
					ShowCustomerProfilePage.run(userId);
					break;
				}
				case 2:{
					UpdateCustomerProfilePage.run(userId);
					break;
				}
				case 3:{
					AccountsPage.run(userId);
					break;
				}
				case 4:{
					continueProgram = false;
					break;
				}
				default:{
					logger.warning("Enter valid choice!!!");
					break;
				}
				}
			
				
				
			} catch (CustomBankException e) {
				logger.warning(e.getMessage());
			}
		}
	}
}
