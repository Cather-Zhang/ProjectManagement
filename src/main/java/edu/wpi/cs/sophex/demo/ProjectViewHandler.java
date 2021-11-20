package edu.wpi.cs.sophex.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.sophex.demo.db.ProjectsDAO;
import edu.wpi.cs.sophex.demo.http.CreateProjectRequest;
import edu.wpi.cs.sophex.demo.http.CreateProjectResponse;
import edu.wpi.cs.sophex.demo.http.ProjectViewResponse;
import edu.wpi.cs.sophex.demo.model.Project;

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
