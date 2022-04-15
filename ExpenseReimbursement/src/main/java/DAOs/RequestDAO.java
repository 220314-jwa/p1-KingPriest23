package DAOs;

import java.sql.SQLException;

import Beans.Request;
import Utilities.ConnectionFactory;

public interface RequestDAO extends GenericDAO<Request> {
	
	public Request getBySubmitterId(int submitterId);
	
	public void updateRequests(int cost) throws SQLException;
	
	public Request getByStatusId(int statusId);
	
	
	
	
	
}
