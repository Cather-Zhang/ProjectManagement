package edu.wpi.cs.sophex.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs.sophex.demo.http.CreateProjectRequest;
import edu.wpi.cs.sophex.demo.http.CreateProjectResponse;
import edu.wpi.cs.sophex.demo.model.Project;



/**
 * Final version of calculator.
 * 
 * If using just double values as strings, then returns the result.
 * If any of the strings do not parse as a number, they are searched for as a constant.
 * First we search the RDS database.
 * Second, we attempt to load up from S3 bucket.
 * 
 * Note: I have stopped using com.fasterxml.jackson.databind.JsonNode and instead use two different
 * JSon packages. SimpleJson is just that -- Simple!. GSon is a google package that is quite useful
 * 
 * @author Cather
 */
public class CreateProjectHandler implements RequestHandler<CreateProjectRequest,CreateProjectResponse> {

	LambdaLogger logger;
	

	@Override
	public CreateProjectResponse handleRequest(CreateProjectRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		boolean fail = false;
		String failMessage = "";
		Project p = null;
		try {
			 p = new Project(req.getArg1());
		} catch (NumberFormatException e) {
				failMessage = req.getArg1() + " is an invalid constant.";
				fail = true;
		}


		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		CreateProjectResponse response;
		if (fail) {
			response = new CreateProjectResponse(400, failMessage);
		} else {
			response = new CreateProjectResponse(p.getname(), 200);  // success
		}

		return response; 
	}
}


