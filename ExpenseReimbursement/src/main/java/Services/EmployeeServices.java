package Services;

import Beans.Employee;
import Exceptions.AlreadyApprovedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

import java.util.List;

public interface EmployeeServices {
    // layout of all behaviors
    // abstract methods, interface to have behaviors
    // type code in class, to get methods to do something
    public Employee logIn(String username, String password) throws IncorrectCredentialsException;
    public Employee register(Employee newEmployee) throws UsernameAlreadyExistsException;
    public Employee getEmployeeById(int id);
    public String getEmployee(String firstName, String lastName);
    public Employee getApprovedEmployee(Employee deptId, Employee managerId) throws AlreadyApprovedException;
    public Employee getStatus(String status) throws AlreadyApprovedException;
   
    
    
}
