
package DAOs;

import Beans.Department;

public interface DepartmentDAO extends GenericDAO<Department>{
    public Department getByDeptName(String deptName);
    public Department getByDeptHead(String deptHead);
}
