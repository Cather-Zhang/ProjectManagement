package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.db.TeammatesDAO;
import sophex.http.project.AddTeammateRequest;
import sophex.http.project.AddTeammateResponse;
import sophex.http.project.RemoveTeammateRequest;
import sophex.http.project.RemoveTeammateResponse;
import sophex.model.Project;

public class RemoveTeammateHandler implements RequestHandler<RemoveTeammateRequest, RemoveTeammateResponse>{

	@Override
	public RemoveTeammateResponse handleRequest(RemoveTeammateRequest req, Context context) {
			String failMessage = "";
			RemoveTeammateResponse response;
			try {
				TeammatesDAO tdao = new TeammatesDAO();
				boolean success = tdao.removeTeammate(req.getTeammateName(), req.getProjectName());	
				if (!success) {
					response = new RemoveTeammateResponse(failMessage,400); //fail
				} else {
						response = new RemoveTeammateResponse();  // success
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					response = new RemoveTeammateResponse("Unable to remove teammate: " + req.getTeammateName() + "(" + e.getMessage() + ")",400);
				}
				return response; 
		}
}

