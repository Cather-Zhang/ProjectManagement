package sophex.http.admin;

public class DeleteProjectRequest {
	public String projectName;

	public String getProjectName() { return projectName; }
	public void setProjectName(String projectName) { this.projectName = projectName; }
	

	public String toString() {
		return "Delete (" + projectName + ")";
	}
	
	public DeleteProjectRequest (String projectName) {
		this.projectName = projectName;
	}
	
	public DeleteProjectRequest() {}
}
