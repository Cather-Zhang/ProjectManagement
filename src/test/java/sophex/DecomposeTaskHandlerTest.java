package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TasksDAO;
import sophex.db.TasksTeammatesDAO;
import sophex.db.TeammatesDAO;
import sophex.handler.task.AddTaskHandler;
import sophex.handler.task.DecomposeTaskHandler;
import sophex.http.task.AddTaskRequest;
import sophex.http.task.AddTaskResponse;
import sophex.http.task.DecomposeTaskRequest;
import sophex.http.task.DecomposeTaskResponse;
import sophex.model.Project;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DecomposeTaskHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	DecomposeTaskHandler handler = new DecomposeTaskHandler();
    	DecomposeTaskRequest req = new Gson().fromJson(incoming, DecomposeTaskRequest.class);
       
    	DecomposeTaskResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	DecomposeTaskHandler handler = new DecomposeTaskHandler();
    	DecomposeTaskRequest req = new Gson().fromJson(incoming, DecomposeTaskRequest.class);

    	DecomposeTaskResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testShouldBeOk() throws Exception {
    	
    	String var = "TestDecomposeTaskProject";
    	
    	ProjectsDAO dao = new ProjectsDAO();
    	dao.addProject(var);
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	TeammatesDAO daoTe = new TeammatesDAO();
    	daoTe.addTeammate("Person A", var);
    	
    	TasksTeammatesDAO daoTT = new TasksTeammatesDAO();
    	daoTT.assignTeammate("Person A", var, "1");
    	
    	String[] tasks = {"t1.1", "t1.2"};
    	
    	DecomposeTaskRequest dtr = new DecomposeTaskRequest(tasks, var, "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(dtr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        	Project p = dao.getProjectUser(var);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    
    @Test
    public void testShouldFail() throws Exception {
    	ProjectsDAO dao = new ProjectsDAO();
    	String var = "TestDecomposeTaskProject";
    	int rndNum = (int)(990*(Math.random()));
    	String[] tasks = {"Task" + rndNum, "Some other task"};
    	
    	DecomposeTaskRequest dtr = new DecomposeTaskRequest(tasks, var, "2");
        String SAMPLE_INPUT_STRING = new Gson().toJson(dtr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}
