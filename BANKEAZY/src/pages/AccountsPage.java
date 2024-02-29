package pages;

import java.util.Map;
import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.CustomerHelper;
import model.Account;
import utilities.InputHelper;

public class AccountsPage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int userId) throws CustomBankException {
		CustomerHelper helper = new CustomerHelper();
		boolean continueProgram = true;
		Map<Integer, Account> accounts = helper.getAccounts(userId);
		while (continueProgram) {
			try {
				logger.info("Select Account : ");
				printAccounts(accounts);
				int selectedAccount = InputHelper.getInt();

				Account account = accounts.get(selectedAccount);

				if (account == null) {
					logger.warning("Select Existing Account !!!");
				} else if (account.getStatus() == 0) {
					logger.warning("Selected account is InActive at the moment!");
				} else {
					long currAccountNo = account.getAccountNo();
					logger.info(
							"1) Deposit Amount\n2) Withdraw Amount\n3) Transfer Amount\n4) View Transaction History\n5) View Account Details\n6) Exit");
					logger.info("Enter your choice : ");
					int customerChoice = InputHelper.getInt();
					switch (customerChoice) {
					case 1: {
						DepositAmountPage.run(currAccountNo, userId);
						break;
					}
					case 2: {
						WithdrawAmountPage.run(currAccountNo, userId);
						break;
					}
					case 3: {
						TransferAmountPage.run(currAccountNo, userId);
						break;
					}
					case 4: {
						break;
					}
					case 5: {
						break;
					}
					case 6: {
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
		for (Map.Entry<Integer, Account> element : accounts.entrySet()) {
			logger.fine(element.getKey() + "                    " + element.getValue());
		}
	}
}