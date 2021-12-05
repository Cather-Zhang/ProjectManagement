package sophex.http.task;

public class AssignTeammateRequest {
	public String projectName;
	public String taskPrefix;
	public String teammateName;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskPrefix() {return this.taskPrefix;}
	public void setTaskPrefix(String id) {this.taskPrefix = id;}
	
	public String getTeammateName() {return this.teammateName;}
	public void setTeammateName(String name) {this.teammateName = name;}
	
	public AssignTeammateRequest(String teammateName, String projectName, String taskPrefix) {
		this.projectName = projectName;
		this.taskPrefix = taskPrefix;
		this.teammateName = teammateName;
	}
	
	public AssignTeammateRequest() {}
}
