
package DAOs;

import Beans.Employee;
import Utilities.ConnectionFactory;

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
        // Allows connection to database
        Connection connection = connFactory.getConnection();
        // store sql command, DML, placeholders
        String sql = "insert into Employee (id, first_name, last_name, manager_id, dept_id, status)" +
                "values (default, ?, ?, ?, ?, ?)";
        // try catch statement: responsible for exception handling.
        try {
            // create a prepared statement (pre-compiled SQL statement) to pass in sql command
            // pass in flag "RETURN_GENERATED_KEYS" so id is generated
            // preparedStatement: provides a way to retrieve values from columns that are part of an index
            // or have default value assigned
            PreparedStatement employeeStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            employeeStatement.setInt(1, engineer.getId());
            employeeStatement.setInt(2, engineer.getDeptId());
            employeeStatement.setInt(3, engineer.getManagerId());
            employeeStatement.setString(4, engineer.getFirstName());
            employeeStatement.setString(5, engineer.getLastName());
            employeeStatement.setString(6, engineer.getUsername());
            employeeStatement.setString(7, engineer.getPassword());
            employeeStatement.setString(8, engineer.getStatus());
            // Connection: this represents the connection to the database {setAutoCommit,createStatemetnt(),
            // prepareStatement(sql,key), prepareStatement(sql),commit(),rollback(),close()
            // for (ACID) transaction management:
            // setAutoCommit: each statement is once again committed automatically when it is completed. Back to
            // the default state where I don't have to call commit myself. Disable during transaction mode.
            connection.setAutoCommit(false);
            // executeUpdate(): used to execute statements such as insert, update, delete. It returns an
            // integer value representing the number of rows affected
            employeeStatement.executeUpdate();
            // ResultSet: represents the results of the query {next(),getInt(columnName),getGeneratedKeys(),
            // executeQuery()
            //auto-generated id
            ResultSet employeeResults = employeeStatement.getGeneratedKeys();
            // next(): ResultSet cursor is initially positioned before the first row; the first call to the method
            // next makes the first row the current row. Moves cursor forward one row
            employeeResults.next();
            // set employeeId to the auto-generated key in first sql column
            int id = employeeResults.getInt(1));
            return id;
            // commit(0 method of the Connection interface saves all the modifications made since last commit
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Employee getById() {
        int id = 0;
        Connection connection = connFactory.getConnection();
        Employee employed = null;
        String sql = "select * from employee where id = ?";
        try {
            PreparedStatement employeeId = connection.prepareStatement(sql);
            employeeId.setInt(1, id);
            ResultSet results = employeeId.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employed;
    }

  

    @Override
    public List<Employee> getAll(Employee engineer) {
        List<Employee> workers = new LinkedList<>();
        try (Connection connected = connFactory.getConnection()) {
            // DQL
            String sql = "Select * from Employee join employee on dept.id=department.dept_id" +
                    " where deparment.dept_id=?";
            PreparedStatement stacks = connected.prepareStatement(sql);
            stacks.setInt(1, engineer.getDeptId());
            ResultSet stacksSet = stacks.executeQuery();
            while (stacksSet.next()) {
                Employee employed = parseResultSets(stacksSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }

    private Employee parseResultSets(ResultSet stacksSet) throws SQLException{
        Employee employed = new Employee();
        // do something with the return value:
        employed.setId(stacksSet.getInt(1));
        employed.setFirstName(stacksSet.getString(2));
        employed.setLastName((stacksSet.getString(3)));
        employed.setDeptId(stacksSet.getInt(4));
        employed.setManagerId(stacksSet.getInt(5));
        employed.setUsername(stacksSet.getString(6));
        employed.setPassword(stacksSet.getString(7));

        return employed;
    }

    @Override
    public void update(Employee updatedObj) {
        Connection connection = connFactory.getConnection();
        String sql = "update employee set pass_word=?, username=?, first_name=?, last_name=?, dept_id=?, " +
                "id=?, manager_id=?";
        try {
            PreparedStatement updated = connection.prepareStatement(sql);
            updated.setString(1, updatedObj.getUsername());
            updated.setString(2, updatedObj.getPassword());
            updated.setString(3, updatedObj.getFirstName());
            updated.setString(4, updatedObj.getLastName());
            updated.setInt(5, updatedObj.getDeptId());
            updated.setInt(6, updatedObj.getManagerId());
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
        return null;
    }
    @Override
    public Employee getById(int Id) {
        Employee employed = null;
        String sql = "select * from employee where id=?";
        try (Connection connection = connFactory.getConnection()){
            PreparedStatement stated = connection.prepareStatement(sql);
            stated.setInt(1, Id);
            ResultSet results = stated.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public List<Employee> managerId(int managerId) {
        Employee employed = null;
        try (Connection conn = connFactory.getConnection()) {
            String sql = "select * from employee where manager_id = ?";
            PreparedStatement stated = conn.prepareStatement(sql);
            stated.setInt(1, managerId);
            ResultSet results = stated.executeQuery();
            results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (List<Employee>) employed;
    }

	@Override
	public Employee getByStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

}
