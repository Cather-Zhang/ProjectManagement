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
			boolean success = dao.deleteProject(req.getProjectName());
			if (!success) {
				response = new DeleteProjectResponse(failMessage,400); //fail
			} else {
					response = new DeleteProjectResponse();  // success
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				response = new DeleteProjectResponse("Unable to delete project: " + req.getProjectName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
		}
	}
