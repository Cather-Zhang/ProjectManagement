package sophex.handler.admin;

import java.util.List;
import com.amazonaws.services.lambda.runtime.*;

import sophex.db.ProjectsDAO;
import sophex.http.admin.ListAllProjectsResponse;
import sophex.model.Project;


/**
 * Eliminated need to work with JSON
 */
public class ListAllProjectsHandler implements RequestHandler<Object, ListAllProjectsResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "constants";

	public static final String TOP_LEVEL_BUCKET = "calculatormaster";
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Project> getConstants() throws Exception {
		logger.log("in getConstants");
		ProjectsDAO dao = new ProjectsDAO();
		
		return dao.getProjectsAdmin();
	}
	
	@Override
	public ListAllProjectsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all constants");

		ListAllProjectsResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Project> list = getConstants();

			response = new ListAllProjectsResponse(list, 200);
		} catch (Exception e) {
			response = new ListAllProjectsResponse(403, e.getMessage());
		}
		
		return response;
	}
}
