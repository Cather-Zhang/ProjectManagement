package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;


import sophex.http.task.AssignTeammateRequest;
import sophex.http.task.AssignTeammateResponse;


public class AssignTeammateHandler implements RequestHandler<AssignTeammateRequest, AssignTeammateResponse> {

	LambdaLogger logger;
	//TODO need teammate-task DAO
	public boolean assignTeammate(String name, String project) throws Exception { 
		return true;
	}
	
	@Override
	public AssignTeammateResponse handleRequest(AssignTeammateRequest req, Context context) {
		return null;
	}

}
