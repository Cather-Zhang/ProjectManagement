package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.handler.task.AssignTeammateHandler;
import sophex.handler.task.UnassignTeammateHandler;
import sophex.http.task.AssignTeammateRequest;
import sophex.http.task.AssignTeammateResponse;
import sophex.http.task.UnassignTeammateRequest;
import sophex.http.task.UnassignTeammateResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UnsssignTeammateHandlerTest extends LambdaTest {

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
    public void testShouldBeOk() {

    	UnassignTeammateRequest atr = new UnassignTeammateRequest("ewa", "myProject", "1.1");
        String SAMPLE_INPUT_STRING = new Gson().toJson(atr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
