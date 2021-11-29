package sophex.http.admin;

import sophex.model.Project;

public class DeleteProjectResponse {
	public int statusCode;
	public String error;
		
	/**
	 * success, status = 200
	 * @param project
	 */
	//Need some way for this to be removed from the database; returning name for now
	public DeleteProjectResponse () {
		this.statusCode = 200;
		this.error = "";
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public DeleteProjectResponse (String errorMessage, int statusCode) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	/*public String toString() {
		if (statusCode / 100 == 2) { 
			return "Project(" + project.getname() + ") was successfully deleted";
		} else if (statusCode == 400) {   //SOME ERROR CODE
			return ("Project " + project.getname() + " could not be deleted");
		}
		{
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}*/
}