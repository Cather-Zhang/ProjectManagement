package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.project.CreateProjectHandler;
import sophex.http.project.CreateProjectRequest;
import sophex.http.project.CreateProjectResponse;

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
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAway" + rndNum;
    	
    	CreateProjectRequest cpr = new CreateProjectRequest(var);
        String SAMPLE_INPUT_STRING = new Gson().toJson(cpr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    
    @Test
    public void testDuplicateInput() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAway" + rndNum;
    	
    	CreateProjectRequest cpr = new CreateProjectRequest(var);
        String SAMPLE_INPUT_STRING = new Gson().toJson(cpr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
        String var2 = var;
    	
    	CreateProjectRequest cpr2 = new CreateProjectRequest(var2);
        String SAMPLE_INPUT_STRING2 = new Gson().toJson(cpr2);  
        try {
        	testFailInput(SAMPLE_INPUT_STRING2, 422);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
