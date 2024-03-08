package daos;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import model.User;

public class UserDAO implements UserDaoInterface {

	private String dbName = "BankEazy";
	StringBuilder selectAllQuery = new StringBuilder("select * from User");

	// read
	public User getUser(int userId) throws CustomBankException {

		StringBuilder query = new StringBuilder(selectAllQuery);
		query.append(" where id = ?");
		try (Connection connection = JDBCConnector.getConnection(dbName)) {
			try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
				statement.setInt(1, userId);
				try (ResultSet result = statement.executeQuery()) {
					User user = null;
					DAOHelper daoHelper = new DAOHelper();
					Map<String, Method> settersMap = daoHelper.getSettersMap(User.class);
					if (result.next()) {
						user = daoHelper.mapResultSetToGivenClassObject(result, User.class, settersMap);
					}
					return user;
				}
			}
		} catch (SQLException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

}
