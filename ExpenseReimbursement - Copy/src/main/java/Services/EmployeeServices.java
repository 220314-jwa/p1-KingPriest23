                                                                                                                                                                                                                                                                                                                                                                                                               package Services;

import java.util.List;

import Beans.Employee;
import Beans.Request;
import Exceptions.IncorrectCredentialsException;
import Exceptions.IncorrectSubmissionException;
import Exceptions.NeedGradeException;
import Exceptions.UsernameAlreadyExistsException;

public interface EmployeeServices {
    // layout of all behaviors
    // abstract methods, interface to have behaviors
    // type code in class, to get methods to do something
    public Employee logIn(String username, String password) throws IncorrectCredentialsException;
    public Employee register(Employee newEmployee) throws UsernameAlreadyExistsException;
    public Employee getEmployeeById(int id);
    public Request getRequestById(int id);
    public List<Request> viewSubmittedRequest();
    public Request getSubmittedByDate(String submittedDate);
    
    public Employee gradeRequests(Employee engineer, Request gradeRequests) throws NeedGradeException, Exception;
    
    
}
