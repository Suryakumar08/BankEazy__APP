package model;

public class Employee extends User {
	private Integer id;
	private Integer salary;
	private Long joiningDate;
	private Integer branchId;
	private Integer role;		//1 -> Admin, 2 -> Employee
	
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
	public long getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(long joinedDate) {
		this.joiningDate = joinedDate;
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
	public String getRoleAsString(String roleType) {
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
		return super.toString() + "Employee [salary=" + salary + "\njoinedDate=" + joiningDate + "\nbranchId=" + branchId
				+ "\nrole=" + role + "]\n";
	}
	
	
	
}
