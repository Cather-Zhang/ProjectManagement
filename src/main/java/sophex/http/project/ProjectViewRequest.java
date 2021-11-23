package sophex.http.project;

public class ProjectViewRequest {
	String name;

	public String getArg1() { return name; }
	public void setArg1(String name) { this.name = name; }
	

	public String toString() {
		return "List(" + name + ")";
	}
	
	public ProjectViewRequest (String name) {
		this.name = name;
	}
	
	public ProjectViewRequest() {
	}
}
