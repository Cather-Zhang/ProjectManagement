package sophex.http.admin;

public class ArchiveProjectResponse {
	public int statusCode;
	public String error;
		
	/**
	 * success, status = 200
	 * @param project
	 */
	public ArchiveProjectResponse () {
		this.statusCode = 200;
		this.error = "";
	}
	
	/**
	 * fail
	 * @param statusCode
	 * @param errorMessage
	 */
	public ArchiveProjectResponse (String errorMessage, int statusCode) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	/*public String toString() {
		if (statusCode / 100 == 2) { 
			return "Project(" + project + ")";
		} else if (statusCode == 400) {   //SOME ERROR CODE
			return ("Project " + project.getname() + " does not exist");
		}
		{
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}*/
}
