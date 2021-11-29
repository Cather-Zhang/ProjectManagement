package sophex.handler.project;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.db.ProjectsDAO;
import sophex.http.project.CreateProjectRequest;
import sophex.http.project.CreateProjectResponse;
import sophex.model.Project;



/**
 * create project.
 * 
 * Input a project name for new project
 * First we search the RDS database and see if the constant already exists.
 * If it does, then the project can not be created because project has to have unique name
 * 
 * @author Cather
 */
public class CreateProjectHandler implements RequestHandler<CreateProjectRequest,CreateProjectResponse> {

	LambdaLogger logger;
	

	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	public boolean createProject(String name) throws Exception { 
		if (logger != null) { logger.log("in createProject"); }
		ProjectsDAO dao = new ProjectsDAO();
		return dao.addProject(name);
	}
	
	@Override
	public CreateProjectResponse handleRequest(CreateProjectRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req.toString());

		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		CreateProjectResponse response;
		
		try {
			if (createProject(req.getName())) {
				ProjectsDAO dao = new ProjectsDAO();
				Project p = dao.getProjectUser(req.getName());
				response = new CreateProjectResponse(p);
			} else {
				response = new CreateProjectResponse(("Project " + req.getName() + " already exists"), 422);
			}
		} catch (Exception e) {
			response = new CreateProjectResponse("Unable to create project: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response; 
	}
	
}


