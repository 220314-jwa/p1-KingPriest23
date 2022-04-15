
package Beans;

import java.util.Objects;

public class Department {
    // fields
    private int employees;
    private String deptName;
    private String deptHead;
    private String status;
    
	// constructor no-args
    public Department() {
    // constructor with parameters
    }
    public Department(int employees, String deptName, String deptHead) {
        this.employees = employees;
        this.deptName = deptName;
        this.deptHead = deptHead;
    }
    // getters and setters
    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptHead() {
        return deptHead;
    }

    public void setDeptHead(String deptHead) {
        this.deptHead = deptHead;
    }
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    // Override toString: for consistency in the display of information in your views. It is far easier to override
    // the toString method than to remember the format used in each view for your class.
    // is a method of the object class in java and it is inherited by all the classes in java by default. It
    // provides the string representation of any object in java.
	@Override
	public String toString() {
		return "Department [employees=" + employees + ", deptName=" + deptName + ", deptHead=" + deptHead + ", status="
				+ status + "]";
	}
    // Override equals and hashcode: because the framework requires that two objects that are the same must have the same hashcode.
    // if you override the equals method to do a special comparison of two objects are considered the same by the method, then the
    // hashcode of the two objects must also be the same.
	@Override
	public int hashCode() {
		return Objects.hash(deptHead, deptName, employees, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(deptHead, other.deptHead) && Objects.equals(deptName, other.deptName)
				&& employees == other.employees && Objects.equals(status, other.status);
	}
    
}
