package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import sophex.db.ProjectsDAO;
import sophex.http.project.CreateProjectRequest;
import sophex.http.project.CreateProjectResponse;
import sophex.http.project.ProjectViewResponse;
import sophex.model.Project;

public class ProjectViewHandler {
LambdaLogger logger;
	

	public ProjectViewResponse handleResponse(String projectName) throws Exception {
		boolean fail = false;
		String failMessage = "";
		if(loadProjectUserFromRDS(projectName) == null) {
			failMessage = projectName + " does not exist.";
			fail = true;
		}

		ProjectViewResponse response;
		if (fail) {
			response = new ProjectViewResponse(400, failMessage); //fail
		} else {
			response = new ProjectViewResponse(projectName);  // success
		}

		return response; 
	}
	public Project loadProjectUserFromRDS(String projectName) throws Exception {
		ProjectsDAO dao = new ProjectsDAO();
		Project p = dao.getProjectUser(projectName);
		return p;
	}
}
