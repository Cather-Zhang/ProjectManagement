package edu.wpi.cs.sophex.demo.http;

import edu.wpi.cs.sophex.demo.model.Project;

/**
 * Arbitrary decision to make this a String and not a native double.
 */
public class CreateProjectResponse {
	public String projectName;
	public int statusCode;
	public String error;
	
	public CreateProjectResponse (String newProjectName, int statusCode) {
		this.projectName = newProjectName; // doesn't matter since error
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public CreateProjectResponse (int statusCode, String errorMessage) {
		this.projectName = null; // doesn't matter since error
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Project(" + projectName + ")";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
