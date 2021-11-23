package sophex.http.project;

import java.util.List;

import sophex.db.ProjectsDAO;
import sophex.model.Project;
import sophex.model.Task;
import sophex.model.Teammate;

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
		
		public String toString() {
			if (statusCode / 100 == 2) {  // too cute?
				return projectName + " view success!";
			} else {
				return "ErrorResult(" + statusCode + ", err=" + error + ")";
			}
		}
		
	

}
