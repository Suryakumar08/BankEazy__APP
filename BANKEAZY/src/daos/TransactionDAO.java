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
import model.Transaction;
import utilities.Validators;

public class TransactionDAO implements TransactionDaoInterface {

	private Connection connection = null;
	private static final String addTransactionQuery = "insert into Transaction(customerId, accountNo, transactionAccountNo, description, type, status, time, closingBalance, transactionId, amount) values(?,?,?,?,?,?,?,?,?,?)";
	private static final String lastTransactionIdQuery = "select MAX(transactionId) as maxId from Transaction";
	private static final String getTransactionQuery = "select customerId, accountNo, transactionAccountNo, description, type, status, time, closingBalance, transactionId, amount, referenceNo from Transaction";
	
	public TransactionDAO() throws CustomBankException {
		connection = JDBCConnector.getConnection();
	}

	//create
	@Override
	public long addTransaction(Transaction transaction) throws CustomBankException {
		Validators.checkNull(transaction);
		try(PreparedStatement statement = connection.prepareStatement(addTransactionQuery, Statement.RETURN_GENERATED_KEYS)){
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
			
			if(noOfRowsAffected < 1) {
				throw new CustomBankException(CustomBankException.TRANSACTION_FAILED);
			}
			long referenceNo = 0;
			try(ResultSet generatedKeys = statement.getGeneratedKeys()){
				if(generatedKeys.next()) {
					referenceNo = generatedKeys.getLong(1);
				}
			}
			return referenceNo;
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	
	//read
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
	public Map<Long, Transaction> getTransactions(Transaction transaction, long from, long to, int limit, long offset) throws CustomBankException {
		Validators.checkNull(transaction);
		DAOHelper daoHelper = new DAOHelper();
		StringBuilder query = new StringBuilder(getTransactionQuery);
		daoHelper.addWhereConditions(query, transaction);
		query.append(" and time between ? and ? limit ? offset ?");
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			int parameterIndexToAdd = daoHelper.setFields(statement, transaction);
			statement.setLong(parameterIndexToAdd++, from);
			statement.setLong(parameterIndexToAdd++, to);
			statement.setObject(parameterIndexToAdd++, limit);
			statement.setObject(parameterIndexToAdd++, offset);
			Map<Long, Transaction> transactionsMap = null; 
			try(ResultSet transactions = statement.executeQuery()){
				Map<String, Method> settersMap = daoHelper.getSettersMap(Transaction.class);
				while(transactions.next()) {
					if(transactionsMap == null) {
						transactionsMap = new HashMap<>();
					}
					Transaction currTransaction = daoHelper.mapResultSetToGivenClassObject(transactions, Transaction.class, settersMap);
					transactionsMap.put(currTransaction.getReferenceNo(), currTransaction);
				}
				return transactionsMap;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

}
