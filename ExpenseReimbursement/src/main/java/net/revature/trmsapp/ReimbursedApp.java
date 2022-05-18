package net.revature.trmsapp;

import io.javalin.Javalin;
import net.revature.controller.EmployeeController;
import net.revature.controller.RequestController;
import net.revature.services.EmployeeServiceImpl;
import net.revature.services.EmployeeServices;

import static io.javalin.apibuilder.ApiBuilder.*; // this allows the routes methods (path, etc.)

public class ReimbursedApp {
	    // field
	    private static EmployeeServices servedEmployee = new EmployeeServiceImpl();

	    public static void main(String[] args) {
	    	
	        // create a javelin app, http handling, service, DAO, Database,
	        // database, dao, service, http response, html, user
	        // specify a http method and path, & when Javalin's web container receives the request that matches
	        // it will use the handler to respond
	        Javalin app = Javalin.create(config -> {
	        	config.enableCorsForAllOrigins();
	        });
	        //		app.get("/", ctx -> ctx.result("Hello World!"));
	        // post method, takes in a lambda function.
	        app.start();
	        // cleaning up the main method by switching to app.routes and moving logic to controllers
	        app.routes(() -> {
	        	// all paths starting with /requests
	        	path("requests", () -> {
	        		get(RequestController::getRequests);
	        		// /requests/4
	        		path("{id}", () -> {
	        			get(RequestController::getRequestById);
	        			// /request/4/grade
	        			put("grade", RequestController::gradeRequests);
	        		});
	        	});
	        	// all paths starting with /employees
	        	path("employees", () -> {
	        		post(EmployeeController::registerEmployee);
	        		path("{id}", () -> {
	        			get(EmployeeController::getEmployeeById);
	        		});
	        	});
	        	// all paths starting with /auth
	        	path("auth", () -> {
	        		post(EmployeeController::logIn);
	        	});
	        });
	        
	        
	        
	        
	    }
}      
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        

//	        app.post("/employee", ctx -> {
//	            // .json() is an alternative to .result() that
//	            //  sets the data type as JSON, the format sends/receives data in
//	            //  ctx.json(servEmp.findEmployeeById());
//	        	Employee employed = ctx.bodyAsClass(Beans.Employee.class);
//	        	System.out.println(employed);
//	        });
//	        app.post("/department", ctx -> {
//	        	Department dept = ctx.bodyAsClass(Beans.Department.class);
//	        	System.out.println(dept);
//	        });
//	        app.get("/department/{name}", ctx -> {
//	        	String name = ctx.header("Engineering");
//	        	System.out.println(name);
//	        });
//	        app.get("/employees/{firstName}", ctx -> {
//	        	//EmployeeDAO empDao = DAOFactory.getEmpDao();
//	        	String firstName = ctx.queryParam("Vincent");
//	        	System.out.println(firstName);
//	        	
//	        });
//	        app.put("/employee", ctx -> {
//	        	Employee employed = ctx.bodyAsClass(Beans.Employee.class);
//	        	EmployeeDAO empDao = DAOFactory.getEmpDao();
//	        	empDao.update(employed);
//	        });
//	        app.post("/auth", ctx -> {
//	        	// if the keys in JSON data don't exactly match a class,
//	        	// we can just use a Map
//	        	Map<String, String> credentials = ctx.bodyAsClass(Map.class);
//	        	String username = credentials.get("username");
//	        	String password = credentials.get("password");
//	        	try {
//	        		Employee engineer = servedEmployee.logIn(username, password);
//	        		ctx.json(engineer);
//	        	} catch (IncorrectCredentialsException e) {
//	        		ctx.status(HttpCode.UNAUTHORIZED); // 401 unauthorized
//	        	}
//	        });
//	        
//	        
//	        
//	        
	        

