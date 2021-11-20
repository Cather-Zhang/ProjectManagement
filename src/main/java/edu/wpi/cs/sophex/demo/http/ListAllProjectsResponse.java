package edu.wpi.cs.sophex.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.sophex.demo.model.Project;

public class ListAllProjectsResponse {
	public final List<Project> list;
	public final int statusCode;
	public final String error;
	
	public ListAllProjectsResponse (List<Project> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public ListAllProjectsResponse (int code, String errorMessage) {
		this.list = new ArrayList<Project>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyProjects"; }
		return "AllProjects(" + list.size() + ")";
	}
}
