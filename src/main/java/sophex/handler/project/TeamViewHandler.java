package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.http.project.TeamViewRequest;
import sophex.http.project.TeamViewResponse;

public class TeamViewHandler implements RequestHandler<TeamViewRequest, TeamViewResponse>{

	@Override
	public TeamViewResponse handleRequest(TeamViewRequest input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
