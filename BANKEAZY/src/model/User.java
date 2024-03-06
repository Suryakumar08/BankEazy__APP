package model;

import utilities.Utilities;

public class User {
	
	
	private Integer id;
	private String name;
	private String password;
	private String mobile;
	private String gender;
	private Long dob;
	private Integer type;		//1 -> Employee, 0 -> Customer
	private Integer status;		//1 -> Active, 0 -> Inactive
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Long getDob() {
		return dob;
	}
	public void setDob(long dob) {
		this.dob = dob;
	}
	public Integer getType() {
		return this.type;
	}
	public String getTypeAsString() {
		if(this.type == 1) {
			return "Employee";
		}
		return "Customer";
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setTypeFromString(String type) {
		if(type.equalsIgnoreCase("employee")) {
			this.type = 1;
		}
		else this.type = 0;
	}
	public Integer getStatus() {
		return this.status;
	}
	public String getStatusAsString() {
		if(this.status == 0) {
			return "Inactive";
		}
		return "Active";
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setStatusFromString(String status) {
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
		return "User [userId=" + id + "\nname=" + name + "\nmobile=" + mobile + "\ndob="
				+ Utilities.getDateString(dob) + "\ntype=" + userType +"\nstatus=" + userStatus + "]\n";
	}
	
	
	
}
