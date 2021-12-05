package sophex.handler.admin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.admin.ArchiveProjectRequest;
import sophex.http.admin.ArchiveProjectResponse;
import sophex.http.admin.DeleteProjectRequest;
import sophex.http.admin.DeleteProjectResponse;
import sophex.model.Project;

public class ArchiveProjectHandler implements RequestHandler<ArchiveProjectRequest, ArchiveProjectResponse>{

	@Override
	public ArchiveProjectResponse handleRequest(ArchiveProjectRequest req, Context context) {
		String failMessage = "";
		ArchiveProjectResponse response;
		try {
			ProjectsDAO dao = new ProjectsDAO();
			boolean success = dao.archiveProject(req.getProjectName());
			if (!success) {
				response = new ArchiveProjectResponse(failMessage,400); //fail
			} else {
					response = new ArchiveProjectResponse();  // success
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				response = new ArchiveProjectResponse("Unable to delete project: " + req.getProjectName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
			}
		}

