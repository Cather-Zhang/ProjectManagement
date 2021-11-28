package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.project.RemoveTeammateRequest;
import sophex.http.project.RemoveTeammateResponse;
import sophex.model.Project;

public class RemoveTeammateHandler implements RequestHandler<RemoveTeammateRequest, RemoveTeammateResponse>{

	@Override
	public RemoveTeammateResponse handleRequest(RemoveTeammateRequest input, Context context) {
				boolean fail = false;
				String failMessage = "";
				RemoveTeammateResponse response;
				try {
					Project project = loadProjectUserFromRDS(input.getProjectName());
					if(project == null) {
						failMessage = input.getProjectName() + " does not exist.";
						fail = true;
					}		
					if (fail) {
						response = new RemoveTeammateResponse(failMessage,400); //fail
					} else {
							response = new RemoveTeammateResponse(project, input.getTeammateName());  // success
						}
					} catch (Exception e) {
						response = new RemoveTeammateResponse("Unable to remove teammate: " + input.getProjectName() + "(" + e.getMessage() + ")",400);
					}
					return response; 
				}
					
				public Project loadProjectUserFromRDS(String projectName) throws Exception {
					ProjectsDAO dao = new ProjectsDAO();
					Project p = dao.getProjectUser(projectName);
					return p;
				}
			}


