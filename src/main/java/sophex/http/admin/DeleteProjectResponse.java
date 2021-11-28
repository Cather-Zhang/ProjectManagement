package sophex.http.admin;

import java.util.ArrayList;
import java.util.List;

import sophex.model.Project;

public class DeleteProjectResponse {
	public Project project;
	public int statusCode;
	public String error;
	public List<Project> list;
		
	/**
	 * success, status = 200
	 * @param project
	 */
	//Need some way for this to be removed from the database; returning name for now
	public DeleteProjectResponse (Project project, List<Project> list) {
		this.project = project; 
		this.list = list;
		this.statusCode = 200;
		this.error = "";
		list.remove(project);
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