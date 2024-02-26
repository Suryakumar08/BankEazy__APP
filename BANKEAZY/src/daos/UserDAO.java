package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import model.User;

public class UserDAO implements UserDaoInterface{
	private Connection connection = null;
	
	StringBuilder selectAllQuery = new StringBuilder("select * from User where ");
	
	public UserDAO() throws CustomBankException {
		connection = JDBCConnector.getConnection();
	}
	public User getUser(int userId) throws CustomBankException {
				
		StringBuilder query = new StringBuilder(selectAllQuery);
		query.append("id = ?");
		
		try(PreparedStatement statement = connection.prepareStatement(query.toString())){
			statement.setInt(1, userId);
			try(ResultSet result = statement.executeQuery()){
				User user = null;
				if(result.next()) {
					user = new User();
					user.setUserId(result.getInt("id"));
					user.setName(result.getString("name"));
					user.setPassword(result.getString("password"));
					user.setMobile(result.getString("mobile"));
					user.setGender(result.getString("gender"));
					user.setDob(result.getLong("dob"));
					user.setStatus(result.getInt("status"));
					user.setType(result.getInt("type"));
				}
				return user;
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
//			e.printStackTrace();
		}
	}

}
