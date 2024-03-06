package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.BranchHelper;
import model.Branch;
import utilities.InputHelper;
import utilities.Validators;

public class AddBranchPage {
	private static Logger logger = BankEazyApp.logger;
	public static void run() throws CustomBankException {
		BranchHelper helper = new BranchHelper();
		try {
			logger.info("Enter Branch Name : ");
			String branchName = InputHelper.getString();
			Validators.validateInput(branchName);
			
			logger.info("Enter Branch Address : ");
			String address = InputHelper.getString();
			Validators.validateInput(address);
			
			logger.info("Enter Branch IFSC : ");
			String ifsc = InputHelper.getString();
			Validators.validateInput(ifsc);
			
			Branch newBranch = new Branch();
			newBranch.setName(branchName);
			newBranch.setAddress(address);
			newBranch.setIfsc(ifsc);
			
			int addedBranchId = helper.addBranch(newBranch);
			logger.fine("Branch added successfully!");
			logger.fine("Branch Id of Added Branch : " + addedBranchId + "\n");
			
		}catch(CustomBankException e) {
			logger.warning(e.getMessage());
		}
	}
}
