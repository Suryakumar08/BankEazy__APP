package model;

public class Account {
	private long accountNo;
	private int customerId;
	private double balance;
	private int branchId;
	private int status;		//1 -> Active 0 -> InActive
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", customerId=" + customerId + ", balance=" + balance + ", branchId="
				+ branchId + ", status=" + (status == 0 ? "Inactive" : "Active") + "]";
	}
	
	
	
}
