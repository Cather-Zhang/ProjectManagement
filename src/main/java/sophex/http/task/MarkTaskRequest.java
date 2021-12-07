package sophex.http.task;

public class MarkTaskRequest {
	public String projectName;
	public String taskPrefix;

	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskPrefix() {return this.taskPrefix;}
	public void setTaskPrefix(String id) {this.taskPrefix = id;}

	
	public MarkTaskRequest(String name, String id) {
		this.projectName = name;
		this.taskPrefix = id;
	}
	
	public MarkTaskRequest() {}
}
