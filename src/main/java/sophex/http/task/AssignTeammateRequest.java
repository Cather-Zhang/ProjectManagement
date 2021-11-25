package sophex.http.task;

public class AssignTeammateRequest {
	public String projectName;
	public String taskID;
	public String teammateName;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskId() {return this.taskID;}
	public void setTaskId(String id) {this.taskID = id;}
	
	public String getTeammate() {return this.teammateName;}
	public void setTeammate(String name) {this.teammateName = name;}
	
	public AssignTeammateRequest(String name, String id, String teammateName) {
		this.projectName = name;
		this.taskID = id;
		this.teammateName = teammateName;
	}
	
	public AssignTeammateRequest() {}
}
