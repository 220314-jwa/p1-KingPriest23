package DAOs;

import Beans.Employee;

import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee>{
    // set the generic's type here since its inherited
    // set to Employee
    public Employee getById(int Id);
    public Employee getByUsername(String username);
    public Employee getByPassword(String password);
    public Employee getByFirstName(String firstName);
    public Employee getByLastName(String lastName);
    public List<Employee> managerId(int managerId);
    public Employee getByStatus(String status);

    public List<Employee> getAll(Employee engineer);
}

