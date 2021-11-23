package sophex.http.project;

import sophex.model.Project;

/**
 * Arbitrary decision to make this a String and not a native double.
 */
public class CreateProjectResponse {
	public Project project;
	public int statusCode;
	public String error;
	
	/**
	 * success, status = 200
	 * @param newProjectName
	 */
	public CreateProjectResponse (Project project) {
		this.project = project; 
		this.statusCode = 200;
		this.error = "";
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public CreateProjectResponse (String errorMessage, int statusCode) {
		this.project = null; // doesn't matter since error
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Project(" + project + ")";
		} else if (statusCode == 422) {
			return ("Project " + project.getname() + " already exists");
		}
		{
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
