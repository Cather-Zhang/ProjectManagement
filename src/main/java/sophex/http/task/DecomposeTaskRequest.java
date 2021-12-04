package sophex.http.task;

public class DecomposeTaskRequest {
	public String[] taskNames;
	public String projectName;
	public String parentPrefix;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getParentPrefix() {return this.parentPrefix;}
	public void setParentPrefix(String prefix) {this.parentPrefix = prefix;}
	
	public String[] getTasks() {return this.taskNames;}
	public void setTasks(String[] names) {this.taskNames = names;}
	
	public DecomposeTaskRequest(String[] taskNames, String projectName, String parentPrefix) {
		this.projectName = projectName;
		this.parentPrefix = parentPrefix;
		this.taskNames = taskNames;
	}
	
	public DecomposeTaskRequest() {}
}
