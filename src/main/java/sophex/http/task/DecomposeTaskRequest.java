package sophex.http.task;

public class DecomposeTaskRequest {
	String projectName;
	String taskID;
	String[] taskNames;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskId() {return this.taskID;}
	public void setTaskId(String id) {this.taskID = id;}
	
	public String[] getTasks() {return this.taskNames;}
	public void setTasks(String[] names) {this.taskNames = names;}
	
	public DecomposeTaskRequest(String name, String id, String[] names) {
		this.projectName = name;
		this.taskID = id;
		this.taskNames = names;
	}
	
	public DecomposeTaskRequest() {}
}
