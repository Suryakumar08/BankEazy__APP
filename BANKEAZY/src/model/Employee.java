package model;

import utilities.Utilities;

public class Employee extends User {
	private Integer userId;
	private Integer salary;
	private Long joiningDate;
	private Integer branchId;
	private Integer role;		//1 -> Admin, 2 -> Employee
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public Long getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(long joinedDate) {
		this.joiningDate = joinedDate;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public Integer getRole() {
		return role;
	}
	public String getRoleAsString() {
		if(role == 1) {
			return "Admin";
		}
		return "Employee";
	}
	public void setRole(int role) {
		this.role = role;
	}
	public void setRoleFromString(String role) {
		if(role.equalsIgnoreCase("admin")) {
			this.role = 1;
		}
		else this.role = 2;
	}
	@Override
	public String toString() {
		return super.toString() + "Employee [salary=" + salary + "\njoinedDate=" + Utilities.getDateString(joiningDate) + "\nbranchId=" + branchId
				+ "\nrole=" + getRoleAsString() + "]\n";
	}
	
	
	
}
