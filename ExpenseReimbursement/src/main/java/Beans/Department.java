
package Beans;

import java.util.Objects;

public class Department {
    // fields
    private String deptName;
    private String deptHead;
    private int deptId;
    
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	// constructor no-args
    public Department() {
    // constructor with parameters
    }
    public Department(String deptName, String deptHead, int deptId) {
        this.deptName = deptName;
        this.deptHead = deptHead;
        deptId = 0;
    }
    // getters and setters
    

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
    
    // Override toString: for consistency in the display of information in your views. It is far easier to override
    // the toString method than to remember the format used in each view for your class.
    // is a method of the object class in java and it is inherited by all the classes in java by default. It
    // provides the string representation of any object in java.
    @Override
	public String toString() {
		return "Department [deptName=" + deptName + ", deptHead=" + deptHead + ", deptId=" + deptId + "]";
	}
    // Override equals and hashcode: because the framework requires that two objects that are the same must have the same hashcode.
    // if you override the equals method to do a special comparison of two objects are considered the same by the method, then the
    // hashcode of the two objects must also be the same.
	@Override
	public int hashCode() {
		return Objects.hash(deptHead, deptId, deptName);
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
		return Objects.equals(deptHead, other.deptHead) && deptId == other.deptId
				&& Objects.equals(deptName, other.deptName);
	}
	
	
    
}
