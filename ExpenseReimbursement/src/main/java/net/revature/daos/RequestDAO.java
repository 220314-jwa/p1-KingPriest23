package net.revature.daos;

import java.sql.SQLException;
import java.util.List;

import net.revature.beans.Employee;
import net.revature.beans.Request;
import net.revature.utilities.ConnectionFactory;

public interface RequestDAO extends GenericDAO<Request> {
	
	public Request getBySubmitterId(int submitterId);
	public List<Request> getByStatus(String status);
	public List<Request> getByApprovedRequests(Employee engineer);
	
	
	
	
	
}
