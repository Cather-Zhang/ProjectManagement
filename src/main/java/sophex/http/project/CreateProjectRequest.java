package sophex.http.project;

/** To work with AWS must not have final attributes, must have no-arg constructor, and all get/set methods. */
public class CreateProjectRequest {
	public String name;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	

	public String toString() {
		return "Create(" + name + ")";
	}
	
	public CreateProjectRequest (String name) {
		this.name = name;
	}
	
	public CreateProjectRequest() {
	}
}
