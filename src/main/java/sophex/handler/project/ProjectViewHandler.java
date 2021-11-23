package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.project.ProjectViewRequest;
import sophex.http.project.ProjectViewResponse;
import sophex.model.Project;

public class ProjectViewHandler implements RequestHandler<ProjectViewRequest,ProjectViewResponse>{
LambdaLogger logger;
	
    @Override
	public ProjectViewResponse handleRequest(ProjectViewRequest req, Context context) {
		boolean fail = false;
		String failMessage = "";
		ProjectViewResponse response;
		try {
			if(loadProjectUserFromRDS(req.getName()) == null) {
				failMessage = req.getName() + " does not exist.";
				fail = true;
			}		
			if (fail) {
				response = new ProjectViewResponse(400, failMessage); //fail
			} else {
				response = new ProjectViewResponse(loadProjectUserFromRDS(req.getName()));  // success
			}
		} catch (Exception e) {
			response = new ProjectViewResponse(400, "Unable to view project: " + req.getName() + "(" + e.getMessage() + ")");
		}
		return response; 
	}
	
	public Project loadProjectUserFromRDS(String projectName) throws Exception {
		ProjectsDAO dao = new ProjectsDAO();
		Project p = dao.getProjectUser(projectName);
		return p;
	}

}
