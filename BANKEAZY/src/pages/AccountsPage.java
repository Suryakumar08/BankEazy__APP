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
		while (continueProgram) {
			try {
				Map<Long, Account> accounts = helper.getAccounts(userId);
				Account selectedAccount = null;
				long selectedAccountNo = -1;
				if(accounts.size() == 1) {
					selectedAccountNo = accounts.keySet().iterator().next();
					selectedAccount = accounts.get(selectedAccountNo);
				}
				else {
					logger.info("Select Account  by entering account number: ");
					printAccounts(accounts);
					selectedAccountNo = InputHelper.getLong();
					selectedAccount = accounts.get(selectedAccountNo);
				}

				if (selectedAccount == null) {
					logger.warning("Select Existing Account !!!");
				} else if (selectedAccount.getStatus() == 0) {
					logger.warning("Selected account is InActive at the moment!");
				} else {
					boolean continueInSelectedAccount = true;
					while(continueInSelectedAccount) {
					logger.info(
							"1) Deposit Amount\n2) Withdraw Amount\n3) Transfer Amount\n4) View Transaction History\n5) View Account Details\n6) Select Another Account\n7) Exit");
					logger.info("Enter your choice : ");
					int customerChoice = InputHelper.getInt();
					switch (customerChoice) {
					case 1: {
						DepositAmountPage.run(selectedAccountNo, userId);
						break;
					}
					case 2: {
						WithdrawAmountPage.run(selectedAccountNo, userId);
						break;
					}
					case 3: {
						TransferAmountPage.run(selectedAccountNo, userId);
						break;
					}
					case 4: {
						ViewCustomerTransactionPage.run(selectedAccountNo);
						break;
					}
					case 5: {
						logger.fine(selectedAccount.toString());
						break;
					}
					case 6: {
						continueInSelectedAccount = false;
						break;
					}
					case 7:{
						logger.fine("Thank you!");
						continueInSelectedAccount = false;
						continueProgram = false;
					}
					default: {
						logger.severe("Give valid choice!");
						break;
					}
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
