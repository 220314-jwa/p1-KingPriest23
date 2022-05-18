
package net.revature.daos;

//singleton is a design pattern where we only create one instance of a class,
//this class is responsible for instantiating/returning dao
public class DAOFactory {
 // initializes dept dao to be null
 // keep static instances of DAOs
 // saves memory
 private static EmployeeDAO empDao = null;
 private static RequestDAO reqDao = null;

 // make our constructor private, so it can't be accessed publicly
 private DAOFactory() {}

 
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