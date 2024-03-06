package pages;

import java.util.Map;

import exception.CustomBankException;
import helpers.TransactionHelper;
import model.Transaction;
import utilities.Utilities;

public class ViewCustomerTransactionPage {
	public static void run(long accountNo) throws CustomBankException{
		TransactionHelper helper = new TransactionHelper();
		Map<Long, Transaction> transactions = helper.getAccountTransactions(accountNo, (Utilities.getCurrentTime() - (30l * 24l * 3600000l)), Utilities.getCurrentTime(), 50, 0);
		Utilities.printObjects(transactions);
	}
}
