package edu.wpi.cs.sophex.demo.http;

/**
 * Arbitrary decision to make this a String and not a native double.
 */
public class CreateProjectResponse {
	public String result;
	public int statusCode;
	public String error;
	
	public CreateProjectResponse (double value, int statusCode) {
		this.result = "" + value; // doesn't matter since error
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public CreateProjectResponse (int statusCode, String errorMessage) {
		this.result = ""; // doesn't matter since error
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Result(" + result + ")";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
