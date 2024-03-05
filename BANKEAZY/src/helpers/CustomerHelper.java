package helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import daos.AccountDaoInterface;
import daos.CustomerDaoInterface;
import daos.TransactionDaoInterface;
import exception.CustomBankException;
import model.Account;
import model.Customer;
import model.Transaction;
import utilities.Sha_256;
import utilities.Utilities;

public class CustomerHelper {

	private AccountDaoInterface accountDao;
	private CustomerDaoInterface customerDao;
	private TransactionDaoInterface transactionDao;

	public CustomerHelper() throws CustomBankException {
		Class<?> AccountDAO;
		Constructor<?> accDao;

		Class<?> CustomerDAO;
		Constructor<?> custDao;

		Class<?> TransactionDAO;
		Constructor<?> transDao;

		try {
			AccountDAO = Class.forName("daos.AccountDAO");
			accDao = AccountDAO.getDeclaredConstructor();
			accountDao = (AccountDaoInterface) accDao.newInstance();

			CustomerDAO = Class.forName("daos.CustomerDAO");
			custDao = CustomerDAO.getDeclaredConstructor();
			customerDao = (CustomerDaoInterface) custDao.newInstance();

			TransactionDAO = Class.forName("daos.TransactionDAO");
			transDao = TransactionDAO.getDeclaredConstructor();
			transactionDao = (TransactionDaoInterface) transDao.newInstance();

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	public Map<Integer, Account> getAccounts(int userId) throws CustomBankException {
		return accountDao.getAccounts(userId);
	}

	public Account getAccount(long accountNo) throws CustomBankException {
		return accountDao.getAccount(accountNo);
	}

	public boolean checkPassword(int userId, String password) throws CustomBankException {
		Customer customer = customerDao.getCustomer(userId);
		if (customer == null || !Sha_256.getHashedPassword(password).equals(customer.getPassword())) {
			return false;
		}
		return true;
	}

	public boolean depositAmount(long accountNo, double amount) throws CustomBankException {
		Account account = getAccount(accountNo);
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

	public boolean withdrawAmount(long accountNo, double amount) throws CustomBankException {
		Account account = getAccount(accountNo);
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

	private boolean updateAmount(long accountNo, double amount) throws CustomBankException {
		Account account = new Account();
		account.setAccountNo(accountNo);
		account.setBalance(amount);
		return accountDao.updateAccount(account);
	}

	private long getLastTransactionId() throws CustomBankException {
		return transactionDao.getLastTransactionId();
	}

	public boolean makeIntraBankTransaction(Transaction currTransaction) throws CustomBankException {

		currTransaction.setAmount(Math.abs(currTransaction.getAmount()));

		double currAmount = currTransaction.getAmount();
		long currAccountNo = currTransaction.getAccountNo();
		Account currAccount = getAccount(currAccountNo);
		double currBalance = currAccount.getBalance();

		currTransaction.setTime(Utilities.getCurrentTime());

		long lastTransId = -1;
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
			boolean isTransactionSuccess = transactionDao.addTransaction(currTransaction);
			if (isTransactionSuccess && currTransaction.getStatus() == 1) {
				updateAmount(currTransaction.getAccountNo(), closingBalance);
			}
			if(closingBalance < 0) {
				throw new CustomBankException(CustomBankException.NOT_ENOUGH_BALANCE);
			}

			if (currTransaction.getTypeAsString().equalsIgnoreCase("debit")) {
				Transaction recipientTransaction = new Transaction();
				Account recipientAccount = getAccount(currTransaction.getTransactionAccountNo());

				recipientTransaction.setAccountNo(currTransaction.getTransactionAccountNo());
				recipientTransaction.setCustomerId(recipientAccount.getCustomerId());
				recipientTransaction.setTransactionAccountNo(currAccountNo);
				recipientTransaction.setDescription(currTransaction.getDescription());
				recipientTransaction.setTransactionId(lastTransId);
				recipientTransaction.setAmount(currAmount);
				recipientTransaction.setTypeFromString("Credit");
				recipientTransaction.setTime(Utilities.getCurrentTime());
				recipientTransaction.setStatus(1);

				boolean isSecondTransactionSuccess = makeIntraBankTransaction(recipientTransaction);
				return isTransactionSuccess && isSecondTransactionSuccess;
			}
			return isTransactionSuccess;
		} else if (currTransaction.getTypeAsString().equalsIgnoreCase("deposit")
				|| currTransaction.getTypeAsString().equalsIgnoreCase("Credit")) {
			double closingBalance = currBalance + currTransaction.getAmount();
			currTransaction.setStatus(1);
			currTransaction.setClosingBalance(closingBalance);

			boolean isTransactionSuccess = transactionDao.addTransaction(currTransaction);
			if (isTransactionSuccess && currTransaction.getStatus() == 1) {
				updateAmount(currTransaction.getAccountNo(), closingBalance);
			}
			return isTransactionSuccess;
		}

		return true;
	}

	public boolean makeInterBankTransaction(Transaction currTransaction) throws CustomBankException {
		currTransaction.setAmount(Math.abs(currTransaction.getAmount()));

		double currAmount = currTransaction.getAmount();
		long currAccountNo = currTransaction.getAccountNo();
		Account currAccount = getAccount(currAccountNo);
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
		boolean isTransactionSuccess = transactionDao.addTransaction(currTransaction);
		if (isTransactionSuccess && currTransaction.getStatus() == 1) {
			updateAmount(currTransaction.getAccountNo(), closingBalance);
		}
		
		if(closingBalance < 0) {
			throw new CustomBankException(CustomBankException.NOT_ENOUGH_BALANCE);
		}
		
		return isTransactionSuccess;

	}

}
