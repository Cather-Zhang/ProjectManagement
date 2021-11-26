package sophex.http.admin;

public class DeleteProjectRequest {
	public String projectName;

	public String getProjectName() { return projectName; }
	public void setName(String projectName) { this.projectName = projectName; }
	

	public String toString() {
		return "Add (" + projectName + ")";
	}
	
	public DeleteProjectRequest (String projectName) {
		this.projectName = projectName;
	}
	
	public DeleteProjectRequest() {}
}
