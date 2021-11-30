package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.TeammatesDAO;
import sophex.http.project.AddTeammateRequest;
import sophex.http.project.AddTeammateResponse;

public class AddTeammateHandler implements RequestHandler<AddTeammateRequest, AddTeammateResponse>{

	@Override
	public AddTeammateResponse handleRequest(AddTeammateRequest req, Context context) {
		String failMessage = "";
		AddTeammateResponse response;
		try {
			TeammatesDAO tdao = new TeammatesDAO();
			boolean success = tdao.addTeammate(req.getTeammateName(), req.getProjectName());	
			if (!success) {
				response = new AddTeammateResponse(failMessage,400); //fail
			} else {
					response = new AddTeammateResponse();  // success
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				response = new AddTeammateResponse("Unable to add teammate: " + req.getTeammateName() + "(" + e.getMessage() + ")",400);
			}
			return response; 
	}
}

