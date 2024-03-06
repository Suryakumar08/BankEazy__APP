package helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import daos.TransactionDaoInterface;
import exception.CustomBankException;
import model.Account;
import model.Transaction;
import utilities.Utilities;
import utilities.Validators;

public class TransactionHelper {

	private TransactionDaoInterface transactionDao = null;
	private AccountHelper accHelper = null;
	
	public TransactionHelper() throws CustomBankException {
		
		accHelper = new AccountHelper();
		
		Class<?> TransactionDAO;
		Constructor<?> transDao;

		try {
			TransactionDAO = Class.forName("daos.TransactionDAO");
			transDao = TransactionDAO.getDeclaredConstructor();
			transactionDao = (TransactionDaoInterface) transDao.newInstance();
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	
	//create

	public long depositAmount(long accountNo, double amount) throws CustomBankException {
		Account account = accHelper.getAccount(accountNo);
		if (account == null) {
			throw new CustomBankException(CustomBankException.ACCOUNT_NOT_EXISTS);
		}
		amount = Math.abs(amount);
		Transaction currTransaction = new Transaction();
		currTransaction.setAccountNo(accountNo);
		currTransaction.setCustomerId(account.getCustomerId());
		currTransaction.setTransactionAccountNo(-1);
		currTransaction.setDescription("");
		currTransaction.setAmount(amount);
		currTransaction.setTypeFromString("Deposit");
		return makeIntraBankTransaction(currTransaction);
	}

	public long withdrawAmount(long accountNo, double amount) throws CustomBankException {
		Account account = accHelper.getAccount(accountNo);
		if (account == null) {
			throw new CustomBankException(CustomBankException.ACCOUNT_NOT_EXISTS);
		}
		amount = Math.abs(amount);
		Transaction currTransaction = new Transaction();
		currTransaction.setAccountNo(accountNo);
		currTransaction.setCustomerId(account.getCustomerId());
		currTransaction.setTransactionAccountNo(-1);
		currTransaction.setDescription("");
		currTransaction.setAmount(amount);
		currTransaction.setTypeFromString("Withdraw");
		return makeIntraBankTransaction(currTransaction);
	}

	public long makeIntraBankTransaction(Transaction currTransaction) throws CustomBankException {
		Validators.checkNull(currTransaction);
		currTransaction.setAmount(Math.abs(currTransaction.getAmount()));

		double currAmount = currTransaction.getAmount();
		long currAccountNo = currTransaction.getAccountNo();
		long transactionAccountNo = currTransaction.getTransactionAccountNo();
		isValidTransaction(currAccountNo, transactionAccountNo);
		Account currAccount = accHelper.getAccount(currAccountNo);
		accHelper.isActiveBankAccount(currAccount);
		double currBalance = currAccount.getBalance();
		currTransaction.setTime(Utilities.getCurrentTime());

		long lastTransId = -1;
		long transactionReferenceNo = -1;
		if (currTransaction.getTransactionId() == 0) {
			lastTransId = getLastTransactionId();
			currTransaction.setTransactionId(lastTransId + 1);
		}

		if (currTransaction.getTypeAsString().equalsIgnoreCase("withdraw")
				|| currTransaction.getTypeAsString().equalsIgnoreCase("debit")) {
			double closingBalance = currBalance - currAmount;
			currTransaction.setAmount(0 - currAmount);
			if (closingBalance < 0) {
				currTransaction.setStatus(0);
				currTransaction.setClosingBalance(currBalance);
			} else {
				currTransaction.setStatus(1);
				currTransaction.setClosingBalance(closingBalance);
			}
			transactionReferenceNo = transactionDao.addTransaction(currTransaction);

			if (transactionReferenceNo == -1) {
				throw new CustomBankException(
						CustomBankException.ERROR_OCCURRED + " " + CustomBankException.TRANSACTION_FAILED);
			}

			if (currTransaction.getStatus() == 1) {
				accHelper.updateAmount(currTransaction.getAccountNo(), closingBalance);
			}

			if (closingBalance < 0) {
				throw new CustomBankException(CustomBankException.NOT_ENOUGH_BALANCE);
			}

			if (currTransaction.getTypeAsString().equalsIgnoreCase("debit")) {
				Transaction recipientTransaction = new Transaction();
				Account recipientAccount = accHelper.getAccount(currTransaction.getTransactionAccountNo());

				recipientTransaction.setAccountNo(currTransaction.getTransactionAccountNo());
				recipientTransaction.setCustomerId(recipientAccount.getCustomerId());
				recipientTransaction.setTransactionAccountNo(currAccountNo);
				recipientTransaction.setDescription(currTransaction.getDescription());
				recipientTransaction.setTransactionId(lastTransId);
				recipientTransaction.setAmount(currAmount);
				recipientTransaction.setTypeFromString("Credit");
				recipientTransaction.setTime(Utilities.getCurrentTime());
				recipientTransaction.setStatus(1);

				long secondTransactionReferenceNo = makeIntraBankTransaction(recipientTransaction);
				if (secondTransactionReferenceNo == -1) {
					throw new CustomBankException(
							CustomBankException.ERROR_OCCURRED + " " + CustomBankException.TRANSACTION_FAILED);
				}
			}
		} else if (currTransaction.getTypeAsString().equalsIgnoreCase("deposit")
				|| currTransaction.getTypeAsString().equalsIgnoreCase("Credit")) {
			double closingBalance = currBalance + currTransaction.getAmount();
			currTransaction.setStatus(1);
			currTransaction.setClosingBalance(closingBalance);
			transactionReferenceNo = transactionDao.addTransaction(currTransaction);

			if (transactionReferenceNo == -1) {
				throw new CustomBankException(
						CustomBankException.ERROR_OCCURRED + " " + CustomBankException.TRANSACTION_FAILED);
			}

			if (currTransaction.getStatus() == 1) {
				accHelper.updateAmount(currTransaction.getAccountNo(), closingBalance);
			}
		} else {
			throw new CustomBankException(CustomBankException.INVALID_TRANSACTION);
		}
		return transactionReferenceNo;

	}

	public long makeInterBankTransaction(Transaction currTransaction) throws CustomBankException {
		Validators.checkNull(currTransaction);
		currTransaction.setAmount(Math.abs(currTransaction.getAmount()));

		double currAmount = currTransaction.getAmount();
		long currAccountNo = currTransaction.getAccountNo();
		long transactionAccountNo = currTransaction.getTransactionAccountNo();
		isValidTransaction(currAccountNo, transactionAccountNo);
		Account currAccount = accHelper.getAccount(currAccountNo);
		accHelper.isActiveBankAccount(currAccount);
		double currBalance = currAccount.getBalance();

		currTransaction.setTime(Utilities.getCurrentTime());

		long lastTransId = -1;
		if (currTransaction.getTransactionId() == 0) {
			lastTransId = getLastTransactionId();
			currTransaction.setTransactionId(lastTransId + 1);
		}

		double closingBalance = currBalance - currAmount;
		if (closingBalance < 0) {
			currTransaction.setStatus(0);
			currTransaction.setClosingBalance(currBalance);
		} else {
			currTransaction.setStatus(1);
			currTransaction.setClosingBalance(closingBalance);
		}
		long transactionReferenceNo = transactionDao.addTransaction(currTransaction);
		if (transactionReferenceNo == -1) {
			throw new CustomBankException(
					CustomBankException.ERROR_OCCURRED + " " + CustomBankException.TRANSACTION_FAILED);
		}
		if (currTransaction.getStatus() == 1) {
			accHelper.updateAmount(currTransaction.getAccountNo(), closingBalance);
		}

		if (closingBalance < 0) {
			throw new CustomBankException(CustomBankException.NOT_ENOUGH_BALANCE);
		}

		return transactionReferenceNo;

	}
	

	
	//read
	public Map<Long, Transaction> getAccountTransactions(long accountNo, long from, long to, int limit, long offset)
			throws CustomBankException {
		Transaction transaction = new Transaction();
		transaction.setAccountNo(accountNo);
		if (to > Utilities.getCurrentTime()) {
			to = Utilities.getCurrentTime();
		}
		return getTransactions(transaction, from, to, limit, offset);
	}

	public Map<Long, Transaction> getCustomerTransactions(int customerId, long from, long to, int limit, long offset)
			throws CustomBankException {
		Transaction transaction = new Transaction();
		transaction.setCustomerId(customerId);
		if (to > Utilities.getCurrentTime()) {
			to = Utilities.getCurrentTime();
		}
		return getTransactions(transaction, from, to, limit, offset);
	}

	public Map<Long, Transaction> getTransactions(Transaction transaction, long from, long to, int limit, long offset)
			throws CustomBankException {
		Map<Long, Transaction> transactions = transactionDao.getTransactions(transaction, from, to, limit, offset);
		if (transactions == null) {
			throw new CustomBankException("No Transactions Exist!");
		}
		return transactions;
	}
	
	private long getLastTransactionId() throws CustomBankException {
		return transactionDao.getLastTransactionId();
	}

	
	//checking
	public boolean isValidTransaction(long userAccountNo, long recipientAccountNo) throws CustomBankException {
		if(userAccountNo == recipientAccountNo) {
			throw new CustomBankException(CustomBankException.INVALID_TRANSACTION);
		}
		return true;
	}
	
}
