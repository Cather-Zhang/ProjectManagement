package sophex.http.project;

/**
 * Arbitrary decision to make this a String and not a native double.
 */
public class CreateProjectResponse {
	public int statusCode;
	public String error;
	public String projectName;
	
	/**
	 * success, status = 200
	 * @param newProjectName
	 */
	public CreateProjectResponse (String projectName) {
		this.statusCode = 200;
		this.error = "";
		this.projectName = projectName;
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public CreateProjectResponse (String errorMessage, int statusCode) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	/*public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Project(" + project + ")";
		} else if (statusCode == 422) {
			return ("Project " + project.getname() + " already exists");
		}
		{
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}*/
}
