package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.db.TeammatesDAO;
import sophex.http.admin.ArchiveProjectResponse;
import sophex.http.project.AddTeammateRequest;
import sophex.http.project.AddTeammateResponse;
import sophex.model.Project;

public class AddTeammateHandler implements RequestHandler<AddTeammateRequest, AddTeammateResponse>{

	@Override
	public AddTeammateResponse handleRequest(AddTeammateRequest req, Context context) {
		String failMessage = "";
		AddTeammateResponse response;
		try {
			TeammatesDAO tdao = new TeammatesDAO();
			ProjectsDAO pdao = new ProjectsDAO();
			boolean fail = tdao.addTeammate(req.getTeammateName(), req.getProjectName());	
			Project project = pdao.getProjectUser(req.getProjectName());
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

