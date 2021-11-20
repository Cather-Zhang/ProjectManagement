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
	
    /*void testFailInput(String incoming, int failureCode) throws Exception {
    	ProjectViewHandler handler = new ProjectViewHandler();
        ProjectViewResponse resp = handler.handleResponse(incoming);
        
        Assert.assertEquals(failureCode, resp.statusCode);
    }*/
    
    @Test
    public void testShouldBeOk() throws Exception {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAway" + rndNum;
    	
    	CreateProjectRequest ccr = new CreateProjectRequest(var);
        String incoming = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(incoming);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
