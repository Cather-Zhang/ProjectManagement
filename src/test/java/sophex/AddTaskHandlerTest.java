package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TasksDAO;
import sophex.handler.task.AddTaskHandler;
import sophex.http.task.AddTaskRequest;
import sophex.http.task.AddTaskResponse;
import sophex.model.Project;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddTaskHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	AddTaskHandler handler = new AddTaskHandler();
    	AddTaskRequest req = new Gson().fromJson(incoming, AddTaskRequest.class);
       
    	AddTaskResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	AddTaskHandler handler = new AddTaskHandler();
    	AddTaskRequest req = new Gson().fromJson(incoming, AddTaskRequest.class);

    	AddTaskResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testAddTopLevel() throws Exception {
    	String var = "TestAddTaskProject";
    	
    	ProjectsDAO dao = new ProjectsDAO();
    	dao.addProject(var);
    	
    	AddTaskRequest atr = new AddTaskRequest(var, "Top level task", null);
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testFail() throws Exception {
    	String var = "TestAddTaskProject";
    	
    	ProjectsDAO dao = new ProjectsDAO();
    	dao.addProject(var);
    	
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	AddTaskRequest atr = new AddTaskRequest(var, "next task", "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testAddSecondLevel() throws Exception {
    	String var = "TestAddTaskProject";
    	ProjectsDAO daoP = new ProjectsDAO();
    	daoP.addProject(var);
    	
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	String[] strs = {"t1.1", "t1.2"};
    	daoT.decomposeTask(strs, var, "1");
    	
    	
    	
    	AddTaskRequest atr = new AddTaskRequest(var, "Second level task 1.3", "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        	Project p = daoP.getProjectUser(var);
        	daoP.deleteProject(var);
        	
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}
