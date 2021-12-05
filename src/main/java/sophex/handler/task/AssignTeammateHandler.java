package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.TasksTeammatesDAO;
import sophex.http.task.AddTaskResponse;
import sophex.http.task.AssignTeammateRequest;
import sophex.http.task.AssignTeammateResponse;


public class AssignTeammateHandler implements RequestHandler<AssignTeammateRequest, AssignTeammateResponse> {

	LambdaLogger logger;

	public boolean assignTeammate(String teammateName, String projectName, String taskPrefix) throws Exception { 
		if (logger != null) {logger.log("in assign teammate");}
		TasksTeammatesDAO dao = new TasksTeammatesDAO();
		return dao.assignTeammate(teammateName, projectName, taskPrefix);
	}
	
	@Override
	public AssignTeammateResponse handleRequest(AssignTeammateRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());
		
		AssignTeammateResponse response;
		
		try {
			if (assignTeammate(req.getTeammateName(), req.getProjectName(), req.getTaskPrefix())) {
				response = new AssignTeammateResponse();
			} else {
				response = new AssignTeammateResponse("Teammate " + req.getTeammateName() + " can not be assigned to task " + req.getTaskPrefix() + " in " + req.getProjectName(), 422);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new AssignTeammateResponse("Unable to assign teammate: " + req.getTeammateName() + "(" + e.getMessage() + ")", 400);
		}
		
		return response;
	}

}
