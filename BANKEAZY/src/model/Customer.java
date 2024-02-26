package model;

public class Customer extends User{
	private int id;
	private String pan;
	private String aadhar;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	@Override
	public String toString() {
		return super.toString() + "\nCustomer [id=" + id + ", pan=" + pan + ", aadhar=" + aadhar + "]";
	}
	
	
	
}
