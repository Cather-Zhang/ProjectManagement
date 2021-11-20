package edu.wpi.cs.sophex.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.sophex.demo.http.CreateProjectRequest;
import edu.wpi.cs.sophex.demo.http.CreateProjectResponse;
import edu.wpi.cs.sophex.demo.http.ProjectViewResponse;

public class ProjectViewHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws Exception {
    	ProjectViewHandler handler = new ProjectViewHandler();  
        ProjectViewResponse resp = handler.handleResponse(incoming);
        
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws Exception {
    	ProjectViewHandler handler = new ProjectViewHandler();
        ProjectViewResponse resp = handler.handleResponse(incoming);
        
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
        
        try {
        	testSuccessInput(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testCalc4InList() throws Exception {
    	String var = "Calc 4";
        try {
        	testSuccessInput(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testNotInList() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	try {
    		testFailInput(var, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
}
