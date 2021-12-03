package sophex.http.task;

public class DecomposeTaskRequest {
	public String projectName;
	public String parentPrefix;
	public String[] taskNames;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getParentPrefix() {return this.parentPrefix;}
	public void setParentPrefix(String prefix) {this.parentPrefix = prefix;}
	
	public String[] getTasks() {return this.taskNames;}
	public void setTasks(String[] names) {this.taskNames = names;}
	
	public DecomposeTaskRequest(String name, String prefix, String[] names) {
		this.projectName = name;
		this.parentPrefix = prefix;
		this.taskNames = names;
	}
	
	public DecomposeTaskRequest() {}
}
