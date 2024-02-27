package helpers;

import java.util.Map;

import daos.AccountDAO;
import daos.AccountDaoInterface;
import exception.CustomBankException;
import model.Account;

public class CustomerHelper {

	public Map<Integer, Account> getAccounts(int userId) throws CustomBankException{
		AccountDaoInterface accountDao = new AccountDAO();
		return accountDao.getAccounts(userId);
	}
	
}
