package daos;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import model.Transaction;

public class TransactionDAO implements TransactionDaoInterface {

	private Connection connection = null;
	private static final String addTransactionQuery = "insert into Transaction(customerId, accountNo, transactionAccountNo, description, type, status, time, closingBalance, transactionId, amount) values(?,?,?,?,?,?,?,?,?,?)";
	private static final String lastTransactionIdQuery = "select MAX(transactionId) as maxId from Transaction";
	private static final String getTransactionQuery = "select customerId, accountNo, transactionAccountNo, description, type, status, time, closingBalance, transactionId, amount from Transaction";
	
	public TransactionDAO() throws CustomBankException {
		connection = JDBCConnector.getConnection();
	}

	@Override
	public boolean addTransaction(Transaction transaction) throws CustomBankException {
		try(PreparedStatement statement = connection.prepareStatement(addTransactionQuery)){
			statement.setObject(1, transaction.getCustomerId());
			statement.setObject(2, transaction.getAccountNo());
			statement.setObject(3, transaction.getTransactionAccountNo());
			statement.setObject(4, transaction.getDescription());
			statement.setObject(5, transaction.getType());
			statement.setObject(6, transaction.getStatus());
			statement.setObject(7, transaction.getTime());
			statement.setObject(8, transaction.getClosingBalance());
			statement.setObject(9, transaction.getTransactionId());
			statement.setObject(10, transaction.getAmount());
			
			int noOfRowsAffected = statement.executeUpdate();
			if(noOfRowsAffected > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	@Override
	public long getLastTransactionId() throws CustomBankException {
		try (Statement statement = connection.createStatement()) {
			try (ResultSet maxIdSet = statement.executeQuery(lastTransactionIdQuery)) {
				if(maxIdSet.next()) {
					return maxIdSet.getLong("maxId");
				}
				else{
					return 0;
				}
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	@Override
	public List<Transaction> getTransactions(long accountNo, long from, long to) throws CustomBankException {
		StringBuilder query = new StringBuilder(getTransactionQuery);
		query.append(" where accountNo = ? and time between ? and ?");
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			statement.setLong(1, accountNo);
			statement.setLong(2, from);
			statement.setLong(3, to);
			List<Transaction> transactionsList = new ArrayList<>(); 
			try(ResultSet transactions = statement.executeQuery()){
				DAOHelper daoHelper = new DAOHelper();
				Map<String, Method> settersMap = daoHelper.getSettersMap(Transaction.class);
				while(transactions.next()) {
					Transaction currTransaction = daoHelper.mapResultSetToGivenClassObject(transactions, Transaction.class, settersMap);
					transactionsList.add(currTransaction);
				}
				return transactionsList;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}
	
	@Override
	public List<Transaction> getTransactions(long from, long to) throws CustomBankException {
		StringBuilder query = new StringBuilder(getTransactionQuery);
		query.append(" where time between ? and ?");
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			statement.setLong(1, from);
			statement.setLong(2, to);
			List<Transaction> transactionsList = new ArrayList<>(); 
			try(ResultSet transactions = statement.executeQuery()){
				DAOHelper daoHelper = new DAOHelper();
				Map<String, Method> settersMap = daoHelper.getSettersMap(Transaction.class);
				while(transactions.next()) {
					Transaction currTransaction = daoHelper.mapResultSetToGivenClassObject(transactions, Transaction.class, settersMap);
					transactionsList.add(currTransaction);
				}
				return transactionsList;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

}
