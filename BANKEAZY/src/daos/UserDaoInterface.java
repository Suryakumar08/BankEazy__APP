package daos;

import exception.CustomBankException;
import model.User;

public interface UserDaoInterface {
	
	//read
	User getUser(int userId) throws CustomBankException;
}
