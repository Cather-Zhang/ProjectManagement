package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.admin.ArchiveProjectHandler;
import sophex.http.admin.ArchiveProjectRequest;
import sophex.http.admin.ArchiveProjectResponse;


public class ArchiveProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();  
    	ArchiveProjectRequest req = new Gson().fromJson(incoming, ArchiveProjectRequest.class);
    	ArchiveProjectResponse resp = handler.handleRequest(req, createContext("list"));
    	
    	Assert.assertEquals(true, resp.project.getIsArchived());
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();
    	ArchiveProjectRequest req = new Gson().fromJson(incoming, ArchiveProjectRequest.class);
    	ArchiveProjectResponse resp = handler.handleRequest(req, createContext("list"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }
    
    @Test
    public void archiveKnownProject() throws Exception {
    	String var = "Calc 4";
    	ArchiveProjectRequest apr = new ArchiveProjectRequest(var);
        String toArchive = new Gson().toJson(apr);  
        try {
        	testSuccessInput(toArchive);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void archiveNonExistent() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	ArchiveProjectRequest apr = new ArchiveProjectRequest(var);
        String toArchive = new Gson().toJson(apr); 
    	try {
    		testFailInput(toArchive, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    } 
}