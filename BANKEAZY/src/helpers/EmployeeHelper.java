package helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import daos.AccountDaoInterface;
import daos.BranchDaoInterface;
import daos.CustomerDaoInterface;
import daos.EmployeeDaoInterface;
import daos.TransactionDaoInterface;
import exception.CustomBankException;
import model.Account;
import model.Branch;
import model.Customer;
import model.Employee;
import model.Transaction;
import utilities.Sha_256;

public class EmployeeHelper {

	private EmployeeDaoInterface employeeDao = null;
	private CustomerDaoInterface customerDao = null;
	private AccountDaoInterface accountDao = null;
	private TransactionDaoInterface transactionDao = null;
	private BranchDaoInterface branchDao = null;

	public EmployeeHelper() throws CustomBankException {
		Class<?> EmployeeDAO;
		Class<?> CustomerDAO;
		Constructor<?> empDao;
		Constructor<?> custDao;
		Class<?> AccountDAO;
		Constructor<?> accDao;
		Class<?> TransactionDAO;
		Constructor<?> transDao;
		Class<?> BranchDAO;
		Constructor<?> branchDaoCon;
		try {
			EmployeeDAO = Class.forName("daos.EmployeeDAO");
			empDao = EmployeeDAO.getDeclaredConstructor();
			employeeDao = (EmployeeDaoInterface) empDao.newInstance();
			
			CustomerDAO = Class.forName("daos.CustomerDAO");
			custDao = CustomerDAO.getDeclaredConstructor();
			customerDao = (CustomerDaoInterface)custDao.newInstance();
			
			AccountDAO = Class.forName("daos.AccountDAO");
			accDao = AccountDAO.getDeclaredConstructor();
			accountDao = (AccountDaoInterface) accDao.newInstance();
			
			TransactionDAO = Class.forName("daos.TransactionDAO");
			transDao = TransactionDAO.getDeclaredConstructor();
			transactionDao = (TransactionDaoInterface) transDao.newInstance();
			
			BranchDAO = Class.forName("daos.BranchDAO");
			branchDaoCon = BranchDAO.getDeclaredConstructor();
			branchDao = (BranchDaoInterface)branchDaoCon.newInstance();
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
		}
	}

	public Employee getEmployee(int userId) throws CustomBankException {
		Employee employee = employeeDao.getEmployee(userId);
		return employee;
	}

	public int addEmployee(Employee employee) throws CustomBankException {
		String password = employee.getPassword();
		employee.setPassword(Sha_256.getHashedPassword(password));
		employee.setStatus(1);
		employee.setType("employee");
		return employeeDao.addEmployee(employee);
	}

	public int addCustomer(Customer customer) throws CustomBankException {
		String password = customer.getPassword();
		customer.setPassword(Sha_256.getHashedPassword(password));
		customer.setStatus(1);
		customer.setType("customer");
		return customerDao.addCustomer(customer);
	}
	
	public long addAccount(Account account) throws CustomBankException{
		return accountDao.addAccount(account);
	}
	
	public Map<Integer, Customer> getCustomers() throws CustomBankException{
		return customerDao.getCustomers();
	}
	
	public Map<Integer, Employee> getEmployees() throws CustomBankException{
		Map<Integer, Employee> employeeList = employeeDao.getAllEmployees();
		return employeeList;
	}

	public List<Transaction> getTransactions(long accountNo) throws CustomBankException{
		return transactionDao.getTransactions(accountNo, 0, System.currentTimeMillis());
	}

	public List<Transaction> getAllTransactions() throws CustomBankException{
		return transactionDao.getTransactions(0, System.currentTimeMillis());
	}

	public Customer getCustomer(int customerId) throws CustomBankException{
		return customerDao.getCustomer(customerId);
	}

	public int addBranch(Branch newBranch) throws CustomBankException{
		return branchDao.addBranch(newBranch);
	}

	public Map<Integer, Branch> getAllBranches() throws CustomBankException{
		return branchDao.getBranches();
	}

	public Branch getBranch(int branchId) throws CustomBankException {
		return branchDao.getBranch(branchId);
	}

	public boolean editBranch(Branch editBranch) throws CustomBankException {
		return branchDao.updateBranch(editBranch);
	}

	public boolean editUser(Customer editCustomer) {
		return false;
	}
	

}
