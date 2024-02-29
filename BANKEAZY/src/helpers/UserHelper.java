package helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import daos.CustomerDaoInterface;
import daos.UserDaoInterface;
import exception.CustomBankException;
import model.Customer;
import model.User;
import utilities.Sha_256;

public class UserHelper {

	private UserDaoInterface userDao;
	private CustomerDaoInterface customerDao;

	public UserHelper() throws CustomBankException {
		Class<?> UserDAO;
		Class<?> CustomerDAO;
		
		Constructor<?> userDaoConstructor;
		Constructor<?> customerDaoConstructor;
		try {
			UserDAO = Class.forName("daos.UserDAO");
			userDaoConstructor = UserDAO.getDeclaredConstructor();
			userDao = (UserDaoInterface) userDaoConstructor.newInstance();
			
			CustomerDAO = Class.forName("daos.CustomerDAO");
			customerDaoConstructor = CustomerDAO.getDeclaredConstructor();
			customerDao = (CustomerDaoInterface) customerDaoConstructor.newInstance();
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	public int checkUserType(int userId, String password) throws CustomBankException {
		User user = userDao.getUser(userId);
		if (user == null) {
			throw new CustomBankException(CustomBankException.USER_NOT_EXISTS);
		}
		if(!Sha_256.getHashedPassword(password).equals(user.getPassword())) {
			throw new CustomBankException(CustomBankException.INVALID_PASSWORD);
		}
		return user.getType(userId);
	}
	
	public Customer getCustomer(int customerId) throws CustomBankException{
		return customerDao.getCustomer(customerId);
	}
}
