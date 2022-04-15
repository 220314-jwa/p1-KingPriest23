package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import Beans.Request;
import Utilities.ConnectionFactory;

public class RequestDaoPost implements RequestDAO {

	private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();
	
	@Override
	public int create(Request engineer) {
	
		Connection conn = connFactory.getConnection();
		try {
			String sql = "insert into request (requestId, submitterId, eventTypeId, statusId, cost,"
					+ "description, location, eventDate, submittedDate";
			PreparedStatement requestedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			requestedStatement.setInt(1, engineer.getRequestId());
			requestedStatement.setInt(2, engineer.getSubmitterId());
			requestedStatement.setInt(3, engineer.getEventTypeId());
			requestedStatement.setInt(4, engineer.getStatusId());
			requestedStatement.setInt(5, engineer.getCost());
			requestedStatement.setString(6, engineer.getDescription());
			requestedStatement.setString(7, engineer.getLocation());
			requestedStatement.setString(8, engineer.getEventDate());
			requestedStatement.setString(9, engineer.getSubmittedDate());
			 // Connection: this represents the connection to the database {setAutoCommit,createStatemetnt(),
            // prepareStatement(sql,key), prepareStatement(sql),commit(),rollback(),close()
            // for (ACID) transaction management:
            // setAutoCommit: each statement is once again committed automatically when it is completed. Back to
            // the default state where I don't have to call commit myself. Disable during transaction mode.
            conn.setAutoCommit(false);
            // executeUpdate(): used to execute statements such as insert, update, delete. It returns an
            // integer value representing the number of rows affected
            requestedStatement.executeUpdate();
            // ResultSet: represents the results of the query {next(),getInt(columnName),getGeneratedKeys(),
            // executeQuery()
            //auto-generated id
            ResultSet requestedResults = requestedStatement.getGeneratedKeys();
            // next(): ResultSet cursor is initially positioned before the first row; the first call to the method
            // next makes the first row the current row. Moves cursor forward one row
            requestedResults.next();
            // set requestId to the auto-generated key in first sql column
            int id = requestedResults.getInt(1));
            return id;
            // commit(0 method of the Connection interface saves all the modifications made since last commit
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
		
		
	}

	@Override
	public Request getById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Request> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Request updatedObj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Request objToDelete) {
		// TODO Auto-generated method stub
		
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
	public void updateRequests(int cost) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = connFactory.getConnection();
		try {
			String sql = "update request set cost=?";
			PreparedStatement requestedStatement = conn.prepareStatement(sql);
			requestedStatement.setInt(1, cost);
			conn.setAutoCommit(false);
			int requestUpdated = requestedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Request getByStatusId(int statusId) {
		// TODO Auto-generated method stub
		Connection conn = connFactory.getConnection();
		try {
			String sql = "select * from request where status_id";
			PreparedStatement requestedStatement = conn.prepareStatement(sql);
			requestedStatement.setInt(1,  statusId);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



	
}
