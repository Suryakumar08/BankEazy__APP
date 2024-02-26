package model;

public class Transaction {
	private int id;
	private long transactionId;
	private int customerId;
	private long accountNo;
	private long transactionAccountNo;
	private String description;
	private int type;		//1 ->	 Deposit, 2 -> Withdraw, 3 -> debit, 4 -> credit
	private int status;		//0 -> Failure, 1 -> Success
	private long time;
	private double closigBalance;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public long getTransactionAccountNo() {
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getClosigBalance() {
		return closigBalance;
	}
	public void setClosigBalance(double closigBalance) {
		this.closigBalance = closigBalance;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", transactionId=" + transactionId + ", customerId=" + customerId
				+ ", accountNo=" + accountNo + ", transactionAccountNo=" + transactionAccountNo + ", description="
				+ description + ", type=" + type + ", status=" + status + ", time=" + time + ", closigBalance="
				+ closigBalance + "]";
	}
	
}
