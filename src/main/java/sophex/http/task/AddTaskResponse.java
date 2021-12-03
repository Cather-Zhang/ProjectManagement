package sophex.http.task;

public class AddTaskResponse {
	public int statusCode;
	public String error;
	
	/**
	 * success, status = 200
	 * @param newProjectName
	 */
	public AddTaskResponse () {
		this.statusCode = 200;
		this.error = "";
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public AddTaskResponse (String errorMessage, int statusCode) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
/*	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Project(" + project.getname() + ")";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}*/
}
