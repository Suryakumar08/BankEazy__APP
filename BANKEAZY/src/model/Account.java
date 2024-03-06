package model;

public class Account {
	private Long accountNo;
	private Integer customerId;
	private Double balance;
	private Integer branchId = -1;
	private Integer status = -1;		//1 -> Active 0 -> InActive
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public Integer getStatus() {
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
