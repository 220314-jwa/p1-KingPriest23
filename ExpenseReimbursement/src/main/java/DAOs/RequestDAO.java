package DAOs;

import java.sql.SQLException;
import java.util.List;

import Beans.Employee;
import Beans.Request;
import Utilities.ConnectionFactory;

public interface RequestDAO extends GenericDAO<Request> {
	
	public Request getBySubmitterId(int submitterId);
	public List<Request> getByStatus(String status);
	public List<Request> getByApprovedRequests(Employee engineer);
	
	
	
	
	
}
