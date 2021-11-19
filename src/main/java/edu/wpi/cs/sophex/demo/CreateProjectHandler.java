package edu.wpi.cs.sophex.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.sophex.demo.db.ProjectsDAO;
import edu.wpi.cs.sophex.demo.http.CreateProjectRequest;
import edu.wpi.cs.sophex.demo.http.CreateProjectResponse;
import edu.wpi.cs.sophex.demo.model.Project;



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
	boolean createProject(String name) throws Exception { 
		if (logger != null) { logger.log("in createConstant"); }
		ProjectsDAO dao = new ProjectsDAO();
		
		// check if present
		Project exist = dao.getProjectUser(name);
		Project constant = new Project (name);
		if (exist == null) {
			return dao.addProject(constant);
		} else {
			return false;
		}
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
			if (createProject(req.getArg1())) {
				response = new CreateProjectResponse(req.getArg1());
			} else {
				response = new CreateProjectResponse(req.getArg1(), 422);
			}
		} catch (Exception e) {
			response = new CreateProjectResponse("Unable to create project: " + req.getArg1() + "(" + e.getMessage() + ")", 400);
		}

		return response; 
	}
	
}


