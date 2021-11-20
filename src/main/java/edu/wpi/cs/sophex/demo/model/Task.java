package edu.wpi.cs.sophex.demo.model;

import java.util.List;

public class Task {
	String name;
	String identifier;
	List<String> assignees;
	List<Task> subtasks;
	String parentTaskId;
	boolean isComplete = false;
	
	
	public Task(String name, String id) {
		this.name = name;
		this.identifier = id;
	}
	
	public Task (String name, String id, String parentTaskId) {
		this.name = name;
		this.identifier = id;
		this.parentTaskId = parentTaskId;
	}
	
	public String getName() {return this.name;}
	public void rename(String name) {this.name = name;}
	
	public String getID() {return this.identifier;}
	public List<String> getAssignees() {return this.assignees;}
	
	public void assignTo(Teammate t) {
		this.assignees.add(t.getName());
		t.addTask(this);
	}
	
	public void unssign(Teammate t) {
		this.assignees.remove(t.getName());
		t.removeTask(this);
	}
	
	public boolean addSubtask(Task t) { 
		if (subtasks.size() != 0) {  //only add subtasks if it is decomposed already
			subtasks.add(t);
			return true;
		}
		else return false;
	}
	
	public boolean decompose(String[] names) {
		if (subtasks.size() != 0) return false; //if subtasks is not empty, return false, unable to decompose the task
		else {
			for (int i = 0; i < names.length; i++) {
				this.subtasks.add(new Task(names[i], this.identifier + "." + i, this.identifier));
			}
			return true;
		}
	}
	
	public boolean markTask(boolean flag) {
		if (subtasks.size() != 0) return false; // if the task has subtasks, unable to mark
		else {
			this.isComplete = flag;
			return true;
		}
	}
}
