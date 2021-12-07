package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TasksDAO;
import sophex.db.TasksTeammatesDAO;
import sophex.db.TeammatesDAO;
import sophex.handler.task.AssignTeammateHandler;
import sophex.http.task.AssignTeammateRequest;
import sophex.http.task.AssignTeammateResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AssignTeammateHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	AssignTeammateHandler handler = new AssignTeammateHandler();
    	AssignTeammateRequest req = new Gson().fromJson(incoming, AssignTeammateRequest.class);
       
    	AssignTeammateResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	AssignTeammateHandler handler = new AssignTeammateHandler();
    	AssignTeammateRequest req = new Gson().fromJson(incoming, AssignTeammateRequest.class);

    	AssignTeammateResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testShouldBeOk() throws Exception {
    	ProjectsDAO dao = new ProjectsDAO();
        String var = "Test assign task";
        dao.addProject(var);
        
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	TeammatesDAO daoTe = new TeammatesDAO();
    	daoTe.addTeammate("Test A", var);
    	
    	AssignTeammateRequest atr = new AssignTeammateRequest("Test A", var, "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testTeammateNotOnProject() {

    	AssignTeammateRequest atr = new AssignTeammateRequest("fdgzdfhr", "myProject", "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    
    @Test
    public void testTeammateAlreadyAssigned() throws Exception {
    	ProjectsDAO dao = new ProjectsDAO();
        String var = "Test assign task";
        dao.addProject(var);
        
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	TeammatesDAO daoTe = new TeammatesDAO();
    	daoTe.addTeammate("Test A", var);
    	
    	TasksTeammatesDAO daoTT = new TasksTeammatesDAO();
    	daoTT.assignTeammate("Test A", var, "1");
    	
    	AssignTeammateRequest atr = new AssignTeammateRequest("Test A", var, "1");

        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testTaskDoesNotExist() throws Exception {

    	ProjectsDAO dao = new ProjectsDAO();
        String var = "Test assign task";
        dao.addProject(var);
        
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	TeammatesDAO daoTe = new TeammatesDAO();
    	daoTe.addTeammate("Test A", var);

    	
    	AssignTeammateRequest atr = new AssignTeammateRequest("Test A", var, "9");

        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    

}
