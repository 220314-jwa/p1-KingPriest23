package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Beans.Employee;
import Beans.Request;
import Utilities.ConnectionFactory;

public class RequestDaoPost implements RequestDAO {

	private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();

	@Override
	public int create(Request engineer) {
		Connection conn = connFactory.getConnection();
		String sql = "insert into request (requestId, submitterId, status_id, cost,"
				+ "description, location, eventDate, submittedDate";
		try {
			
			PreparedStatement requestedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			requestedStatement.setInt(1, engineer.getRequestId());
			requestedStatement.setInt(2, engineer.getSubmitterId());
			int status_id;
			if (engineer.getStatus().equals("Approved")) {
				status_id = 1;
			} else {
				status_id = 2;
			}
			requestedStatement.setInt(3, status_id);
			requestedStatement.setInt(4, engineer.getCost());
			requestedStatement.setString(5, engineer.getDescription());
			requestedStatement.setString(6, engineer.getLocation());
			requestedStatement.setString(7, engineer.getEventDate());
			requestedStatement.setString(8, engineer.getSubmittedDate());
			
			 // Connection: this represents the connection to the database {setAutoCommit,createStatemetnt(),
            // prepareStatement(sql,key), prepareStatement(sql),commit(),rollback(),close()
            // for (ACID) transaction management:
            // setAutoCommit: each statement is once again committed automatically when it is completed. Back to
            // the default state where I don't have to call commit myself. Disable during transaction mode.
            conn.setAutoCommit(false);
            // executeUpdate(): used to execute statements such as insert, update, delete. It returns an
            // integer value representing the number of rows affected
            int count = requestedStatement.executeUpdate();
            // ResultSet: represents the results of the query {next(),getInt(columnName),getGeneratedKeys(),
            // executeQuery()
            //auto-generated id
            ResultSet requestedResults = requestedStatement.getGeneratedKeys();
            if (count > 0) {
            	System.out.println("Request added!");
            // next(): ResultSet cursor is initially positioned before the first row; the first call to the method
            // next makes the first row the current row. Moves cursor forward one row
            requestedResults.next();
            // set requestId to the auto-generated key in first sql column
            int id = requestedResults.getInt(1);
            engineer.setRequestId(id);;
            conn.commit();
            }
            else {
            	System.out.println("Something went wrong trying to add a request.");
            	conn.rollback(); // rollback the changes
            }
        } catch (SQLException e){
            e.printStackTrace();
            try {
            	conn.rollback();
            } catch (SQLException e1) {
            	e1.printStackTrace();
            }
        }	
		return engineer.getRequestId();
	}

	@Override
	public void update(Request updatedObj) {
		Connection connection = connFactory.getConnection();
		String sql = "update requests set request_id = ?, submitter_id = ?, status_id=?, cost=?,"
				+ "description = ?, location=?, event_date=?, submitted_date=? ";
		try {
			PreparedStatement requestedStmt = connection.prepareStatement(sql);
			requestedStmt.setInt(1, updatedObj.getRequestId());
			int status_id = (updatedObj.getStatus().equals("Approved")) ? 1: 2;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public List<Request> getAll() {
		// initialize empty Request list:
		List<Request> requests = new ArrayList<Request>();
		String sql = "Select * from request";
		try (Connection connection = connFactory.getConnection()) {
			PreparedStatement requestedStmt = connection.prepareStatement(sql);
			// get the result from query:
			ResultSet rs = requestedStmt.executeQuery();
			// because the resultSet has multiple requests in it, use while loop
			while (rs.next()) {
				Request request = parseResultSet(rs);
				requests.add(request);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests;
	}

	// given a result set, parse it out and then return the request
	private Request parseResultSet(ResultSet rs) throws SQLException {
		Request request = new Request();
		// do something with return value:
		request.setRequestId(rs.getInt(1));
		request.setSubmitterId(rs.getInt(2));
		request.setCost(rs.getInt(3));
		request.setDescription(rs.getString(4));
		request.setLocation(rs.getString(5));
		request.setEventDate(rs.getString(6));
		request.setSubmittedDate(rs.getString(7));
		int status_id = rs.getInt(8);
		String status = (status_id == 1) ? "Approved" : "Rejected";
		request.setStatus(status);

		return request;
	}

	@Override
	public void delete(Request objToDelete) {
		Connection connection = connFactory.getConnection();
		String sql = "delete from request where request_id = ?;";
		try {
			PreparedStatement requestedStmt = connection.prepareStatement(sql);
			requestedStmt.setInt(1, objToDelete.getRequestId());
			connection.setAutoCommit(false);
			int count = requestedStmt.executeUpdate();
			if (count != 1) {
				connection.rollback();
				throw new SQLException("Error: no object found to update");
			} else
				connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Request getBySubmitterId(int submitterId) {
		// TODO Auto-generated method stub
		Request request = null;
		try (Connection conn = connFactory.getConnection()) {
			String sql = "select * from request where submitterId=?";
			PreparedStatement requestedStatement = conn.prepareStatement(sql);
			requestedStatement.setInt(1, submitterId);
			ResultSet requestedResult = requestedStatement.executeQuery();
			if (requestedResult.next()) {
				request = new Request();
				request.setSubmitterId(submitterId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return request;
	}



	@Override
	public List<Request> getByApprovedRequests(Employee engineer) {
		List<Request> requests = new LinkedList<>();
		try (Connection conn = connFactory.getConnection()) {
			String sql = "select * from request join employee on request.request_id=employee.request_id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, engineer.getRequestId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Request request = parseResultSet(rs);
				requests.add(request);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests;
	}

	@Override
	public Request getById(int id) {
		// initialize request object to be null:
		Connection conn = connFactory.getConnection();
		Request request = null;
		String sql = "Select * from pet where request_id = ?";
		try {
			PreparedStatement requestedStmt = conn.prepareStatement(sql);
			requestedStmt.setInt(1, id);
			ResultSet rs = requestedStmt.executeQuery();
			if (rs.next()) {
				request = parseResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return request;
	}

	@Override
	public List<Request> getByStatus(String status) {
		List<Request> requests = new LinkedList<>();
		Connection connection = connFactory.getConnection();
		try {
			String sql = "select * from request where status_id";
			PreparedStatement stmt = connection.prepareStatement(sql);
			int statusId = (status.equals("Approved")?1:2);
			stmt.setInt(1, statusId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Request request = parseResultSet(rs);
				requests.add(request);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests;
}
}



































