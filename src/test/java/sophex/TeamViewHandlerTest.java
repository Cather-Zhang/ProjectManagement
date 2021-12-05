package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.project.CreateProjectHandler;
import sophex.handler.project.TeamViewHandler;
import sophex.http.project.CreateProjectRequest;
import sophex.http.project.CreateProjectResponse;
import sophex.http.project.TeamViewRequest;
import sophex.http.project.TeamViewResponse;

public class TeamViewHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	TeamViewHandler handler = new TeamViewHandler();  
    	TeamViewRequest req = new Gson().fromJson(incoming, TeamViewRequest.class);
    	TeamViewResponse resp = handler.handleRequest(req, createContext("list"));

        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	TeamViewHandler handler = new TeamViewHandler();
    	TeamViewRequest req = new Gson().fromJson(incoming, TeamViewRequest.class);
    	TeamViewResponse resp = handler.handleRequest(req, createContext("list"));
    
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
        
        TeamViewRequest pvr = new TeamViewRequest("myProject");
        String incomingView = new Gson().toJson(pvr);  
      
        try {
        	testSuccessInput(incomingView);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testInList() throws Exception {
    	String var = "myProject";
    	TeamViewRequest pvr = new TeamViewRequest(var);
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
    	TeamViewRequest pvr = new TeamViewRequest(var);
        String incomingView = new Gson().toJson(pvr); 
    	try {
    		testFailInput(incomingView, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
}