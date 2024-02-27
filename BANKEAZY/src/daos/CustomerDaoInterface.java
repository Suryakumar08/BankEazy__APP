package daos;

import exception.CustomBankException;
import model.Customer;

public interface CustomerDaoInterface {

	public int addCustomer(Customer customer) throws CustomBankException;

}
