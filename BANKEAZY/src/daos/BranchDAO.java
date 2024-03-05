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
import model.Branch;

public class BranchDAO implements BranchDaoInterface{
	
	private Connection connection = null;
	private String selectBranchQuery = "select id, name, address, ifsc from Branch";
	private String addBranchQuery = "insert into Branch(name, address, ifsc) values (?, ?, ?)";
	
	public BranchDAO() throws CustomBankException {
		connection = JDBCConnector.getConnection();
	}

	@Override
	public int addBranch(Branch branch) throws CustomBankException {
		try(PreparedStatement query = connection.prepareStatement(addBranchQuery, Statement.RETURN_GENERATED_KEYS)){
			query.setObject(1, branch.getName());
			query.setObject(2, branch.getAddress());
			query.setObject(3, branch.getIfsc());
			
			int noOfRowsAffected = query.executeUpdate();
			
			int lastAddedBranchId = -1;
			
			try(ResultSet result = query.getGeneratedKeys()){
				if(result.next()) {
					lastAddedBranchId = result.getInt(1);
				}
			}
			if(noOfRowsAffected < 0) {
				throw new CustomBankException(CustomBankException.ERROR_OCCURRED + " Branch not added!");
			}
			
			return lastAddedBranchId;
			
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	@Override
	public Map<Integer, Branch> getBranches() throws CustomBankException {
		Map<Integer, Branch> branchMap;
		try(Statement statement = connection.createStatement()){
			try(ResultSet branches = statement.executeQuery(selectBranchQuery)){
				branchMap = new HashMap<>();
				DAOHelper daoHelper = new DAOHelper();
				Map<String, Method> settersMap = daoHelper.getSettersMap(Branch.class);
				while(branches.next()) {
					Branch currBranch = daoHelper.mapResultSetToGivenClassObject(branches, Branch.class, settersMap);
					branchMap.put(currBranch.getId(), currBranch);
				}
				return branchMap;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	@Override
	public boolean updateBranch(Branch branch) throws CustomBankException {
		DAOHelper helper = new DAOHelper();
		StringBuilder updateBranchQuery = new StringBuilder(helper.generateUpdateQuery(branch));
		updateBranchQuery.append(" where id = ?");
		try(PreparedStatement statement = connection.prepareStatement(updateBranchQuery.toString())){
			int noOfParametersAdded = helper.setFields(statement, branch);
			statement.setObject(noOfParametersAdded, branch.getId());
			int noOfRowsAffected = statement.executeUpdate();
			return noOfRowsAffected > 0;
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	@Override
	public Branch getBranch(int branchId) throws CustomBankException {
		Branch currBranch = null;
		StringBuilder query = new StringBuilder(selectBranchQuery);
		query.append(" where id = ?");
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			statement.setObject(1, branchId);
			try(ResultSet branch = statement.executeQuery()){
				DAOHelper daoHelper = new DAOHelper();
				Map<String, Method> settersMap = daoHelper.getSettersMap(Branch.class);
				if(branch.next()) {
					currBranch = daoHelper.mapResultSetToGivenClassObject(branch, Branch.class, settersMap);
				}
				return currBranch;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}
	
	
}
