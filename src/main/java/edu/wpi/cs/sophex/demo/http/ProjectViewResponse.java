package edu.wpi.cs.sophex.demo.http;

import java.util.List;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.sophex.demo.model.Project;
import edu.wpi.cs.sophex.demo.model.Task;
import edu.wpi.cs.sophex.demo.model.Teammate;

public class ProjectViewResponse {
		public List<Task> tasks;
		public List<Teammate> teammates;
		public int statusCode;
		public String error;
		
		public ProjectViewResponse (String projectName, int statusCode) throws Exception {
			Project p = loadProjectUserFromRDS(projectName); //need to find project with this name
			teammates = p.getTeammates();
			tasks = p.getTasks();
			this.statusCode = statusCode;
			this.error = "";
		}
		
		public ProjectViewResponse (int statusCode, String errorMessage) {
			this.teammates = null;
			this.tasks = null;
			this.statusCode = statusCode;
			this.error = errorMessage;
		}
		
		public String toString() {
			if (statusCode / 100 == 2) {  // too cute?
				return "";
			} else {
				return "ErrorResult(" + statusCode + ", err=" + error + ")";
			}
		}
		
		public Project loadProjectUserFromRDS(String projectName) throws Exception {
			ProjectsDAO dao = new ProjectsDAO();
			Project p = dao.getProjectUser(projectName);
			return p;
		}
	

}
