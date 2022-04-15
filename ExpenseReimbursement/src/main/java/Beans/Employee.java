package Beans;


import java.util.Objects;

public class Employee {
    private String username, password, firstName, lastName, status;
    private int Id, managerId, deptId;

    // constructor
    public Employee() {
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
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

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
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
		return "Employee [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", status=" + status + ", Id=" + Id + ", managerId=" + managerId + ", deptId=" + deptId
				+ "]";
	}
    // Override equals and hashcode: because the framework requires that two objects that are the same must have the same hashcode.
    // if you override the equals method to do a special comparison of two objects are considered the same by the method, then the
    // hashcode of the two objects must also be the same.

	@Override
	public int hashCode() {
		return Objects.hash(Id, deptId, firstName, lastName, managerId, password, status, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Id == other.Id && deptId == other.deptId && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && managerId == other.managerId
				&& Objects.equals(password, other.password) && Objects.equals(status, other.status)
				&& Objects.equals(username, other.username);
	}

    

}