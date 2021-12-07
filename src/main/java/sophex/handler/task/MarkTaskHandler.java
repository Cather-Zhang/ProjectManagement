package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.TasksDAO;
import sophex.http.task.MarkTaskRequest;
import sophex.http.task.MarkTaskResponse;
import sophex.http.task.RenameTaskResponse;

public class MarkTaskHandler implements RequestHandler<MarkTaskRequest, MarkTaskResponse>{

LambdaLogger logger;
	
	public boolean markTask(String projectName, String taskPrefix) throws Exception { 
		if (logger != null) { logger.log("in marking task"); }
		TasksDAO dao = new TasksDAO();
		return dao.markTask(projectName, taskPrefix);
	}
	
	@Override
	public MarkTaskResponse handleRequest(MarkTaskRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		MarkTaskResponse response;
		
		try {
			if (markTask(req.getProjectName(), req.getTaskPrefix())) {
				response = new MarkTaskResponse();
			} else {
				response = new MarkTaskResponse(("Task: " + req.getTaskPrefix() + " can't be marked"), 422);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new MarkTaskResponse("Unable to mark task to: " + "(" + e.getMessage() + ")", 400);
		}

		return response; 
	}


}
