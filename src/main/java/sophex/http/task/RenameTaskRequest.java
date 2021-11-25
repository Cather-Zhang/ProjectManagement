package sophex.http.task;

public class RenameTaskRequest {
	public String projectName;
	public String taskID;
	public String newName;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskId() {return this.taskID;}
	public void setTaskId(String id) {this.taskID = id;}
	
	public String getFlag() {return this.newName;}
	public void setFlag(String newName) {this.newName = newName;}
	
	public RenameTaskRequest(String name, String id, String newName) {
		this.projectName = name;
		this.taskID = id;
		this.newName = newName;
	}
	
	public RenameTaskRequest() {}
}
