package sophex;

import org.junit.Assert;
import org.junit.Test;

import sophex.db.ProjectsDAO;
import sophex.handler.admin.ListAllProjectsHandler;
import sophex.http.admin.ListAllProjectsResponse;
import sophex.model.Project;


public class ListAllProjectsTest extends LambdaTest{

    @Test
    public void testGetList() throws Exception {
    	ListAllProjectsHandler handler = new ListAllProjectsHandler();

        ListAllProjectsResponse resp = handler.handleRequest(null, createContext("list"));
        ProjectsDAO dao = new ProjectsDAO();
        String var = "Test Admin View";
        dao.addProject(var);
        boolean hasCather = false;
        for (Project p : resp.list) {
        	System.out.println("found project " + p.getname());
        	if (p.getname().equals(var)) { hasCather = true; }
        }
        Assert.assertTrue("Project" + var + "needs to exist in the Projects table (from tutorial) for this test case to work.", hasCather);
        Assert.assertEquals(200, resp.statusCode);
        dao.deleteProject(var);
    }
}
