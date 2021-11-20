package sophex.model;

import java.util.List;

public class Project {
	final String name;
	List<Teammate> teammates;
	List<Task> tasks;
	boolean isArchived = false;
	double progress = 0;
	
	
	public Project (String name) {
		this.name = name;

	}	
	
	public Project (String name, boolean isArchived, double progress) {
		this.name = name;
		this.isArchived = isArchived;
		this.progress = progress;
	}
	
	public String getname() {return name;}
	public List<Teammate> getTeammates() {return teammates;}
	public List<Task> getTasks() {return tasks;}
	public boolean getIsArchived() {return isArchived;}
	public Double getProgress() {return progress;}
	
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

	//TODO 
	public String toString() {
		return "";
	}
}
