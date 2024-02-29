package daos;

import java.util.Map;

import exception.CustomBankException;
import model.Account;

public interface AccountDaoInterface {

	public long addAccount(Account account) throws CustomBankException;

	public Map<Integer, Account> getAccounts(int userId) throws CustomBankException;

	public Account getAccount(long accNo) throws CustomBankException;

	public boolean updateAccount(Account account) throws CustomBankException;

}
