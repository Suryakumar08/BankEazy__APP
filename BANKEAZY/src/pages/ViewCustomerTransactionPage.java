package pages;

import java.util.List;

import exception.CustomBankException;
import helpers.EmployeeHelper;
import model.Transaction;
import utilities.Utilities;

public class ViewCustomerTransactionPage {
	public static void run(long accountNo) throws CustomBankException{
		EmployeeHelper helper = new EmployeeHelper();
		List<Transaction> transactions = helper.getTransactions(accountNo);
		Utilities.printObjects(transactions);
	}
}
