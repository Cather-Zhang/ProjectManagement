package sophex.http.project;

import sophex.model.Project;

;

public class ProjectViewResponse {
		public int statusCode;
		public String error;
		public Project project;
		
		public ProjectViewResponse (Project project) {
			this.project = project;
			this.statusCode = 200;
			this.error = "";
		}
		
		public ProjectViewResponse (int statusCode, String errorMessage) {
			project = null;
			this.statusCode = statusCode;
			this.error = errorMessage;
		}
		
		/*public String toString() {
			if (statusCode / 100 == 2) {  // too cute?
				return project.getname() + " view success!";
			} else {
				return "ErrorResult(" + statusCode + ", err=" + error + ")";
			}
		}*/
		
	

}
