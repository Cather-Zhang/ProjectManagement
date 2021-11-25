package sophex.handler.admin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.http.admin.DeleteProjectRequest;
import sophex.http.admin.DeleteProjectResponse;

public class DeleteProjectHandler implements RequestHandler<DeleteProjectRequest, DeleteProjectResponse>{

	@Override
	public DeleteProjectResponse handleRequest(DeleteProjectRequest input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
