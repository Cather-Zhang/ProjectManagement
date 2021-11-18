package edu.wpi.cs.sophex.demo.model;

import java.util.List;

public class Project {
	final String name;
	List<Teammate> teammates;
	List<Task> tasks;
	boolean isArchived = false;
	double progress = 0;
	final boolean system;      // when TRUE this is actually stored in S3 bucket
	
	public Project (String name) {
		this.name = name;
		this.system = false;
	}
	
	public Project (String name, boolean system) {
		this.name = name;
		this.system = system;
	}
	
	public boolean getSystem() { return system; }
	public String getname() {return name;}
	public List<Teammate> getTeammates() {return teammates;}
	public List<Task> getTasks() {return tasks;}
	public boolean getIsArchived() {return isArchived;}
	
	public void addTeammate(String name) {
		Teammate t = new Teammate(name);
		teammates.add(t);
	}
	public void removeTeammate(String name) {
		for (int i = 0; i < teammates.size(); i++) {
			if (teammates.get(i).name == name) teammates.remove(i);
		}
	}
	public void archive() {isArchived = true;}
	public void calculateProgress() {
		//TODO
	}
	
	/**
	 * Equality of Constants determined by name alone.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Project) {
			Project other = (Project) o;
			return name.equals(other.name);
		}		
		return false;  // not a Constant
	}

	public String toString() {
		String sysString = "";
		if (system) { sysString = " (system)"; }
		return "[" + name+ " = " + sysString + "]";
	}
}
