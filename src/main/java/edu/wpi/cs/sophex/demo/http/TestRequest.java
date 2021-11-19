package edu.wpi.cs.sophex.demo.http;

/** To work with AWS must not have final attributes, must have no-arg constructor, and all get/set methods. */
public class TestRequest {
	String projectName;

	public String getProjectName() { return projectName; }
	public void setProjectName(String projectName) { this.projectName = projectName; }
	
	public String toString() {
		return "Project(" + projectName + ")";
	}
	
	public TestRequest (String projectName) {
		this.projectName = projectName;
	}
	
	public TestRequest() {
	}
}
