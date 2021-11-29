package sophex.http.project;

public class AddTeammateRequest {
	public String teammateName;
	public String projectName;

	public String getTeammateName() { return teammateName; }
	public String getProjectName() {return projectName;}
	

	public String toString() {
		return "Add (" + teammateName + ") to (" + projectName + ")";
	}
	
	public AddTeammateRequest (String teammateName, String projectName) {
		this.teammateName = teammateName;
		this.projectName = projectName;
	}
	
	public AddTeammateRequest() {}
}