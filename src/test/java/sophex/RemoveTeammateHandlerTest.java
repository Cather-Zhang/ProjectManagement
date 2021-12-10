package sophex;

import java.io.IOException;


import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TeammatesDAO;
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
	
    
    @Test
    public void testShouldPass() throws Exception {
    	String var = "Test remove teammate";
    	ProjectsDAO dao = new ProjectsDAO();
        dao.addProject(var);
        
    	TeammatesDAO daoT = new TeammatesDAO();
    	daoT.addTeammate("Test A", var);
    	RemoveTeammateRequest apr = new RemoveTeammateRequest("Test A", var);
        String toRemove = new Gson().toJson(apr);  
        try {
        	testSuccessInput(toRemove);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}