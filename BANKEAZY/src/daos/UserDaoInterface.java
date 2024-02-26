package daos;

import exception.CustomBankException;
import model.User;

public interface UserDaoInterface {
	public User getUser(int userId) throws CustomBankException;
}
