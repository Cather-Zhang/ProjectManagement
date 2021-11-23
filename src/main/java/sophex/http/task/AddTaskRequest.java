package sophex.http.task;

public class AddTaskRequest {
	String projectName;
	String taskID;
	String taskName;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskId() {return this.taskID;}
	public void setTaskId(String id) {this.taskID = id;}
	
	public String getName() {return this.taskName;}
	public void setFlag(String name) {this.taskName = name;}
	
	public AddTaskRequest(String projectName, String id, String taskName) {
		this.projectName = projectName;
		this.taskID = id;
		this.taskName = taskName;
	}
	
	public AddTaskRequest() {}
}
