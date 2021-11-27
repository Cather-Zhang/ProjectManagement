package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.admin.ArchiveProjectResponse;
import sophex.http.project.AddTeammateRequest;
import sophex.http.project.AddTeammateResponse;
import sophex.model.Project;

public class AddTeammateHandler implements RequestHandler<AddTeammateRequest, AddTeammateResponse>{

	@Override
	public AddTeammateResponse handleRequest(AddTeammateRequest req, Context context) {
		boolean fail = false;
		String failMessage = "";
		AddTeammateResponse response;
		try {
			Project project = loadProjectUserFromRDS(req.getProjectName());
			if(project == null) {
				failMessage = req.getProjectName() + " does not exist.";
				fail = true;
			}		
			if (fail) {
				response = new AddTeammateResponse(failMessage,400); //fail
			} else {
					response = new AddTeammateResponse(project, req.getTeammateName());  // success
				}
			} catch (Exception e) {
				response = new AddTeammateResponse("Unable to add teammate: " + req.getProjectName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
		}
			
		public Project loadProjectUserFromRDS(String projectName) throws Exception {
			ProjectsDAO dao = new ProjectsDAO();
			Project p = dao.getProjectUser(projectName);
			return p;
		}
	}

