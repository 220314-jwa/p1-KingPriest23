
package DAOs;

//singleton is a design pattern where we only create one instance of a class,
//this class is responsible for instantiating/returning dao
public class DAOFactory {
 // initializes dept dao to be null
 // keep static instances of DAOs
 // saves memory
 private static DepartmentDAO deptDao = null;
 private static EmployeeDAO empDao = null;
 private static RequestDAO reqDao = null;

 // make our constructor private, so it can't be accessed publicly
 private DAOFactory() {}

 public static DepartmentDAO getDeptDao() {
     // make sure we're not recreating the dao if it already exists
     if (deptDao == null) {
         deptDao = new DepartmentDaoPost();
     }
     return deptDao;
 }
 public static EmployeeDAO getEmpDao() {
     if (empDao == null) {
         empDao = new EmployeeDaoPost();
     }
    return empDao;
 }
 public static RequestDAO getReqDao() {
	 if (reqDao == null) {
		 reqDao = new RequestDaoPost();
	 }
	 return reqDao;
 }
}