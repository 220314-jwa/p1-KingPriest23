package net.revature.controller;

import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import net.revature.beans.Employee;
import net.revature.exceptions.IncorrectCredentialsException;
import net.revature.exceptions.UsernameAlreadyExistsException;
import net.revature.services.EmployeeServiceImpl;
import net.revature.services.EmployeeServices;

public class EmployeeController {
	private static EmployeeServices empServed = new EmployeeServiceImpl();

	// POST to /users
	public static void registerEmployee(Context ctx) {
		Employee newEngineer = ctx.bodyAsClass(Employee.class);
		
		try {
			newEngineer = empServed.register(newEngineer);
			ctx.json(newEngineer);
		} catch (UsernameAlreadyExistsException e) {
			ctx.status(HttpCode.CONFLICT); // 409 conflict
		}
	}
	
	// POST to /auth
	public static void logIn(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		String username = credentials.get("username");
		String password = credentials.get("password");
		
		try {
			Employee engineer = empServed.logIn(username, password);
			ctx.json(engineer);
		} catch (IncorrectCredentialsException e) {
			ctx.status(HttpCode.UNAUTHORIZED); // 401 unauthorized
		}
	}
	
	// GET to /users/{id} where {id} is the user's id
	public static void getEmployeeById(Context ctx) {
		String pathParam = ctx.pathParam("id");
		if (pathParam != null && !pathParam.equals("undefined") && !pathParam.equals("null")) {
			int engineerId = Integer.parseInt(pathParam);
			
			Employee engineer = empServed.getEmployeeById(engineerId); 
			if (engineer != null)
				ctx.json(engineer);
			else
				ctx.status(HttpCode.NOT_FOUND); // 404 not found
		} else {
			ctx.status(HttpCode.BAD_REQUEST); // 400 bad request
		}
	}
}


