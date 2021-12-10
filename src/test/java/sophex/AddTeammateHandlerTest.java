package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TeammatesDAO;
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
    	String str = "TestAddTeammate";
    	ProjectsDAO daoP = new ProjectsDAO();
    	daoP.addProject(str);
   
    	String var = "TestA";
    	AddTeammateRequest atr = new AddTeammateRequest(var, str);
        String toAdd = new Gson().toJson(atr); 
        try {
        	testSuccessInput(toAdd);
        	daoP.deleteProject(str);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void addDuplicateTeammate() throws Exception {
    	String str = "TestAddTeammate";
    	ProjectsDAO daoP = new ProjectsDAO();
    	daoP.addProject(str);
   
    	String var = "TestA";
    	TeammatesDAO daoT = new TeammatesDAO();
    	daoT.addTeammate(var, str);
    	AddTeammateRequest atr = new AddTeammateRequest(var, str);
        String toAdd = new Gson().toJson(atr); 
        try {
        	testFailInput(toAdd, 400);
        	daoP.deleteProject(str);
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
