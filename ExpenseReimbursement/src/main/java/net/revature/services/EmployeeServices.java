                                                                                                                                                                                                                                                                                                                                                                                                               package net.revature.services;

import java.util.List;

import net.revature.beans.Employee;
import net.revature.beans.Request;
import net.revature.exceptions.IncorrectCredentialsException;
import net.revature.exceptions.IncorrectSubmissionException;
import net.revature.exceptions.NeedGradeException;
import net.revature.exceptions.UsernameAlreadyExistsException;

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
