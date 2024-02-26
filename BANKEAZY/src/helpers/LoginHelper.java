package helpers;

import daos.UserDAO;
import daos.UserDaoInterface;
import exception.CustomBankException;
import model.User;
import utilities.Sha_256;

public class LoginHelper {

	public int checkUser(int userId, String password) throws CustomBankException {
		UserDaoInterface userDao = new UserDAO();
		User user = userDao.getUser(userId);
		if(user == null) {
			return -1;
		}
		else{
			if(!Sha_256.getHashedPassword(password).equals(user.getPassword())) {
				return 0;
			}
			else {
				if(user.getStatus(1) == 0) {
					return 1;
				}
				else {
					if(user.getType(1) == 1) {
						return 2;
					}
					else {
						return 3;
					}
				}
			}
		}
	}
	
}
