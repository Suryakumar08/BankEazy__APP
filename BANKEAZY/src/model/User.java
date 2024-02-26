package model;

public class User {
	
	
	private int userId;
	private String name;
	private String password;
	private String mobile;
	private String gender;
	private long dob;
	private int type;		//1 -> Employee, 0 -> Customer
	private int status;		//1 -> Active, 0 -> Inactive
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getDob() {
		return dob;
	}
	public void setDob(long dob) {
		this.dob = dob;
	}
	public int getType(int requestingType) {
		return this.type;
	}
	public String getType(String requestingType) {
		if(this.type == 1) {
			return "Employee";
		}
		return "Customer";
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setType(String type) {
		if(type.equalsIgnoreCase("employee")) {
			this.type = 1;
		}
		else this.type = 0;
	}
	public int getStatus(int requestingType) {
		return this.status;
	}
	public String getStatus(String requestingType) {
		if(this.status == 0) {
			return "Inactive";
		}
		return "Active";
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setStatus(String status) {
		if(status.equalsIgnoreCase("active")) {
			this.status = 1;
		}
		else {
			this.status = 0;
		}
	}
	
	@Override
	public String toString() {
		String userType = "Customer";
		if(type == 1) {
			userType = "Employee";
		}
		String userStatus = "Inactive";
		if(status == 1) {
			userStatus = "Active";
		}
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", mobile=" + mobile + ", dob="
				+ dob + ", type=" + userType + ", status=" + userStatus + "]";
	}
	
	
	
}
