package sophex.http.project;

public class TeamViewRequest {
	public String projectName;

	public String getName() { return projectName; }
	public void setName(String projectName) { this.projectName = projectName; }
	

	public String toString() {
		return "List(" + projectName + ")";
	}
	
	public TeamViewRequest (String projectName) {
		this.projectName = projectName;
	}
	
	public TeamViewRequest() {}
}
