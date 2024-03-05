package pages;

import java.util.logging.Logger;

import exception.CustomBankException;
import helpers.EmployeeHelper;
import model.Branch;
import utilities.InputHelper;

public class EditBranchPage {
	private static Logger logger = BankEazyApp.logger;
	public static void run() throws CustomBankException{
		EmployeeHelper helper = new EmployeeHelper();
		try {
			logger.info("Enter branchId to edit : ");
			int branchId = InputHelper.getInt();
			Branch branch = helper.getBranch(branchId);
			logger.fine(branch.toString());
			
			Branch editBranch = new Branch();
			boolean continueProgram = true;
			int noOfChanges = 0;
			while(continueProgram) {
				try {
					logger.info("1) Edit Branch Name\n2) Edit Branch Address\n3) Edit IFSC\n4) Edit!!!");
					int choice = InputHelper.getInt();
					switch(choice) {
					case 1:{
						logger.info("Enter new name : ");
						String newBranchName = InputHelper.getString();
						editBranch.setName(newBranchName);
						noOfChanges++;
						break;
					}
					case 2:{
						logger.info("Enter new address : ");
						String newBranchAddress = InputHelper.getString();
						editBranch.setAddress(newBranchAddress);
						noOfChanges++;
						break;
					}
					case 3:{
						logger.info("Enter new IFSC : ");
						String newIfsc = InputHelper.getString();
						editBranch.setIfsc(newIfsc);
						noOfChanges++;
						break;
					}
					case 4:{
						continueProgram = false;
						break;
					}
					}
					
				}
				catch(CustomBankException e) {
					logger.warning(e.getMessage());
				}
			}
			if(noOfChanges > 0) {
				editBranch.setId(branchId);
				boolean isEdited = helper.editBranch(editBranch);
				logger.fine(isEdited ? "Edit Successful" : "Edit Failed");
			}
			else {
				logger.fine("No change needed!");
			}
			
			
		}catch(CustomBankException e) {
			logger.warning(e.getMessage() + e.getCause());
		}
	}
}
