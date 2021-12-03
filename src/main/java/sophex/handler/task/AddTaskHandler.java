package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.TasksDAO;
import sophex.http.task.AddTaskRequest;
import sophex.http.task.AddTaskResponse;

/**
 * 
 * @author Cather Zhang
 *
 */
public class AddTaskHandler implements RequestHandler<AddTaskRequest, AddTaskResponse>{
	LambdaLogger logger;
	
	public boolean addTask(String taskName, String projectName, String parentPrefix) throws Exception { 
		if (logger != null) { logger.log("in add task"); }
		TasksDAO dao = new TasksDAO();
		return dao.addTask(taskName, projectName, parentPrefix);
	}
	
	@Override
	public AddTaskResponse handleRequest(AddTaskRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		AddTaskResponse response;
		
		try {
			if (addTask(req.getTaskName(), req.getProjectName(), req.getParentPrefix())) {
				response = new AddTaskResponse();
			} else {
				response = new AddTaskResponse(("Task: " + req.getTaskName() + " can't be added"), 422);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new AddTaskResponse("Unable to add Task: " + req.getTaskName() + "(" + e.getMessage() + ")", 400);
		}

		return response; 
	}


}
