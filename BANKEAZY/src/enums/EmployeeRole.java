package enums;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeRole {
	EMPLOYEE(0), ADMIN(1);
	private int role;
	private static Map<Integer, EmployeeRole> constantMap = new HashMap<>();
	
	static {
		for(EmployeeRole currRole : EmployeeRole.values()) {
			constantMap.put(currRole.getRole(), currRole);
		}
	}
	
	private EmployeeRole(int role) {
		this.role = role;
	}
	public int getRole() {
		return this.role;
	}
	
	public static EmployeeRole getEmployeeRole(int role) {
		return constantMap.get(role);
	}
}





