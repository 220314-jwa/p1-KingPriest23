package DAOs;

import Beans.Employee;
import Beans.Request;
import Exceptions.IncorrectSubmissionException;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee>{
    // set the generic's type here since its inherited
    // set to Employee
    public Employee getById(int id);
    public Employee getByUsername(String username);
    public Employee getByPassword(String password);
    public Employee getByFirstName(String firstName);
    public Employee getByLastName(String lastName);
    public List<Employee> requestId(int requestId);
    public void updateRequests(int requestId, int employeeId) throws SQLException;
	
}

