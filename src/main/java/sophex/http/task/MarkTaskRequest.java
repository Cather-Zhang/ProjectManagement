package sophex.http.task;

public class MarkTaskRequest {
	String projectName;
	String taskID;
	boolean flag;
	
	public String getProjectName() {return this.projectName;}
	public void setProjectName(String name) {this.projectName = name;}
	
	public String getTaskId() {return this.taskID;}
	public void setTaskId(String id) {this.taskID = id;}
	
	public boolean getFlag() {return this.flag;}
	public void setFlag(boolean flag) {this.flag = flag;}
	
	public MarkTaskRequest(String name, String id, boolean flag) {
		this.projectName = name;
		this.taskID = id;
		this.flag = flag;
	}
	
	public MarkTaskRequest() {}
}
