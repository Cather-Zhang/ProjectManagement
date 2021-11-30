package sophex;

import java.io.IOException;


import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.project.RemoveTeammateHandler;
import sophex.http.project.RemoveTeammateRequest;
import sophex.http.project.RemoveTeammateResponse;


public class RemoveTeammateHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	RemoveTeammateHandler handler = new RemoveTeammateHandler();  
    	RemoveTeammateRequest req = new Gson().fromJson(incoming, RemoveTeammateRequest.class);
    	RemoveTeammateResponse resp = handler.handleRequest(req, createContext("create"));
    	
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	RemoveTeammateHandler handler = new RemoveTeammateHandler();
    	RemoveTeammateRequest req = new Gson().fromJson(incoming, RemoveTeammateRequest.class);
    	RemoveTeammateResponse resp = handler.handleRequest(req, createContext("create"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }
    
    @Test
    public void addToKnownProject() throws Exception {
    	String var = "Calc 4";
    	RemoveTeammateRequest apr = new RemoveTeammateRequest("Cather", var);
        String toArchive = new Gson().toJson(apr);  
        try {
        	testSuccessInput(toArchive);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void addToNonExistent() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	RemoveTeammateRequest apr = new RemoveTeammateRequest("Billie", var);
        String toArchive = new Gson().toJson(apr); 
    	try {
    		testFailInput(toArchive, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    } 
}