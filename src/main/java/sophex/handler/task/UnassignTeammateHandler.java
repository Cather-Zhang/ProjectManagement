package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.TasksTeammatesDAO;
import sophex.http.task.UnassignTeammateRequest;
import sophex.http.task.UnassignTeammateResponse;

public class UnassignTeammateHandler implements RequestHandler<UnassignTeammateRequest, UnassignTeammateResponse>{
	LambdaLogger logger;

	public boolean unassignTeammate(String teammateName, String projectName, String taskPrefix) throws Exception { 
		if (logger != null) {logger.log("in assign teammate");}
		TasksTeammatesDAO dao = new TasksTeammatesDAO();
		return dao.unassignTeammate(teammateName, projectName, taskPrefix);
	}
	@Override
	public UnassignTeammateResponse handleRequest(UnassignTeammateRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());
		
	UnassignTeammateResponse response;
		
		try {
			if (unassignTeammate(req.getTeammateName(), req.getProjectName(), req.getTaskPrefix())) {
				response = new UnassignTeammateResponse();
			} else {
				response = new UnassignTeammateResponse("Teammate " + req.getTeammateName() + " can not be unassigned to task " + req.getTaskPrefix() + " in " + req.getProjectName(), 422);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new UnassignTeammateResponse("Unable to unassign teammate: " + req.getTeammateName() + "(" + e.getMessage() + ")", 400);
		}
		
		return response;
	}


}
