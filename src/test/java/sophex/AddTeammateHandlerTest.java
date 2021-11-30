package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.project.AddTeammateHandler;
import sophex.http.project.AddTeammateRequest;
import sophex.http.project.AddTeammateResponse;
import sophex.http.project.CreateProjectRequest;


public class AddTeammateHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	AddTeammateHandler handler = new AddTeammateHandler();  
    	AddTeammateRequest req = new Gson().fromJson(incoming, AddTeammateRequest.class);
    	AddTeammateResponse resp = handler.handleRequest(req, createContext("create"));
    	
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	AddTeammateHandler handler = new AddTeammateHandler();
    	AddTeammateRequest req = new Gson().fromJson(incoming, AddTeammateRequest.class);
    	AddTeammateResponse resp = handler.handleRequest(req, createContext("create"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }
    
    @Test
    public void addToKnownProject() throws Exception {
    	int rndNum = (int)(990*(Math.random()));
    	String str = "throwAway" + rndNum;
   
    	String var = "TestA";
    	AddTeammateRequest apr = new AddTeammateRequest(str, var);
        String toAdd = new Gson().toJson(apr); 
        try {
        	testSuccessInput(toAdd);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void addToNonExistent() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	AddTeammateRequest apr = new AddTeammateRequest("Billie", var);
        String toAdd = new Gson().toJson(apr); 
    	try {
    		testFailInput(toAdd, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    } 
}
