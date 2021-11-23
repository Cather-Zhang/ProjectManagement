package sophex.http.task;

public class UnassignTeammateRequest {
	String projectName;
	String taskID;
	String teammateName;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskId() {return this.taskID;}
	public void setTaskId(String id) {this.taskID = id;}
	
	public String getTeammate() {return this.teammateName;}
	public void setTeammate(String name) {this.teammateName = name;}
	
	public UnassignTeammateRequest(String name, String id, String teammateName) {
		this.projectName = name;
		this.taskID = id;
		this.teammateName = teammateName;
	}
	
	public UnassignTeammateRequest() {}
}
