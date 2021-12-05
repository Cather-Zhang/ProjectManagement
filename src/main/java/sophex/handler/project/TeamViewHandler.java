package sophex.handler.project;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.project.ProjectViewResponse;
import sophex.http.project.TeamViewRequest;
import sophex.http.project.TeamViewResponse;
import sophex.model.Project;
import sophex.model.Teammate;

public class TeamViewHandler implements RequestHandler<TeamViewRequest, TeamViewResponse>{

	@Override
	public TeamViewResponse handleRequest(TeamViewRequest req, Context context) {
		boolean fail = false;
		String failMessage = "";
		TeamViewResponse response;
		String name = req.getName().replaceAll("%20", " ");
		try {
			ArrayList<Teammate> teammates = loadTeammateListFromRDS(req.projectName, context);
			if(teammates == null) {
				failMessage = name + " does not exist.";
				fail = true;
			}	
			if (fail) {
				response = new TeamViewResponse(400, failMessage); //fail
			} else {
				response = new TeamViewResponse(teammates);  // success
			}
		} catch (Exception e) {
			response = new TeamViewResponse(400, "Unable to view team view: " + name + "(" + e.getMessage() + ")");
		}
		return response; 
	}
	
	public ArrayList<Teammate> loadTeammateListFromRDS(String projectName, Context c) throws Exception {
		ProjectsDAO dao = new ProjectsDAO();
        dao.logger = c.getLogger();
		ArrayList<Teammate> teammates = dao.getTeamView(projectName);
		return teammates;
	}
}

