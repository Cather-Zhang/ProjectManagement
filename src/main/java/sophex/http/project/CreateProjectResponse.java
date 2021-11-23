package sophex.http.project;

/**
 * Arbitrary decision to make this a String and not a native double.
 */
public class CreateProjectResponse {
	public String projectName;
	public int statusCode;
	public String error;
	
	/**
	 * success, status = 200
	 * @param newProjectName
	 */
	public CreateProjectResponse (String newProjectName) {
		this.projectName = newProjectName; 
		this.statusCode = 200;
		this.error = "";
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public CreateProjectResponse (String errorMessage, int statusCode) {
		this.projectName = null; // doesn't matter since error
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Project(" + projectName + ")";
		} else if (statusCode == 422) {
			return ("Project " + projectName + " already exists");
		}
		{
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
