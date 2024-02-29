package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.CustomerHelper;
import model.Transaction;
import utilities.InputHelper;

public class TransferAmountPage {
	private static Logger logger = BankEazyApp.logger;

	public static void run(long accountNo, int customerId) throws CustomBankException {
		boolean continueProgram = true;
		CustomerHelper helper = new CustomerHelper();
		while (continueProgram) {
			try {
				logger.fine("1) Transfer amount within the bank\n2) Transfer amount outside of the bank\n3) Exit");
				logger.fine("Enter type of Transaction : ");
				int customerChoice = InputHelper.getInt();
				switch (customerChoice) {
				case 1: {
					logger.info("Enter Recipient Account No : ");
					long recipientAccNo = InputHelper.getLong();

					logger.info("Enter description : ");
					String description = InputHelper.getString();

					double amount = -1;
					while (true) {
						logger.info("Enter amount : ");
						amount = InputHelper.getDouble();
						if (amount > 0) {
							break;
						}
					}

					Transaction currTransaction = new Transaction();
					currTransaction.setAccountNo(accountNo);
					currTransaction.setCustomerId(customerId);
					currTransaction.setTransactionAccountNo(recipientAccNo);
					currTransaction.setDescription(description);
					currTransaction.setAmount(amount);
					currTransaction.setType("Debit");

					boolean isSuccess = helper.makeTransaction(currTransaction);

					if (isSuccess) {
						logger.info("Transaction Success!!!");
					} else {
						logger.warning("Transaction Failed!!");
					}
					break;
				}
				case 2: {
					logger.info("Transferring outside the bank!");
					break;
				}
				case 3: {
					continueProgram = false;
					break;
				}
				default: {
					logger.warning("Enter valid choice!!!");
					break;
				}
				}
			} catch (CustomBankException e) {
				e.printStackTrace();
				logger.warning(e.getMessage());
			}
		}
	}
}
