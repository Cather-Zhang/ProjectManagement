package sophex.http.admin;

import java.util.ArrayList;
import java.util.List;

import sophex.model.Project;

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
