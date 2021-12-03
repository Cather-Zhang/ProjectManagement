package sophex.http.task;

public class AddTaskRequest {
	public String taskName;
	public String projectName;
	public String parentPrefix;

	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getParentPrefix() {return this.parentPrefix;}
	public void setparentPrefix(String prefix) {this.parentPrefix = prefix;}
	
	public String getTaskName() {return this.taskName;}
	public void setTaskName(String name) {this.taskName = name;}
	
	public AddTaskRequest(String projectName, String taskName, String parentPrefix) {
		this.projectName = projectName;
		this.parentPrefix = parentPrefix;
		this.taskName = taskName;
	}
	
	public AddTaskRequest() {}
}
