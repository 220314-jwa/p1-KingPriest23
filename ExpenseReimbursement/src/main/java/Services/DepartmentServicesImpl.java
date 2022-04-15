package Services;

import Beans.Department;
import Beans.Employee;
import DAOs.DepartmentDAO;

public class DepartmentServicesImpl implements DepartmentServices {
	private static DepartmentDAO deptDao;
	private static Employee empDao;
	
	@Override
	public Department getDeptId(int deptId) {
		Department dept = deptDao.getByDeptId(deptId);
		
		
		return dept;
	}

	@Override
	public Department getDeptName(String deptName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Department getDeptHead(String deptHead) {
		// TODO Auto-generated method stub
		return null;
	}

}
