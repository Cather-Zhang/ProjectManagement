package sophex.handler.admin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.admin.DeleteProjectRequest;
import sophex.http.admin.DeleteProjectResponse;
import sophex.model.Project;

public class DeleteProjectHandler implements RequestHandler<DeleteProjectRequest, DeleteProjectResponse>{

	@Override
	public DeleteProjectResponse handleRequest(DeleteProjectRequest req, Context context) {
		boolean fail = false;
		String failMessage = "";
		DeleteProjectResponse response;
		try {
			if(loadProjectUserFromRDS(req.getProjectName()) == null) {
				failMessage = req.getProjectName() + " does not exist.";
				fail = true;
			}		
			if (fail) {
				response = new DeleteProjectResponse(failMessage,400); //fail
			} else {
					response = new DeleteProjectResponse(loadProjectUserFromRDS(req.getProjectName()));  // success
				}
			} catch (Exception e) {
				response = new DeleteProjectResponse("Unable to delete project: " + req.getProjectName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
		}
			
		public Project loadProjectUserFromRDS(String projectName) throws Exception {
			ProjectsDAO dao = new ProjectsDAO();
			Project p = dao.getProjectUser(projectName);
			return p;
		}
	}
