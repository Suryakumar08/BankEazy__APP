package daos;

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
	StringBuilder addAccountQuery = new StringBuilder(
			"Insert into Account(customerId, balance, branchId, status) values(?, ?, ?, ?)");
	StringBuilder selectAccountsQuery = new StringBuilder(
			"Select accountNo, customerId, balance, branchId, status from Account where ");

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
				while (accounts.next()) {
					long accountNo = accounts.getLong("accountNo");
					int customerId = accounts.getInt("customerId");
					double balance = accounts.getDouble("balance");
					int branchId = accounts.getInt("branchId");
					int status = accounts.getInt("status");

					Account account = new Account();
					account.setAccountNo(accountNo);
					account.setCustomerId(customerId);
					account.setBalance(balance);
					account.setBranchId(branchId);
					account.setStatus(status);

					accountMap.put(++noOfAccounts, account);
				}
			}
			return accountMap;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

}
