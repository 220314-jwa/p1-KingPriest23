package Services;
import Services.EmployeeServices;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Beans.Department;
import Beans.Employee;
import DAOs.DepartmentDAO;
import DAOs.EmployeeDAO;
import Exceptions.AlreadyApprovedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class EmployeeServicesTest {
	@Mock // says that we want Mockito to create a mock version of this object
	private EmployeeDAO empDao;
	@Mock
	private DepartmentDAO deptDao;
	
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
    public void checkStatus() throws AlreadyApprovedException {
        String requestStatus = "Approved";
        List<Employee> engineers = (List<Employee>) empDao.getByStatus(requestStatus);
        boolean onlyApprovedStatus = true;
        for (Employee employed: engineers) {
        	if (!(employed.getStatus().equals(requestStatus))) {
        		onlyApprovedStatus = false;
        		break;
        	}
        }
    }
    @Test
    public void submitRequest() {
    	r
    }
  
}
