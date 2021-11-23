package sophex.http.project;

import java.util.List;

import sophex.db.ProjectsDAO;
import sophex.model.Project;
import sophex.model.Task;
import sophex.model.Teammate;

public class ProjectViewResponse {
		public int statusCode;
		public String error;
		public Project p;
		
		public ProjectViewResponse (Project p) {
			this.statusCode = 200;
			this.error = "";
			this.p = p;
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
