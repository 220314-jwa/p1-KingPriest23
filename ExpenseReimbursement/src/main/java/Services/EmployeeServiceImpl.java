
package Services;

import Beans.Employee;
import DAOs.DAOFactory;
import DAOs.DepartmentDAO;
import DAOs.EmployeeDAO;
import Exceptions.AlreadyApprovedException;
import Exceptions.IncorrectCredentialsException;
import Exceptions.UsernameAlreadyExistsException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeServices{
    private EmployeeDAO empDAO = DAOFactory.getEmpDao();
    private DepartmentDAO deptDAO = DAOFactory.getDeptDao();

    @Override
    public Employee logIn(String username, String password) throws IncorrectCredentialsException {
        Employee engineer = empDAO.getByUsername(username);
        if (engineer != null && engineer.getPassword().equals(password)) {
            return engineer;
        } else {
            throw new IncorrectCredentialsException();
        }
    }
    
    @Override
	public Employee register(Employee newEmployee) throws UsernameAlreadyExistsException {
		// TODO Auto-generated method stub
    	int id = empDAO.create(newEmployee);
    	if (id != 0) {
    		newEmployee.setId(id);;
    		return newEmployee;
    	} else {
    		throw new UsernameAlreadyExistsException();
    	}
		
	}
	
    @Override
    public String getEmployee(String firstName, String lastName) {
    	String fullName = firstName + lastName;
        return fullName;
    }


	@Override
	public Employee getEmployeeById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getApprovedEmployee(Employee deptId, Employee managerId) throws AlreadyApprovedException {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Employee getStatus(String status) throws AlreadyApprovedException {
		// TODO Auto-generated method stub
		return null;
	}
}
