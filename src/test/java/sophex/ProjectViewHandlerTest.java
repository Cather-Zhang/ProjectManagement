package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.project.CreateProjectHandler;
import sophex.handler.project.ProjectViewHandler;
import sophex.http.project.CreateProjectRequest;
import sophex.http.project.CreateProjectResponse;
import sophex.http.project.ProjectViewRequest;
import sophex.http.project.ProjectViewResponse;

public class ProjectViewHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	ProjectViewHandler handler = new ProjectViewHandler();  
    	ProjectViewRequest req = new Gson().fromJson(incoming, ProjectViewRequest.class);
    	ProjectViewResponse resp = handler.handleRequest(req, createContext("list"));

        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	ProjectViewHandler handler = new ProjectViewHandler();
    	ProjectViewRequest req = new Gson().fromJson(incoming, ProjectViewRequest.class);
    	ProjectViewResponse resp = handler.handleRequest(req, createContext("list"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }
    
    @Test
    public void testShouldBeOk() throws Exception {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAway" + rndNum;
    	
    	CreateProjectRequest ccr = new CreateProjectRequest(var);
        String incoming = new Gson().toJson(ccr);  
        CreateProjectHandler handler = new CreateProjectHandler();
        CreateProjectRequest req = new Gson().fromJson(incoming, CreateProjectRequest.class);
        CreateProjectResponse resp = handler.handleRequest(req, createContext("create"));
        
        ProjectViewRequest pvr = new ProjectViewRequest(var);
        String incomingView = new Gson().toJson(pvr);  
      
        try {
        	testSuccessInput(incomingView);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testSpaceInvaderInList() throws Exception {
    	String var = "SpaceInvader";
    	ProjectViewRequest pvr = new ProjectViewRequest(var);
        String incomingView = new Gson().toJson(pvr);  
        try {
        	testSuccessInput(incomingView);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testNotInList() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	ProjectViewRequest pvr = new ProjectViewRequest(var);
        String incomingView = new Gson().toJson(pvr); 
    	try {
    		testFailInput(incomingView, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
}
