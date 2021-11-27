package sophex.handler.admin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.admin.DeleteProjectRequest;
import sophex.http.admin.DeleteProjectResponse;
import sophex.model.Project;
import java.util.ArrayList;
import java.util.List;

public class DeleteProjectHandler implements RequestHandler<DeleteProjectRequest, DeleteProjectResponse>{

	@Override
	public DeleteProjectResponse handleRequest(DeleteProjectRequest req, Context context) {
		boolean fail = false;
		String failMessage = "";
		DeleteProjectResponse response;
		try {
			Project project = loadProjectUserFromRDS(req.getProjectName());
			if(project == null) {
				failMessage = req.getProjectName() + " does not exist.";
				fail = true;
			}		
			if (fail) {
				response = new DeleteProjectResponse(failMessage,400); //fail
			} else {
					response = new DeleteProjectResponse(project, loadAllProjectsFromRDS());  // success
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
		public List<Project> loadAllProjectsFromRDS() throws Exception{
			ProjectsDAO dao = new ProjectsDAO();
			List<Project> list = dao.getProjectsAdmin();
			return list;
		}
	}
