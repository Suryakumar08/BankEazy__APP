package helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import daos.UserDaoInterface;
import exception.CustomBankException;
import model.User;
import utilities.Sha_256;

public class UserHelper {

	private UserDaoInterface userDao;

	public UserHelper() throws CustomBankException {
		Class<?> UserDAO;
		Constructor<?> userDaoConstructor;
		try {
			UserDAO = Class.forName("daos.UserDAO");
			userDaoConstructor = UserDAO.getDeclaredConstructor();
			userDao = (UserDaoInterface) userDaoConstructor.newInstance();
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}
	
	public User getUser(int userId) throws CustomBankException {
		User user = userDao.getUser(userId);
		if (user == null) {
			throw new CustomBankException(CustomBankException.USER_NOT_EXISTS);
		}
		return user;
	}

	public int checkUserType(int userId, String password) throws CustomBankException {
		User user = getUser(userId);
		checkPassword(password, user);
		return user.getType();
	}
	
	
	public boolean checkPassword(String password, User user) throws CustomBankException {
		if(!Sha_256.getHashedPassword(password).equals(user.getPassword())) {
			throw new CustomBankException(CustomBankException.INVALID_PASSWORD);
		}
		return true;
	}
}
