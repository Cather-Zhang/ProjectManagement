package sophex.http.admin;

public class ArchiveProjectRequest {
	public String projectName;

	public String getProjectName() { return projectName; }
	public void setName(String projectName) { this.projectName = projectName; }
	

	public String toString() {
		return "Archive (" + projectName + ")";
	}
	
	public ArchiveProjectRequest (String projectName) {
		this.projectName = projectName;
	}
	
	public ArchiveProjectRequest() {}
}
