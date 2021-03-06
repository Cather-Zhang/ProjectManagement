package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.handler.admin.DeleteProjectHandler;
import sophex.http.admin.DeleteProjectRequest;
import sophex.http.admin.DeleteProjectResponse;


public class DeleteProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	DeleteProjectHandler handler = new DeleteProjectHandler();
    	DeleteProjectRequest req = new Gson().fromJson(incoming, DeleteProjectRequest.class);
    	DeleteProjectResponse resp = handler.handleRequest(req, createContext("create"));
    	
    	Assert.assertEquals(200, resp.statusCode);
    }
	

   /* void testFailInput(String incoming, int failureCode) throws IOException {
    	DeleteProjectHandler handler = new DeleteProjectHandler();
    	DeleteProjectRequest req = new Gson().fromJson(incoming, DeleteProjectRequest.class);
    	DeleteProjectResponse resp = handler.handleRequest(req, createContext("create"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }*/
    
    @Test
    public void delete() throws Exception{
    	String var = "Test delete project";
    	ProjectsDAO dao = new ProjectsDAO();
        dao.addProject(var);
        
    	DeleteProjectRequest apr = new DeleteProjectRequest(var);
    	String toDelete = new Gson().toJson(apr);
    	try {
    		testSuccessInput(toDelete);
    	}catch(IOException ioe) {
    		Assert.fail("Invalid: " + ioe.getMessage());
    	}
    }
  
}