package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.TasksDAO;
import sophex.http.task.RenameTaskRequest;
import sophex.http.task.RenameTaskResponse;

public class RenameTaskHandler implements RequestHandler<RenameTaskRequest, RenameTaskResponse>{

	LambdaLogger logger;
	
	public boolean renameTask(String newTaskName, String projectName, String taskPrefix) throws Exception { 
		if (logger != null) { logger.log("in rename task"); }
		TasksDAO dao = new TasksDAO();
		return dao.renameTask(newTaskName, projectName, taskPrefix);
	}
	
	@Override
	public RenameTaskResponse handleRequest(RenameTaskRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		RenameTaskResponse response;
		
		try {
			if (renameTask(req.getNewTaskName(), req.getProjectName(), req.getTaskPrefix())) {
				response = new RenameTaskResponse();
			} else {
				response = new RenameTaskResponse(("Task: " + req.getTaskPrefix() + " can't be renamed"), 422);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new RenameTaskResponse("Unable to rename task to: " + req.getNewTaskName() + "(" + e.getMessage() + ")", 400);
		}

		return response; 
	
	}

}
