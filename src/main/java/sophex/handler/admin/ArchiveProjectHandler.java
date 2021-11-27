package sophex.handler.admin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.admin.ArchiveProjectRequest;
import sophex.http.admin.ArchiveProjectResponse;
import sophex.model.Project;

public class ArchiveProjectHandler implements RequestHandler<ArchiveProjectRequest, ArchiveProjectResponse>{

	@Override
	public ArchiveProjectResponse handleRequest(ArchiveProjectRequest req, Context context) {
		boolean fail = false;
		String failMessage = "";
		ArchiveProjectResponse response;
		try {
			Project project = loadProjectUserFromRDS(req.getProjectName());
			if(project == null) {
				failMessage = req.getProjectName() + " does not exist.";
				fail = true;
			}		
			if (fail) {
				response = new ArchiveProjectResponse(failMessage,400); //fail
			} else {
					response = new ArchiveProjectResponse(project);  // success
				}
			} catch (Exception e) {
				response = new ArchiveProjectResponse("Unable to view project: " + req.getProjectName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
		}
			
		public Project loadProjectUserFromRDS(String projectName) throws Exception {
			ProjectsDAO dao = new ProjectsDAO();
			Project p = dao.getProjectUser(projectName);
			return p;
		}
	}

