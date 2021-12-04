package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.TasksDAO;
import sophex.http.task.DecomposeTaskRequest;
import sophex.http.task.DecomposeTaskResponse;
/**
 * 
 * @author Cather Zhang
 *
 */

public class DecomposeTaskHandler implements RequestHandler<DecomposeTaskRequest, DecomposeTaskResponse>{
	LambdaLogger logger;
	
	public boolean decomposeTask(String[] taskNames, String projectName, String parentPrefix) throws Exception { 
		if (logger != null) { logger.log("in decompose task"); }
		TasksDAO dao = new TasksDAO();
		return dao.decomposeTask(taskNames, projectName, parentPrefix);
	}
	@Override
	public DecomposeTaskResponse handleRequest(DecomposeTaskRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		DecomposeTaskResponse response;
		
		try {
			if (decomposeTask(req.getTasks(), req.getProjectName(), req.getParentPrefix())) {
				response = new DecomposeTaskResponse();
			} else {
				response = new DecomposeTaskResponse(("Task: " + req.getTasks() + " can't be added"), 422);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new DecomposeTaskResponse("Unable to add Task: " + req.getTasks() + "(" + e.getMessage() + ")", 400);
		}

		return response; 
	}
}

