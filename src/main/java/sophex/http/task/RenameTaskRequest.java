package sophex.http.task;

public class RenameTaskRequest {
	public String projectName;
	public String taskPrefix;
	public String newTaskName;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskPrefix() {return this.taskPrefix;}
	public void setTaskPrefix(String id) {this.taskPrefix = id;}
	
	public String getNewTaskName() {return this.newTaskName;}
	public void setNewTaskName(String newName) {this.newTaskName = newName;}
	
	public RenameTaskRequest(String newTaskName, String projectName, String taskPrefix) {
		this.projectName = projectName;
		this.taskPrefix = taskPrefix;
		this.newTaskName = newTaskName;
	}
	
	public RenameTaskRequest() {}
}
