package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.TransactionHelper;
import helpers.UserHelper;
import model.Transaction;
import utilities.InputHelper;
import utilities.Validators;

public class TransferAmountPage {
	private static Logger logger = BankEazyApp.logger;

	public static void run(long accountNo, int customerId) throws CustomBankException {
		boolean continueProgram = true;
		UserHelper userHelper = new UserHelper();
		TransactionHelper transactionHelper = new TransactionHelper();
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
					
					logger.info("Enter password : ");
					String password = InputHelper.getString();
					
					if(!userHelper.checkPassword(password, userHelper.getUser(customerId))) {
						throw new CustomBankException(CustomBankException.INVALID_PASSWORD);
					}

					Transaction currTransaction = new Transaction();
					currTransaction.setAccountNo(accountNo);
					currTransaction.setCustomerId(customerId);
					currTransaction.setTransactionAccountNo(recipientAccNo);
					currTransaction.setDescription(description);
					currTransaction.setAmount(amount);
					currTransaction.setTypeFromString("Debit");

					long referenceNo = transactionHelper.makeIntraBankTransaction(currTransaction);

					if (referenceNo != -1) {
						logger.fine("Transaction Success!!!");
						logger.fine("Transaction Reference No : " + referenceNo);
					} else {
						logger.warning("Transaction Failed!!");
					}
					break;
				}
				case 2: {
					logger.info("Transferring outside the bank!");
					logger.info("Enter recipient account no : ");
					long recipientAccountNo = InputHelper.getLong();

					logger.info("Enter IFSC code : ");
					String ifsc = InputHelper.getString();
					Validators.validateInput(ifsc);

					logger.info("Enter description : ");
					String description = InputHelper.getString();

					logger.info("Enter Amount to Transfer : ");
					double amount = InputHelper.getDouble();
					if (amount <= 0) {
						throw new CustomBankException(CustomBankException.ERROR_OCCURRED + "Please Enter valid amount!!!");
					}
					
					logger.info("Enter password : ");
					String password = InputHelper.getString();
					
					if(!userHelper.checkPassword(password, userHelper.getUser(customerId))) {
						throw new CustomBankException(CustomBankException.INVALID_PASSWORD);
					}
					
					Transaction currTransaction = new Transaction();
					currTransaction.setAccountNo(accountNo);
					currTransaction.setCustomerId(customerId);
					currTransaction.setTransactionAccountNo(recipientAccountNo);
					currTransaction.setDescription(description);
					currTransaction.setAmount(amount);
					currTransaction.setTypeFromString("Debit");

					long referenceNo = transactionHelper.makeInterBankTransaction(currTransaction);

					if (referenceNo != -1) {
						logger.info("Transaction Success!!!");
						logger.fine("Transaction Reference No : " + referenceNo);
					} else {
						logger.warning("Transaction Failed!!");
					}
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
