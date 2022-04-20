
package DAOs;

import Beans.Employee;
import Beans.Request;
import Utilities.ConnectionFactory;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDaoPost implements EmployeeDAO {
    private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();


    @Override
    public int create(Employee engineer) {
    	
    	int generatedId = 0;
        // Allows connection to database
        Connection connection = connFactory.getConnection();
        // store sql command, DML, placeholders
        String sql = "insert into Employee (full_name, request_id, dept_id)" +
                "values (?, ?, ?)";
        // try catch statement: responsible for exception handling.
        try {
            // create a prepared statement (pre-compiled SQL statement) to pass in sql command
            // pass in flag "RETURN_GENERATED_KEYS" so id is generated
            // preparedStatement: provides a way to retrieve values from columns that are part of an index
            // or have default value assigned
            PreparedStatement employeeStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            employeeStatement.setString(1,  engineer.getFirstName() + " " + engineer.getLastName());
            employeeStatement.setString(2, engineer.getUsername());
            employeeStatement.setString(3,  engineer.getPassword());
            // Connection: this represents the connection to the database {setAutoCommit,createStatemetnt(),
            // prepareStatement(sql,key), prepareStatement(sql),commit(),rollback(),close()
            // for (ACID) transaction management:
            // setAutoCommit: each statement is once again committed automatically when it is completed. Back to
            // the default state where I don't have to call commit myself. Disable during transaction mode.
            connection.setAutoCommit(false);
            // executeUpdate(): used to execute statements such as insert, update, delete. It returns an
            // integer value representing the number of rows affected
            int updatedRows = employeeStatement.executeUpdate();
            // ResultSet: represents the results of the query {next(),getInt(columnName),getGeneratedKeys(),
            // executeQuery()
            //auto-generated id
            ResultSet employeeResults = employeeStatement.getGeneratedKeys();
            // next(): ResultSet cursor is initially positioned before the first row; the first call to the method
            // next makes the first row the current row. Moves cursor forward one row
            if (employeeResults.next()) {
            	System.out.println("Employee added!");
                 // set employeeId to the auto-generated key in first sql column
                 generatedId = employeeResults.getInt(1);
                 connection.commit();
            } else {
            	connection.rollback();
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
                return generatedId;
            
        }

    @Override
    public Employee getById(int id) {
        Employee engineer = null;
        Connection connection = connFactory.getConnection();
        Employee employed = null;
        String sql = "select * from employee left join department on employee.dept_id=department.dept_id" +
        			"where dept_id = ?";
        try {
            PreparedStatement employeeId = connection.prepareStatement(sql);
            employeeId.setInt(1, id);
            ResultSet results = employeeId.executeQuery();
            if (results.next()) {
            	engineer = new Employee();
            	engineer.setId(id);
            	String fullName = results.getString("full_name");
            	engineer.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
            	engineer.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
            	engineer.setUsername(results.getString("username"));
            	engineer.setPassword(results.getString("passwd"));
            	
            	RequestDAO reqDao = DAOFactory.getReqDao();
            	engineer.setRequests(reqDao.getByStatus(fullName)); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return engineer;
    }


    private Employee parseResultSets(ResultSet stacksSet) throws SQLException{
        Employee employed = new Employee();
        // do something with the return value:
        employed.setId(stacksSet.getInt(1));
        employed.setFirstName(stacksSet.getString(2));
        employed.setLastName((stacksSet.getString(3)));
        employed.setDeptId(stacksSet.getInt(4));
        employed.setRequestId(stacksSet.getInt(5));
        employed.setUsername(stacksSet.getString(6));
        employed.setPassword(stacksSet.getString(7));

        return employed;
    }

    @Override
    public void update(Employee updatedObj) {
        Connection connection = connFactory.getConnection();
        String sql = "update employee set pass_word=?, username=?, first_name=?, last_name=?, dept_id=?, " +
                "id=?, request_id=?";
        try {
            PreparedStatement updated = connection.prepareStatement(sql);
            updated.setString(1, updatedObj.getUsername());
            updated.setString(2, updatedObj.getPassword());
            updated.setString(3, updatedObj.getFirstName());
            updated.setString(4, updatedObj.getLastName());
            updated.setInt(5, updatedObj.getDeptId());
            updated.setInt(6, updatedObj.getRequestId());
            updated.setInt(7, updatedObj.getId());
            connection.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Employee objToDelete) {
        Connection connection = connFactory.getConnection();
		String sql = "delete from employee where id=?";
		try {
			PreparedStatement deletions = connection.prepareStatement(sql);
			deletions.setInt(1, objToDelete.getId());
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> engineers = new LinkedList<>();
		Connection conn = connFactory.getConnection();
		String sql = "select * from employee right join department on employee.dept_id=department.dept_id";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Employee engineer = new Employee();
				engineer.setDeptId(rs.getInt("dept_id"));
				String fullName = rs.getString("full_name");
				engineer.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
				engineer.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
				engineer.setUsername(rs.getString("username"));
				engineer.setPassword(rs.getString("passwd"));

				RequestDAO reqDao = DAOFactory.getReqDao();
				engineer.setRequests(reqDao.getByApprovedRequests(engineer));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return engineers;
	}

    @Override
    public Employee getByUsername(String username) {
        Employee employed = null;
        try (Connection conn = connFactory.getConnection()) {
            String sql = "select * from employee where username =?";
            PreparedStatement stated = conn.prepareStatement(sql);
            stated.setString(1, username);
            ResultSet results = stated.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getByPassword(String password) {
        Employee employed = null;
        try (Connection conn = connFactory.getConnection()) {
            String sql = "select * from employee where pass_word = ?";
            PreparedStatement stated = conn.prepareStatement(sql);
            stated.setString(1, password);
            ResultSet results = stated.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employed;
    }

    @Override
    public Employee getByFirstName(String firstName) {
        Employee employed = null;
        try (Connection conn = connFactory.getConnection()) {
            String sql = "select * from employee where first_Name = ?";
            PreparedStatement stated = conn.prepareStatement(sql);
            stated.setString(1, firstName);
            ResultSet results = stated.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employed;
    }

    @Override
    public Employee getByLastName(String lastName) {
        Employee employed = null;
        try (Connection conn = connFactory.getConnection()) {
            String sql = "select * from employee where last_Name = ?";
            PreparedStatement stated = conn.prepareStatement(sql);
            stated.setString(1, lastName);
            ResultSet results = stated.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employed;
    }

    @Override
    public List<Employee> requestId(int requestId) {
        Employee employed = null;
        try (Connection conn = connFactory.getConnection()) {
            String sql = "select * from employee where request_id = ?";
            PreparedStatement stated = conn.prepareStatement(sql);
            stated.setInt(1, requestId);
            ResultSet results = stated.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (List<Employee>) employed;
    }



	@Override
	public void updateRequests(int requestId, int employeeId) throws SQLException {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "insert into employee (request_id, dept_id) values (?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,  requestId);
			stmt.setInt(2, employeeId);
			conn.setAutoCommit(false);
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 1) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

}
