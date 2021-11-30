package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.project.AddTeammateHandler;
import sophex.http.project.AddTeammateRequest;
import sophex.http.project.AddTeammateResponse;


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
    	String var = "Calc 4";
    	AddTeammateRequest apr = new AddTeammateRequest("Jair", var);
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
    	AddTeammateRequest apr = new AddTeammateRequest("Billie", var);
        String toArchive = new Gson().toJson(apr); 
    	try {
    		testFailInput(toArchive, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    } 
}
