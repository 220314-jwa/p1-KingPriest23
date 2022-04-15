package Services;

import Beans.Department;

public interface DepartmentServices {

	public Department getDeptId(int deptId);
	
	public Department getDeptName(String deptName);
	
	public Department getDeptHead(String deptHead);
}
