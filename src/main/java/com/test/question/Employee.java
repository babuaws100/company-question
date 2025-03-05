package com.test.question;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private int salary;
	private int managerId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	public boolean equals(Object other) {
		if(! (other instanceof Employee)) {
			return false;
		}
		
		Employee otherEmp = (Employee) other;
		if(this.id == otherEmp.getId()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return this.managerId;
	}
	
	public String toString() {
		return String.valueOf(id);
	}

}
