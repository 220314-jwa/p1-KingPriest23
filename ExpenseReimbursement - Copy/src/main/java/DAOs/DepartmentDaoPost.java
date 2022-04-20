
package DAOs;

import Beans.Department;
import Utilities.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoPost implements DepartmentDAO{
    public DepartmentDaoPost() {}

    // connection object, used to connect to the database
    private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();
    // constructor, retrieve/get a connection from the connection factory
    // call the method made in ConnectionFactory

   

    @Override
    // this method needs to insert the object into the database
    // need to connect to the database
    public int create(Department engineer){
        Connection connection = connFactory.getConnection();

        // stores sql command that normally goes in DBeaver
        String sql = "insert into Department (dept_id, deptHead, deptName)" +
                "values (default, ?, ?)";
        // define before try/catch, so it doesn't error below
        PreparedStatement stated;

        try {
            // create a prepared statement (pre-compiled SQL statement) to pass in sql command
            // pass in flag "RETURN_GENERATED_KEYS" so id is generated
            // provides a way to retrieve values from columns that are part of an index
            // or have default value assigned
            stated = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // set the fields
            stated.setInt(1, engineer.getDeptId());
            stated.setString(1, engineer.getDeptHead());
            stated.setString(2, engineer.getDeptName());

            // setAutoCommit(false) allows you to group multiple subsequent Statements
            // under the same transaction.
            connection.setAutoCommit(false); // ACID
            // execute this command, return number of rows affected:
            int affectedRows = stated.executeUpdate();
            // Return id that is auto-generated
            // ResultSet is a Java object that contains the results of executing an SQL query
            ResultSet auto = stated.getGeneratedKeys();
            // pointer returns next value
            if (affectedRows > 0) {
            	System.out.println("Department added!");
            	auto.next();
            	int id = auto.getByte(affectedRows);
            	return id;
            } else {
            	System.out.println("Something went wrong when trying to add a department.");
            	connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	connection.rollback();
            }catch (SQLException e1) {
            	e1.printStackTrace();
            }finally {
            	try {
            		connection.close();
            	}catch (SQLException e2) {
            		e.printStackTrace();
            	}
            }
        } 
        return engineer.getDeptId();
       
    }

    @Override
    public Department getByDeptName(String deptName) {
        // initialize department object to be null;
        Department dept = null;
        String sql = "SELECT * FROM department WHERE deptName";

        try (Connection connection = connFactory.getConnectionFactory().getConnection()) {
            // create a prepared statement (pre-compiled SQL statement) to pass in sql command
            PreparedStatement stated = connection.prepareStatement(sql);
            stated.setString(1, deptName);
            // execute this command, return number of rows affected:
            ResultSet results = stated.executeQuery();
            // if results doesn't point to next value, something is wrong
            if (results.next()) {
                dept = parseResultSet(results);
                // created a dept object based on the info from table
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return dept;
    }

    @Override
    public List<Department> getAll() {
        // initialize empty Dept list:
        List<Department> dept = new ArrayList<Department>();
        String sql = "SELECT * FROM department";
        try (Connection connection = connFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            // get the result from query
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                Department depts = parseResultSet(results);
                dept.add(depts);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return dept;
    }

    private Department parseResultSet(ResultSet results) throws SQLException {
    	Department dept = new Department();
    	dept.setDeptId(results.getInt(1));
    	dept.setDeptHead(results.getString(2));
    	dept.setDeptName(results.getString(3));
    	
        return (Department) results;
    }


    @Override
    public void update(Department updatedObj) {
        Connection connection = connFactory.getConnection();
        String updatedDept = "update department set deptName = ?, deptHead = ?, deptId = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updatedDept);
            preparedStatement.setString(1, updatedObj.getDeptName());
            preparedStatement.setString(2, updatedObj.getDeptHead());
            preparedStatement.setInt(3, updatedObj.getDeptId());
            connection.setAutoCommit(false);
            // return a count of how many records were updated
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated == 1) {
            	connection.commit();
            } else {
            	connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void delete(Department objToDelete) {
        Connection connection = connFactory.getConnection();
        String sql = "delete from department where deptId = ?;";
        try{
            PreparedStatement stated = connection.prepareStatement(sql);
            stated.setInt(1, objToDelete.getDeptId());
            connection.setAutoCommit(false); // for ACID (transaction management)
            int rowsUpdated = stated.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    @Override
    public Department getById(int id) {
        Department dept = null;
        Connection conn = connFactory.getConnection();
        String sql = "select * from department inner join employee on department.deptId=employee.deptId"
        		+ " where dept.id = ?";
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	stmt.setInt(1, id);
        	ResultSet rs = stmt.executeQuery();
        	if(rs.next()) {
        	 dept = parseResultSet(rs);
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return dept;
    }
    @Override
    public Department getByDeptHead(String deptHead) {
        Department depts = null;
        try (Connection conn = connFactory.getConnection()) {
        String sql = "select * from department where dept_head";
        PreparedStatement stated = conn.prepareStatement(sql);
        stated.setString(1, deptHead);
        ResultSet results = stated.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return depts;
    }




}
