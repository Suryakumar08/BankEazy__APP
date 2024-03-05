package daos;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import model.Account;

public class AccountDAO implements AccountDaoInterface {
	
	
	private Connection connection = null;
	private String addAccountQuery = 
			"Insert into Account(customerId, balance, branchId, status) values(?, ?, ?, ?)";
	private String selectAccountsQuery = 
			"Select accountNo, customerId, balance, branchId, status from Account where ";

	public AccountDAO() throws CustomBankException {
		connection = JDBCConnector.getConnection();
	}

	@Override
	public long addAccount(Account account) throws CustomBankException {
		long accNo = -1l;
		try (PreparedStatement statement = connection.prepareStatement(addAccountQuery.toString(),
				Statement.RETURN_GENERATED_KEYS)) {
			statement.setObject(1, account.getCustomerId());
			statement.setObject(2, account.getBalance());
			statement.setObject(3, account.getBranchId());
			statement.setObject(4, account.getStatus());
			int noOfRowsAffected = statement.executeUpdate();
			if (noOfRowsAffected == 0) {
				System.out.println("noOfRowsAff is zero");
				throw new CustomBankException(CustomBankException.ERROR_OCCURRED);
			}

			try (ResultSet genKeys = statement.getGeneratedKeys()) {
				if (genKeys.next()) {
					accNo = genKeys.getLong(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
		return accNo;
	}

	@Override
	public Map<Integer, Account> getAccounts(int userId) throws CustomBankException {
		Map<Integer, Account> accountMap = new HashMap<>();
		int noOfAccounts = 0;
		StringBuilder query = new StringBuilder(selectAccountsQuery).append("customerId = ?");
		try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
			statement.setObject(1, userId);
			try (ResultSet accounts = statement.executeQuery()) {
				DAOHelper daoHelper = new DAOHelper();
				Map<String, Method> settersMap = daoHelper.getSettersMap(Account.class);
				while (accounts.next()) {
					Account account = null;
					account = daoHelper.mapResultSetToGivenClassObject(accounts, Account.class, settersMap);
					accountMap.put(++noOfAccounts, account);
				}
			}
			return accountMap;
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}
	
	public StringBuilder getUpdateQuery(Account account) {
		StringBuilder query = new StringBuilder("update Account ");
		if(account.getBalance() != -1) {
			query.append("set balance = ?, ");
		}
		if(account.getBranchId() != -1) {
			query.append("set branchId = ?, ");
		}
		if(account.getStatus() != -1) {
			query.append("set status = ?, ");
		}
		query.delete(query.length() - 2, query.length());
		return query;
	}
	
	@Override
	public boolean updateAccount(Account account) throws CustomBankException {
		StringBuilder query = getUpdateQuery(account);
		query.append(" where accountNo = ?");
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			int index = 0;
			if(account.getBalance() != -1) {
				statement.setDouble(++index, account.getBalance());
			}
			if(account.getBranchId() != -1) {
				statement.setInt(++index, account.getBranchId());
			}
			if(account.getStatus() != -1) {
				statement.setInt(++index, account.getStatus());
			}
			statement.setLong(++index, account.getAccountNo());
			
			int noOfRowsAffected = statement.executeUpdate();
			
			if(noOfRowsAffected == 0) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	@Override
	public Account getAccount(long accNo) throws CustomBankException{
		Account account = null;
		StringBuilder query = new StringBuilder(selectAccountsQuery).append("accountNo = ?");
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			statement.setLong(1, accNo);
			try(ResultSet accountSet = statement.executeQuery()){
				DAOHelper daoHelper = new DAOHelper();
				Map<String, Method> settersMap = daoHelper.getSettersMap(Account.class);
				if(accountSet.next()) {
					account = daoHelper.mapResultSetToGivenClassObject(accountSet, Account.class, settersMap);
				}
			}
			return account;
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}


}
