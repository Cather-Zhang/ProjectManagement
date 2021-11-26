package sophex.http.project;

public class RemoveTeammateRequest {
	public String teammateName;
	public String projectName;

	public String getTeammateName() { return teammateName; }
	public void setTeammateName(String teammateName) { this.teammateName = teammateName; }
	public String getProjectName() {return projectName;}
	public void setProjectName(String projectName) {this.projectName = projectName;}
	

	public String toString() {
		return "Add (" + teammateName + ") to (" + projectName + ")";
	}
	
	public RemoveTeammateRequest (String teammateName, String projectName) {
		this.teammateName = teammateName;
		this.projectName = projectName;
	}
	
	public RemoveTeammateRequest() {}
}