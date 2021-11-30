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
			boolean fail = tdao.addTeammate(req.getTeammateName(), req.getProjectName());	
			if (fail) {
				response = new AddTeammateResponse(failMessage,400); //fail
			} else {
					response = new AddTeammateResponse();  // success
				}
			} catch (Exception e) {
				response = new AddTeammateResponse("Unable to add teammate: " + req.getTeammateName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
	}
}

