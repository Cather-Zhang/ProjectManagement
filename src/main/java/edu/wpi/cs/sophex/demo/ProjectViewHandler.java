package edu.wpi.cs.sophex.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.sophex.demo.http.CreateProjectRequest;
import edu.wpi.cs.sophex.demo.http.CreateProjectResponse;
import edu.wpi.cs.sophex.demo.model.Project;

public class ProjectViewHandler {
LambdaLogger logger;
	

	public CreateProjectResponse handleResponse(String projectName) throws Exception {
		boolean fail = false;
		String failMessage = "";
		if(loadProjectUserFromRDS(projectName) == null) {
			failMessage = projectName + " does not exist.";
			fail = true;
		}

		CreateProjectResponse response;
		if (fail) {
			response = new CreateProjectResponse(400, failMessage);
		} else {
			response = new CreateProjectResponse(projectName, 200);  // success
		}

		return response; 
	}
	public Project loadProjectUserFromRDS(String projectName) throws Exception {
		ProjectsDAO dao = new ProjectsDAO();
		Project p = dao.getProjectUser(projectName);
		return p;
	}
}
