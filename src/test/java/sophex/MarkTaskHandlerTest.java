package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TasksDAO;
import sophex.handler.task.AddTaskHandler;
import sophex.handler.task.MarkTaskHandler;
import sophex.http.task.AddTaskRequest;
import sophex.http.task.AddTaskResponse;
import sophex.http.task.MarkTaskRequest;
import sophex.http.task.MarkTaskResponse;
import sophex.model.Project;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class MarkTaskHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	MarkTaskHandler handler = new MarkTaskHandler();
    	MarkTaskRequest req = new Gson().fromJson(incoming, MarkTaskRequest.class);
       
    	MarkTaskResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testAddTopLevel() throws Exception {
    	String var = "TestMarkTaskProject";
    	
    	ProjectsDAO dao = new ProjectsDAO();
    	dao.addProject(var);
    	
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	
    	MarkTaskRequest atr = new MarkTaskRequest(var, "1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        	dao.deleteProject(var);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
