package Controller;

import Beans.Employee;
import Beans.Request;
import Exceptions.NeedGradeException;
import Services.EmployeeServiceImpl;
import Services.EmployeeServices;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class RequestController {
	private static EmployeeServices empServed = new EmployeeServiceImpl();
	
	// GET to /pets
		public static void getRequests(Context ctx) {
			ctx.json(empServed.viewSubmittedRequest());
		}
		
		// GET to /pets/{id} where {id} is the ID of the pet
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
