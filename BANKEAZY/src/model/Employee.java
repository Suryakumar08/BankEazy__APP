package model;

public class Employee extends User {
	private int id;
	private int salary;
	private long joinedDate;
	private int branchId;
	private int role;		//1 -> Admin, 2 -> Employee
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public long getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(long joinedDate) {
		this.joinedDate = joinedDate;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getRole(int roleType) {
		return role;
	}
	public String getRole(String roleType) {
		if(role == 1) {
			return "Admin";
		}
		return "Employee";
	}
	public void setRole(int role) {
		this.role = role;
	}
	public void setRole(String role) {
		if(role.equalsIgnoreCase("admin")) {
			this.role = 1;
		}
		else this.role = 2;
	}
	@Override
	public String toString() {
		return super.toString() + "\nEmployee [id=" + id + ", salary=" + salary + ", joinedDate=" + joinedDate + ", branchId=" + branchId
				+ ", role=" + role + "]";
	}
	
	
	
}
