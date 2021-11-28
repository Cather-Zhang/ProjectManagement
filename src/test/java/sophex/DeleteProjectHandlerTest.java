package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.admin.DeleteProjectHandler;
import sophex.http.admin.DeleteProjectRequest;
import sophex.http.admin.DeleteProjectResponse;


public class DeleteProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	DeleteProjectHandler handler = new DeleteProjectHandler();  
    	DeleteProjectRequest req = new Gson().fromJson(incoming, DeleteProjectRequest.class);
    	DeleteProjectResponse resp = handler.handleRequest(req, createContext("list"));

        Assert.assertEquals(200, resp.statusCode);
    }
	

    void testFailInput(String incoming, int failureCode) throws IOException {
    	DeleteProjectHandler handler = new DeleteProjectHandler();
    	DeleteProjectRequest req = new Gson().fromJson(incoming, DeleteProjectRequest.class);
    	DeleteProjectResponse resp = handler.handleRequest(req, createContext("list"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }
   
    @Test
    public void deleteKnownProject() throws Exception {
    	String var = "Calc 4";
    	DeleteProjectRequest apr = new DeleteProjectRequest(var);
        String toDelete = new Gson().toJson(apr);  
        try {
        	testSuccessInput(toDelete);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
   
    @Test
    public void deleteNonExistent() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	DeleteProjectRequest apr = new DeleteProjectRequest(var);
        String toDelete = new Gson().toJson(apr); 
    	try {
    		testFailInput(toDelete, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    } 
}