
package net.revature.services;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import net.revature.beans.Employee;
import net.revature.beans.Request;
import net.revature.daos.DAOFactory;
import net.revature.daos.EmployeeDAO;
import net.revature.daos.RequestDAO;
import net.revature.exceptions.IncorrectCredentialsException;
import net.revature.exceptions.IncorrectSubmissionException;
import net.revature.exceptions.NeedGradeException;
import net.revature.exceptions.UsernameAlreadyExistsException;

public class EmployeeServiceImpl implements EmployeeServices{
	
    private EmployeeDAO empDao = DAOFactory.getEmpDao();
    private RequestDAO reqDao = DAOFactory.getReqDao();

    @Override
    public Employee logIn(String username, String password) throws IncorrectCredentialsException {
        Employee engineer = empDao.getByUsername(username);
        if (engineer != null && engineer.getPassword().equals(password)) {
            return engineer;
        } else {
            throw new IncorrectCredentialsException();
        }
    }
    
    @Override
	public Employee register(Employee newEmployee) throws UsernameAlreadyExistsException {
		int id = empDao.create(newEmployee);
    	if (id != 0) {
    		newEmployee.setId(id);
    		return newEmployee;
    	} else {
    		throw new UsernameAlreadyExistsException();
    	}
		
	}
    @Override
	public List<Request> viewSubmittedRequest() {
		return reqDao.getByStatus("Approved");
	}
    
    @Override
	public Request getSubmittedByDate(String submittedDate) {
    	List<Request> requests = reqDao.getAll();
    	List<Request> dates = new LinkedList<>();
    	for (int i = 0; i < requests.size(); i++) {
    		if (requests.get(i).getSubmittedDate().equals(submittedDate)) {
    			dates.add(dates.get(i));
    		}
    	}
    	return (Request) dates;
	}
	

	@Override
	public Employee getEmployeeById(int id) {
		return empDao.getById(id);
	}

	

	@Override
	public Employee gradeRequests(Employee engineer, Request gradeRequests) throws NeedGradeException, Exception {
		gradeRequests = reqDao.getById(gradeRequests.getRequestId());
		if (gradeRequests.getStatus().equals("Approved")) {
			throw new NeedGradeException();
		} else {
			engineer = empDao.getById(engineer.getId());
			if (engineer != null) {
				gradeRequests.setStatus("Approved");
				engineer.getRequests().add(gradeRequests);
				try {
					reqDao.update(gradeRequests);
					empDao.update(engineer);
					empDao.updateRequests(gradeRequests.getRequestId(), engineer.getRequestId());
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception();
				}
			}
			return engineer;
		}
	}

	@Override
	public Request getRequestById(int id) {
		return reqDao.getById(id);
	}

	

}
