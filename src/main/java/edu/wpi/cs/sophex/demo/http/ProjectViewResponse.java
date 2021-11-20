package edu.wpi.cs.sophex.demo.http;

import java.util.List;

import edu.wpi.cs.sophex.demo.db.ProjectsDAO;
import edu.wpi.cs.sophex.demo.model.Project;
import edu.wpi.cs.sophex.demo.model.Task;
import edu.wpi.cs.sophex.demo.model.Teammate;

public class ProjectViewResponse {
		public int statusCode;
		public String error;
		public String projectName;
		
		public ProjectViewResponse (String projectName) {
			this.statusCode = 200;
			this.error = "";
			this.projectName = projectName;
		}
		
		public ProjectViewResponse (int statusCode, String errorMessage) {
			this.statusCode = statusCode;
			this.error = errorMessage;
		}
		
		//todo
		public String toString() {
			if (statusCode / 100 == 2) {  // too cute?
				return projectName + " view success!";
			} else {
				return "ErrorResult(" + statusCode + ", err=" + error + ")";
			}
		}
		
	

}
