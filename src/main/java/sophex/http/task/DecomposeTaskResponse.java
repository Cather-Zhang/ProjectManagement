package sophex.http.task;

import sophex.model.Task;

public class DecomposeTaskResponse {
	public int statusCode;
	public String error;
	
	/**
	 * success, status = 200
	 * @param newProjectName
	 */
	public DecomposeTaskResponse () {
		this.statusCode = 200;
		this.error = "";
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public DecomposeTaskResponse (String errorMessage, int statusCode) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "success";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
