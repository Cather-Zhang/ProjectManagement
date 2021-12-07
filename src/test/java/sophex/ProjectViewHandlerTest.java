package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.db.TasksDAO;
import sophex.db.TasksTeammatesDAO;
import sophex.db.TeammatesDAO;
import sophex.handler.project.CreateProjectHandler;
import sophex.handler.project.ProjectViewHandler;
import sophex.http.project.CreateProjectRequest;
import sophex.http.project.CreateProjectResponse;
import sophex.http.project.ProjectViewRequest;
import sophex.http.project.ProjectViewResponse;

public class ProjectViewHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	ProjectViewHandler handler = new ProjectViewHandler();  
    	ProjectViewRequest req = new Gson().fromJson(incoming, ProjectViewRequest.class);
    	ProjectViewResponse resp = handler.handleRequest(req, createContext("list"));

        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	ProjectViewHandler handler = new ProjectViewHandler();
    	ProjectViewRequest req = new Gson().fromJson(incoming, ProjectViewRequest.class);
    	ProjectViewResponse resp = handler.handleRequest(req, createContext("list"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }
    /*
    @Test
    public void testShouldBeOk() throws Exception {
    	ProjectsDAO dao = new ProjectsDAO();
        String var = "Test Project View";
        dao.addProject(var);
        
        TeammatesDAO daoTe = new TeammatesDAO();
        daoTe.addTeammate("Person A", var);
        daoTe.addTeammate("Person B", var);
        daoTe.addTeammate("Person C", var);
        
        
    	TasksDAO daoT = new TasksDAO();
    	daoT.addTask("Top level task", var, null);
    	daoT.addTask("Top level task 2", var, null);
    	
    	String[] strs = {"Task 1.1","Task 1.2"};
    	String[] strs2 = {"Task 1.1.1","Task 1.1.2"};
    	daoT.decomposeTask(strs, var, "1");
    	daoT.decomposeTask(strs2, var, "1.1");
    	
    	TasksTeammatesDAO daoTT = new TasksTeammatesDAO();
    	daoTT.assignTeammate("Person A", var, "1.2");
    	daoTT.assignTeammate("Person B", var, "1.1.1");
    	daoTT.assignTeammate("Person A", var, "1.1.1");
    	daoTT.assignTeammate("Person A", var, "1.1.2");
       
        ProjectViewRequest pvr = new ProjectViewRequest(var);
        String incomingView = new Gson().toJson(pvr);  
      
        try {
        	testSuccessInput(incomingView);

        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    */
    
    @Test
    public void testInList() throws Exception {
    	String var = "Test Project View";
    	ProjectViewRequest pvr = new ProjectViewRequest(var);
        String incomingView = new Gson().toJson(pvr);  
        try {
        	testSuccessInput(incomingView);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testNotInList() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	ProjectViewRequest pvr = new ProjectViewRequest(var);
        String incomingView = new Gson().toJson(pvr); 
    	try {
    		testFailInput(incomingView, 400);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
}
