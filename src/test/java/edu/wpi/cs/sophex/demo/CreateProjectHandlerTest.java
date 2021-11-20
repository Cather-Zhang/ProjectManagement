package edu.wpi.cs.sophex.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.sophex.demo.http.CreateProjectRequest;
import edu.wpi.cs.sophex.demo.http.CreateProjectResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	CreateProjectHandler handler = new CreateProjectHandler();
    	CreateProjectRequest req = new Gson().fromJson(incoming, CreateProjectRequest.class);
       
        CreateProjectResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	CreateProjectHandler handler = new CreateProjectHandler();
    	CreateProjectRequest req = new Gson().fromJson(incoming, CreateProjectRequest.class);

    	CreateProjectResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testShouldBeOk() {
    	String var = "Calc 4";
    	
    	CreateProjectRequest ccr = new CreateProjectRequest(var);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    
    @Test
    public void testDuplicateInput() {
    	String var = "Calc 4";
    	
    	CreateProjectRequest ccr = new CreateProjectRequest(var);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
        String var2 = "Calc 4";
    	
    	CreateProjectRequest ccr2 = new CreateProjectRequest(var2);
        String SAMPLE_INPUT_STRING2 = new Gson().toJson(ccr2);  
        try {
        	testFailInput(SAMPLE_INPUT_STRING2, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
