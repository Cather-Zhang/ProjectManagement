package sophex;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import sophex.db.ProjectsDAO;
import sophex.handler.admin.ArchiveProjectHandler;
import sophex.http.admin.ArchiveProjectRequest;
import sophex.http.admin.ArchiveProjectResponse;
import sophex.model.Project;


public class ArchiveProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws Exception {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();
    	ArchiveProjectRequest req = new Gson().fromJson(incoming, ArchiveProjectRequest.class);
    	ArchiveProjectResponse resp = handler.handleRequest(req, createContext("create"));
    	
    	ProjectsDAO dao = new ProjectsDAO();
    	Project project = dao.getProjectUser("JairsFavProject4rl4rl");
    	
    	Assert.assertEquals(true, project.getIsArchived());
    	Assert.assertEquals(200, resp.statusCode);
    }
	

   /* void testFailInput(String incoming, int failureCode) throws IOException {
    	DeleteProjectHandler handler = new DeleteProjectHandler();
    	DeleteProjectRequest req = new Gson().fromJson(incoming, DeleteProjectRequest.class);
    	DeleteProjectResponse resp = handler.handleRequest(req, createContext("create"));
    
        Assert.assertEquals(failureCode, resp.statusCode);
    }*/
    
    @Test
    public void archive() throws Exception{
    	String var = "JairsFavProject4rl4rl";
    	ArchiveProjectRequest apr = new ArchiveProjectRequest(var);
    	String toArchive = new Gson().toJson(apr);
    	try {
    		testSuccessInput(toArchive);
    	}catch(IOException ioe) {
    		Assert.fail("Invalid: " + ioe.getMessage());
    	}
    }
   
    @Test
    public void archiveNonExistent() throws Exception{
    	String var = "salkj;oeihtpwaoie";
    	ArchiveProjectRequest apr = new ArchiveProjectRequest(var);
        String toArchive = new Gson().toJson(apr); 
    	try {
    		testSuccessInput(toArchive);
    	} catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    } 
}