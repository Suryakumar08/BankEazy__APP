package pages;

import java.util.Map;
import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.CustomerHelper;
import model.Account;
import utilities.InputHelper;

public class CustomerPage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int userId) {
		CustomerHelper helper = new CustomerHelper();
		boolean continueProgram = true;		
		while (continueProgram) {
			try {
				logger.fine("------------Hello Customer!!!-------------");
				
				Map<Integer, Account> accounts = helper.getAccounts(userId);
				logger.info("Select Account : ");
				printAccounts(accounts);
				
				int selectedAccount = InputHelper.getInt();
				
				Account account = accounts.get(selectedAccount);
				
				if(account == null) {
					logger.warning("Select Existing Account !!!");
				}
				else {
					long currAccountNo = account.getAccountNo();
					logger.info(
							"1) Deposit Amount\n2) Withdraw Amount\n3) Transfer Amount\n4) View Transaction History\n5) View Profile\n6) Edit Profile\n7) Logout");
					logger.info("Enter your choice : ");
					int customerChoice = InputHelper.getInt();
					switch (customerChoice) {
					case 1: {
						DepositAmountPage.run(currAccountNo, userId);
						break;
					}
					case 2: {
						break;
					}
					case 3: {
						break;
					}
					case 4: {
						break;
					}
					case 5: {
						break;
					}
					case 6: {
						break;
					}
					case 7: {
						logger.fine("Thank you!");
						continueProgram = false;
						break;
					}
					default: {
						logger.severe("Give valid choice!");
						break;
					}
					}
				}
				
			} catch (CustomBankException e) {
				logger.warning(e.getMessage());
			}
		}
	}

	private static void printAccounts(Map<Integer, Account> accounts) {
		logger.fine("SI.NO                   Account Details");
		for(Map.Entry<Integer, Account> element : accounts.entrySet()) {
			logger.info(element.getKey() + "                          " + element.getValue());
		}
	}
}
