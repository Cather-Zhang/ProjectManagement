package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.task.RenameTaskHandler;
import sophex.http.task.RenameTaskRequest;
import sophex.http.task.RenameTaskResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RenameTaskHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	RenameTaskHandler handler = new RenameTaskHandler();
    	RenameTaskRequest req = new Gson().fromJson(incoming, RenameTaskRequest.class);
       
    	RenameTaskResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	RenameTaskHandler handler = new RenameTaskHandler();
    	RenameTaskRequest req = new Gson().fromJson(incoming, RenameTaskRequest.class);

    	RenameTaskResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testShouldBeOk() {

    	
    	RenameTaskRequest rtr = new RenameTaskRequest("newName", "myProject", "1.1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(rtr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}
