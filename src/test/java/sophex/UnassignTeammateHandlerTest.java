package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TasksDAO;
import sophex.db.TeammatesDAO;
import sophex.handler.task.AssignTeammateHandler;
import sophex.handler.task.UnassignTeammateHandler;
import sophex.http.task.AssignTeammateRequest;
import sophex.http.task.AssignTeammateResponse;
import sophex.http.task.UnassignTeammateRequest;
import sophex.http.task.UnassignTeammateResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UnassignTeammateHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	UnassignTeammateHandler handler = new UnassignTeammateHandler();
    	UnassignTeammateRequest req = new Gson().fromJson(incoming, UnassignTeammateRequest.class);
       
    	UnassignTeammateResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	UnassignTeammateHandler handler = new UnassignTeammateHandler();
    	UnassignTeammateRequest req = new Gson().fromJson(incoming, UnassignTeammateRequest.class);

    	UnassignTeammateResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    @Test
    public void testShouldBeOk() throws Exception {
    	ProjectsDAO dao = new ProjectsDAO();
        String var = "Test unassign task";
        dao.addProject(var);
        
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	TeammatesDAO daoTe = new TeammatesDAO();
    	daoTe.addTeammate("Test A", var);
    	
    	UnassignTeammateRequest atr = new UnassignTeammateRequest("Test A", var, "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testTeammateNotExist() throws Exception {
    	ProjectsDAO dao = new ProjectsDAO();
        String var = "Test unassign task";
        dao.addProject(var);
        
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	UnassignTeammateRequest atr = new UnassignTeammateRequest("Test A", var, "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testTaskNotExist() throws Exception {
    	ProjectsDAO dao = new ProjectsDAO();
        String var = "Test unassign task";
        dao.addProject(var);
        
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	TeammatesDAO daoTe = new TeammatesDAO();
    	daoTe.addTeammate("Test A", var);
    	
    	UnassignTeammateRequest atr = new UnassignTeammateRequest("Test A", var, "2");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
}
