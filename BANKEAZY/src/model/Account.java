package model;

import enums.AccountStatus;

public class Account {
	private Long accountNo;
	private Integer customerId;
	private Double balance;
	private Integer branchId;
	private Integer status;		//1 -> Active 0 -> InActive
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
		this.balance = ((double)Math.round(balance * 100.0) / 100.0);
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
	public String getStatusAsString() {
		return AccountStatus.getAccountStatus(status).toString();
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", customerId=" + customerId + ", balance=" + balance + ", branchId="
				+ branchId + ", status=" + getStatusAsString() + "]";
	}
	
	
	
}
