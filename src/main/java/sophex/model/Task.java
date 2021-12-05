package sophex.model;

import java.util.List;

public class Task {

	String name;
	String prefix;
	List<Teammate> assignees;
	List<Task> subtasks;
	String parentPrefix;
	boolean isComplete = false;
	
	
	public Task(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
	}
	
	public Task (String name, String prefix, String parentPrefix) {
		this.name = name;
		this.prefix = prefix;
		this.parentPrefix = parentPrefix;
	}
	
	public String getName() {return this.name;}
	public void rename(String name) {this.name = name;}
	
	public String getID() {return this.parentPrefix;}
	public List<Teammate> getAssignees() {return this.assignees;}
	
	public void assignTo(Teammate t) {
		this.assignees.add(t);
		t.addTask(this);
	}
	
	public void unssign(Teammate t) {
		this.assignees.remove(t);
		t.removeTask(this);
	}
	
	public boolean addSubtask(String taskName) { 
		if (subtasks.size() != 0) {  //only add subtasks if it is decomposed already
			Task newTask = new Task(taskName, this.prefix + "." + subtasks.size() + 1, this.prefix);
			subtasks.add(newTask);
			return true;
		}
		else return false;
	}
	
	public boolean decompose(String[] names) {
		if (subtasks.size() != 0) return false; //if subtasks is not empty, return false, unable to decompose the task
		else {
			this.isComplete = false;  //set isComplete to false first
			List<Teammate> oldAssignees = this.assignees;
			this.assignees = null;
			
			for (int i = 0; i < names.length; i++) {  //add new task with each new string
				Task newTask = new Task(names[i], this.prefix + "." + i, this.prefix);
				if (oldAssignees.get(i) != null) newTask.assignTo(oldAssignees.get(i));
				this.subtasks.add(newTask);
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
