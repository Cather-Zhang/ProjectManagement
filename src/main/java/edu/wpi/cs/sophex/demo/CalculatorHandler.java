package edu.wpi.cs.sophex.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;

import edu.wpi.cs.sophex.demo.http.AddRequest;
import edu.wpi.cs.sophex.demo.http.AddResponse;


//brianna is on the project now
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
 * @author sophex
 */
public class CalculatorHandler implements RequestHandler<AddRequest,AddResponse> {

	LambdaLogger logger;
	
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "constants";
	public static final String TEST_BUCKET = "testconstants";

	@Override
	public AddResponse handleRequest(AddRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		boolean fail = false;
		String failMessage = "";
		double val1 = 0.0;
		try {
			val1 = Double.parseDouble(req.getArg1());
		} catch (NumberFormatException e) {
				failMessage = req.getArg1() + " is an invalid constant.";
				fail = true;
		}

		double val2 = 0.0;
		try {
			val2 = Double.parseDouble(req.getArg2());
		} catch (NumberFormatException e) {		
				failMessage = req.getArg2() + " is an invalid constant.";
				fail = true;
		}

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		AddResponse response;
		if (fail) {
			response = new AddResponse(400, failMessage);
		} else {
			response = new AddResponse(val1 + val2, 200);  // success
		}

		return response; 
	}
}


