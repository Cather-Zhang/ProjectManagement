package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.handler.admin.ArchiveProjectHandler;
import sophex.http.admin.ArchiveProjectRequest;
import sophex.http.admin.ArchiveProjectResponse;
import sophex.model.Project;


public class ArchiveProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming, String projectName) throws Exception {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();
    	ArchiveProjectRequest req = new Gson().fromJson(incoming, ArchiveProjectRequest.class);
    	ArchiveProjectResponse resp = handler.handleRequest(req, createContext("create"));
    	
    	ProjectsDAO dao = new ProjectsDAO();
    	Project p = dao.getProjectUser(projectName);
    	Assert.assertEquals(true, p.getIsArchived());
    	Assert.assertEquals(200, resp.statusCode);
    }
	
    
    @Test
    public void archive() throws Exception{
    	String var = "TestArchiveProject";
    	ProjectsDAO dao = new ProjectsDAO();
    	dao.addProject(var);
    	ArchiveProjectRequest apr = new ArchiveProjectRequest(var);
    	String toArchive = new Gson().toJson(apr);
    	try {
    		testSuccessInput(toArchive, var);
    		dao.deleteProject(var);
    	}catch(IOException ioe) {
    		Assert.fail("Invalid: " + ioe.getMessage());
    	}
    }
   

}