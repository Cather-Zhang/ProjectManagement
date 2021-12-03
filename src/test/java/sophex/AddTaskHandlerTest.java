package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.task.AddTaskHandler;
import sophex.http.task.AddTaskRequest;
import sophex.http.task.AddTaskResponse;

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
    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "Task" + rndNum;
    	
    	AddTaskRequest atr = new AddTaskRequest("myProject", var, null);
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}
