package pages;

import java.util.Map;
import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.AccountHelper;
import model.Account;
import utilities.InputHelper;

public class AccountsPage {

	private static Logger logger = BankEazyApp.logger;

	public static void run(int userId) throws CustomBankException {
		AccountHelper helper = new AccountHelper();
		boolean continueProgram = true;
		Map<Long, Account> accounts = helper.getAccounts(userId);
		while (continueProgram) {
			try {
				logger.info("Select Account  by entering account number: ");
				printAccounts(accounts);
				long selectedAccount = InputHelper.getLong();

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
						ViewCustomerTransactionPage.run(currAccountNo);
						break;
					}
					case 5: {
						logger.fine(account.toString());
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

	private static void printAccounts(Map<Long, Account> accounts) {
		logger.fine("SI.NO                   Account Details");
		for (Map.Entry<Long, Account> element : accounts.entrySet()) {
			logger.fine(element.getKey() + "                    " + element.getValue());
		}
	}
}
