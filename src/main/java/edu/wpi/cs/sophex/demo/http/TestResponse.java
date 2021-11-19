package edu.wpi.cs.sophex.demo.http;

/**
 * Arbitrary decision to make this a String and not a native double.
 */
public class TestResponse {
	public String response;
	public int statusCode;
	public String error;
	
	public TestResponse (String response, int statusCode) {
		this.response = "" + response;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public TestResponse (int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Result(" + response + ")";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
