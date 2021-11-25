package sophex.http.project;

public class AddTeammateRequest {
	public String name;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	

	public String toString() {
		return "Add (" + name + ")";
	}
	
	public AddTeammateRequest (String name) {
		this.name = name;
	}
	
	public AddTeammateRequest() {
	}
}