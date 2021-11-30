package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import sophex.handler.admin.ListAllProjectsHandler;
import sophex.http.admin.ListAllProjectsResponse;
import sophex.model.Project;


public class ListAllProjectsTest extends LambdaTest{

    @Test
    public void testGetList() throws IOException {
    	ListAllProjectsHandler handler = new ListAllProjectsHandler();

        ListAllProjectsResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasCather = false;
        for (Project p : resp.list) {
        	System.out.println("found project " + p.getname());
        	if (p.getname().equals("cather")) { hasCather = true; }
        }
        Assert.assertTrue("cather project needs to exist in the Projects table (from tutorial) for this test case to work.", hasCather);
        Assert.assertEquals(200, resp.statusCode);
    }
}
