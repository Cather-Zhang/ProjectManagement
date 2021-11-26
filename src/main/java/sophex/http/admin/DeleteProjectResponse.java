package sophex.http.admin;

import sophex.model.Project;

public class DeleteProjectResponse {
	public Project project;
	public int statusCode;
	public String error;
		
	/**
	 * success, status = 200
	 * @param project
	 */
	public DeleteProjectResponse (Project project) {
		this.project = project; 
		this.statusCode = 200;
		this.error = "";
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public DeleteProjectResponse (String errorMessage, int statusCode) {
		this.project = null; // doesn't matter since error
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) { 
			return "Project(" + project + ")";
		} else if (statusCode == 400) {   //SOME ERROR CODE
			return ("Project " + project.getname() + " does not exist");
		}
		{
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}