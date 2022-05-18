package net.revature.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import net.revature.beans.Employee;
import net.revature.beans.Request;
import net.revature.exceptions.NeedGradeException;
import net.revature.services.EmployeeServiceImpl;
import net.revature.services.EmployeeServices;

public class RequestController {
	private static EmployeeServices empServed = new EmployeeServiceImpl();
	
	// GET to /requests
		public static void getRequests(Context ctx) {
			ctx.json(empServed.viewSubmittedRequest());
		}
		
		// GET to /requests/{id} where {id} is the ID of the request
		public static void getRequestById(Context ctx) {
			int id = Integer.parseInt(ctx.pathParam("id"));
			Request req = empServed.getRequestById(id);
			if (req != null)
				ctx.json(req);
			else
				ctx.status(HttpCode.NOT_FOUND); // 404 not found
		}
		
		public static void gradeRequests(Context ctx) {
			int reqId = Integer.parseInt(ctx.pathParam("id"));
			Request reqToGrade = empServed.getRequestById(reqId);
			
			Employee engineer = ctx.bodyAsClass(Employee.class);
			
			try {
				engineer = empServed.gradeRequests(engineer, reqToGrade);
				
				ctx.json(engineer);
			} catch (NeedGradeException e) {
				ctx.status(HttpCode.CONFLICT); // 409 conflict
			} catch (Exception e) {
				ctx.status(HttpCode.BAD_REQUEST); // 400 bad request
			}
		}
	}
