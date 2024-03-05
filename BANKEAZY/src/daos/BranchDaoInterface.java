package daos;

import java.util.Map;

import exception.CustomBankException;
import model.Branch;

public interface BranchDaoInterface {
	int addBranch(Branch branch) throws CustomBankException;
	
	Map<Integer, Branch> getBranches() throws CustomBankException;
	
	boolean updateBranch(Branch branch) throws CustomBankException;
	
	Branch getBranch(int branchId) throws CustomBankException;
}
