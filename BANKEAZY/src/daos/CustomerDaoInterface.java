package daos;

import java.util.Map;

import exception.CustomBankException;
import model.Customer;

public interface CustomerDaoInterface {

	public int addCustomer(Customer customer) throws CustomBankException;
	
	public Customer getCustomer(int customerId) throws CustomBankException;

	public Map<Integer, Customer> getCustomers() throws CustomBankException;

}
