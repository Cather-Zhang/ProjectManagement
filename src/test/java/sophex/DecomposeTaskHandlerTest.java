package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.task.AddTaskHandler;
import sophex.handler.task.DecomposeTaskHandler;
import sophex.http.task.AddTaskRequest;
import sophex.http.task.AddTaskResponse;
import sophex.http.task.DecomposeTaskRequest;
import sophex.http.task.DecomposeTaskResponse;

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
    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String[] var = {"Task" + rndNum, "Some other task"};
    	
    	DecomposeTaskRequest dtr = new DecomposeTaskRequest(var, "JairsFavProject4rl4rl", "1.1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(dtr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    
    @Test
    public void testShouldFail() {
    	int rndNum = (int)(990*(Math.random()));
    	String[] var = {"Task" + rndNum, "Some other task"};
    	
    	DecomposeTaskRequest dtr = new DecomposeTaskRequest(var, "myProject", "2");
        String SAMPLE_INPUT_STRING = new Gson().toJson(dtr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 422);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}
