package sophex.http.project;

public class ProjectViewRequest {
	String name;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	

	public String toString() {
		return "List(" + name + ")";
	}
	
	public ProjectViewRequest (String name) {
		this.name = name;
	}
	
	public ProjectViewRequest() {
	}
}
