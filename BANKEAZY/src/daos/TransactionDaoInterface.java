package daos;

import java.util.List;

import exception.CustomBankException;
import model.Transaction;

public interface TransactionDaoInterface {
	public boolean addTransaction(Transaction transaction) throws CustomBankException;
	
	public long getLastTransactionId() throws CustomBankException;
	
	public List<Transaction> getTransactions(long accountNo, long from, long to) throws CustomBankException;

	public List<Transaction> getTransactions(long from, long to) throws CustomBankException;
}
