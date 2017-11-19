package model;

public class Employee {
	private String name;
	private boolean isAdmin;
	private String pass;
	private boolean onDuty;

	public Employee(String name, boolean isAdmin, String pass, boolean onDuty){
		this.name = name;
		this.isAdmin = isAdmin;
		this.pass = pass;
		this.onDuty = onDuty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isOnDuty() {
		return onDuty;
	}

	public void setOnDuty(boolean onDuty) {
		this.onDuty = onDuty;
	}
	
	

}
