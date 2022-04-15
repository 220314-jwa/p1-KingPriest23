package Services;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Beans.Employee;
import Beans.Request;
import DAOs.DepartmentDAO;
import DAOs.EmployeeDAO;
import DAOs.RequestDAO;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class EmployeeServicesTest {
	@Mock // says that we want Mockito to create a mock version of this object
	private EmployeeDAO empDao;
	@Mock
	private DepartmentDAO deptDao;
	@Mock
	private RequestDAO reqDao;
	
//	private static Employee employed = new Employee();
//	private static Employee manager = new Employee();
	
	  // we need a field for the class that we're testing
    @InjectMocks // this is where Mockito needs to inject the mocks
    private EmployeeServices servedEmployee = new EmployeeServiceImpl();

    @Test
    void logInSuccessfully() throws IncorrectCredentialsException {
        // setup (arguments, expected results, etc.)
        String username = "Vincent";
        String password = "Mushu";
        // mocking: we need to mock EmployeeDAO.getByUsername(username)
        // we're expecting a employee with matching username & password
        Employee mockEmployee = new Employee();
        mockEmployee.setUsername(username);
        mockEmployee.setPassword(password);
        when(empDao.getByUsername(username)).thenReturn(mockEmployee);
        // call the method we're testing
        Employee resultServed = servedEmployee.logIn(username, password);
        // assertion
        assertEquals(username, resultServed.getUsername());
    }
    @Test
    public void logInWrongUsername() {
        String username = "Vincent";
        String password = "Mushu";
        // we need to mock EmployeeDAO.getByUsername(username)
        when(empDao.getByUsername(username)).thenReturn(null);
        
        assertThrows(IncorrectCredentialsException.class, () -> {
            // put the code that we're expecting to throw the exception
            servedEmployee.logIn(username, password);
        });
    }
    @Test
    public void logInWrongPassword() {
        String username = "Vincent";
        String password = "Mushu";
     // we need to mock EmployeeDAO.getByUsername(username)
        Employee mockEmployee = new Employee();
        mockEmployee.setUsername(username);
        mockEmployee.setPassword("fake_password");
        when(empDao.getByUsername(username)).thenReturn(mockEmployee);
        assertThrows(IncorrectCredentialsException.class, () -> {
            // put the code that we're expecting to throw the exception
            servedEmployee.logIn(username, password);
        });
    }
    @Test
    public void registerSuccessfully() throws UsernameAlreadyExistsException {
        Employee newEmployee = new Employee();
        Employee resultServed = servedEmployee.register(newEmployee);
        // the behavior that I'm looking for is that the method returns
        // the Employee with their newly generated ID, so that I want to
        // make sure the ID was generated (not the default)
        assertNotEquals(0, resultServed.getId());
    }
    @Test
    public void registerUsernameTaken() {
        Employee newEmployee = new Employee();
        newEmployee.setUsername("Vincent");
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            servedEmployee.register(newEmployee);
        });
    }
    @Test
    public void viewSubmittedRequests() {
    	when(reqDao.getByStatus("Approved")).thenReturn(Collections.emptyList());
    	List<Request> requests = servedEmployee.viewSubmittedRequest();
    	assertNotNull(requests);
    }
   @Test
   public void getBySubmittedDate() {
	   String date = "2022-04-15";
	   List<Request> mockRequests = new LinkedList<>();
	   Request request = new Request();
	   request.setSubmittedDate("2022-04-15");
	   Request notRequest = new Request();
	   notRequest.setSubmittedDate("2022-05-22");
	   mockRequests.add(request);
	   mockRequests.add(notRequest);
	   when(reqDao.getAll()).thenReturn(mockRequests);
	   
	   List<Request> reqByDate = (List<Request>) servedEmployee.getSubmittedByDate(date);
	   boolean onlyReqDates = true;
	   for (Request reqs: reqByDate) {
		   String reqDates = reqs.getSubmittedDate();
		   if (!reqDates.contains(date)) {
			   onlyReqDates = false;
			   break;
		   }
	   }
	   assertTrue(onlyReqDates);
   }
  
   @Test
   public void submittedSuccessfully() throws Exception {
	   Employee testEmployee = new Employee();
	   Request testRequest = new Request();
	   // reqDAo.getById: return testRequest
	   when(reqDao.getById(testRequest.getRequestId())).thenReturn(testRequest);
	   // empDao.getById: return testEmployee
	   when(empDao.getById(testEmployee.getRequestId())).thenReturn(testEmployee);
	   // reqDAo.update: do nothing
	   // when reqDao update is called with any request object, do nothing
	   doNothing().when(reqDao).update(any(Request.class));
	   doNothing().when(empDao).update(any(Employee.class));
	   doNothing().when(empDao).updateRequests(testEmployee.getRequestId(), testRequest.getRequestId());
	   Employee result = servedEmployee.gradeRequests(testEmployee, testRequest);
	   
	   // employee now has request in list of requests, and the request in the list has its status updated
	   // check with the "rejected" status in the employee list
	   testRequest.setStatus("Rejected");
	   List<Request> employeeReqs = result.getRequests();
	   assertTrue(employeeReqs.contains(testRequest));
	   // Mockito.verify allows you to make sure that a particular mock method was called 
	   // (or that it was never called, or how many times, etc.)
	   verify(reqDao, times(2)).update(any(Request.class));
	   
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
