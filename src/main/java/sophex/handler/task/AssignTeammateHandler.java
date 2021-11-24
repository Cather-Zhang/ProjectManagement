package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.db.TeammatesDAO;
import sophex.http.project.CreateProjectResponse;
import sophex.http.task.AssignTeammateRequest;
import sophex.http.task.AssignTeammateResponse;
import sophex.model.Project;
import sophex.model.Teammate;

public class AssignTeammateHandler implements RequestHandler<AssignTeammateRequest, AssignTeammateResponse> {

	LambdaLogger logger;
	//TODO need teammate-task DAO
	public boolean assignTeammate(String name, String project) throws Exception { 
		if (logger != null) { logger.log("in get Teammate"); }
		TeammatesDAO tdao = new TeammatesDAO();
		
		// check if teammate exists present
		Teammate exist = tdao.getTeammate(project, name);
		if (exist != null) {
			return tdao.addTeammate(exist);
		} else {
			return false;
		}
	}
	
	@Override
	public AssignTeammateResponse handleRequest(AssignTeammateRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());
		
		AssignTeammateResponse response;
		
		try {
			if (addTeammate(req.getName())) {
				ProjectsDAO dao = new ProjectsDAO();
				Project p = dao.getTeammate(req.getName());
				response = new AssignTeammateResponse(p);
			} else {
				response = new AssignTeammateResponse(("Project " + req.getName() + " already exists"), 422);
			}
		} catch (Exception e) {
			response = new AssignTeammateResponse("Unable to create project: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response; 
	}

}
