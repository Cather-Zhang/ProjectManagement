package edu.wpi.cs.sophex.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.annotation.JsonCreator;

import edu.wpi.cs.sophex.demo.http.TestRequest;
import edu.wpi.cs.sophex.demo.http.TestResponse;

public class TestJsonHandler implements RequestHandler<TestRequest,TestResponse> {

	LambdaLogger logger;

    private Object Json;

	public static final String REAL_BUCKET = "constants";
	public static final String TEST_BUCKET = "testconstants";

	@Override
	public TestResponse handleRequest(TestRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		boolean fail = false;
		String failMessage = "";

        String projectName = "";
        try {
            projectName = req.getProjectName();
        } catch (Exception e) {
            failMessage = req.getProjectName() + " is invalid";
            fail = true;
        }
        
        Json = 
        "["+
        "   \"projectName\" : " + projectName + "\""+
        "]";

		TestResponse response;
		if (fail) {
			response = new TestResponse(400, failMessage);
		} else {
			response = new TestResponse(projectName, 200);  // success
		}

		return response; 
	}
}


