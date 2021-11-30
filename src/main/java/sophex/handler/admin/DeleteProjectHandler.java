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
		String failMessage = "";
		DeleteProjectResponse response;
		try {
			ProjectsDAO dao = new ProjectsDAO();
			boolean fail = dao.deleteProject(req.getProjectName());
			Project project = dao.getProjectUser(req.getProjectName());
			if (fail) {
				response = new DeleteProjectResponse(failMessage,400); //fail
			} else {
					response = new DeleteProjectResponse();  // success
				}
			} catch (Exception e) {
				response = new DeleteProjectResponse("Unable to delete project: " + req.getProjectName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
		}
	}
