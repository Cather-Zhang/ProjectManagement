package edu.wpi.cs.sophex.demo.model;

import java.util.List;

public class Teammate {

	final String name;
	List<Task> tasks;
	
	public Teammate(String name) {
		this.name = name;
	}
	
	public String getName() {return this.name;}
	
	public List<Task> getTasks() {return this.tasks;}
	
	public void addTask(Task t) {this.tasks.add(t);}
	public void removeTask(Task t) {this.tasks.remove(t);}

}
