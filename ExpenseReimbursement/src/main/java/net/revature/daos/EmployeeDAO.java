package net.revature.daos;

import net.revature.beans.Employee;
import net.revature.beans.Request;
import net.revature.exceptions.IncorrectSubmissionException;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee>{
    // set the generic's type here since its inherited
    // set to Employee
    public Employee getById(int id);
    public Employee getByUsername(String username);
    public Employee getByPassword(String password);
    public Employee getByFullName(String fullName);
    public List<Employee> requestId(int requestId);
    public void updateRequests(int requestId, int employeeId) throws SQLException;
	
}

