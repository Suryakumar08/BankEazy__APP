package model;

import utilities.Utilities;

public class Transaction {
	private Long transactionId;
	private Integer customerId;
	private Long accountNo;
	private Long transactionAccountNo;
	private String description;
	private Integer type;		//1 ->	 Deposit, 2 -> Withdraw, 3 -> debit, 4 -> credit
	private Integer status;		//0 -> Failure, 1 -> Success
	private Long time;
	private Double closingBalance;
	private Double amount;
	private Long referenceNo;
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public Long getTransactionAccountNo() {
		return transactionAccountNo;
	}
	public void setTransactionAccountNo(long transactionAccountNo) {
		this.transactionAccountNo = transactionAccountNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Integer getType() {
		return type;
	}
	public void setTypeFromString(String type) {
		if(type.equalsIgnoreCase("deposit")) {
			this.type = 1;
		}
		else if(type.equalsIgnoreCase("withdraw")) {
			this.type = 2;
		}
		else if(type.equalsIgnoreCase("debit")) {
			this.type = 3;
		}
		else if(type.equalsIgnoreCase("credit")) {
			this.type = 4;
		}
	}
	public String getTypeAsString() {
		if(type == 1) {
			return "Deposit";
		}
		if(type == 2) {
			return "Withdraw";
		}
		if(type == 3) {
			return "Debit";
		}
		if(type == 4) {
			return "Credit";
		}
		else {
			return "";
		}
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Double getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}
	public Long getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", customerId=" + customerId
				+ ", accountNo=" + accountNo + ", transactionAccountNo=" + transactionAccountNo + ", description="
				+ description + ", type=" + getTypeAsString() + ", status=" + (status == 1 ? "Success" : "Failed") + ", time=" + Utilities.getDateTimeString(time) + ", closingBalance="
				+ closingBalance + ", amount=" + amount + " referenceNo=" + referenceNo + "]";
	}
}
