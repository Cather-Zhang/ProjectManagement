package sophex.model;

import java.util.ArrayList;

public class Project {
	final String name;
	ArrayList<Teammate> teammates;
	ArrayList<Task> tasks;
	boolean isArchived = false;
	double progress = 0;
	
	
	public Project (String name) {
		this.name = name;
		teammates = new ArrayList<Teammate>();
		tasks = new ArrayList<Task>();

	}	
	
	public Project (String name, boolean isArchived, double progress) {
		this.name = name;
		this.isArchived = isArchived;
		this.progress = progress;
		teammates = new ArrayList<Teammate>();
		tasks = new ArrayList<Task>();
	}
	
	public String getname() {return name;}
	public ArrayList<Teammate> getTeammates() {return teammates;}
	public ArrayList<Task> getTasks() {return tasks;}
	public boolean getIsArchived() {return isArchived;}
	public Double getProgress() {return progress;}
	public void setTasks(ArrayList<Task> tasks) {this.tasks = tasks;}
	
	public void addTeammate(String name) {
		Teammate t = new Teammate(name);
		System.out.println(t.name);
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
	
	public void wipeTasks() {
		tasks = new ArrayList<Task>();
	}
	
	public void addTask(Task t) {
		tasks.add(t);
	}
	
	/**
	 * Equality of Projects determined by name alone.
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
