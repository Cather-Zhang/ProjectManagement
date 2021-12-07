package sophex.model;

import java.util.ArrayList;
import java.util.List;

public class Task {

	public String name;
	public String prefix;
	public List<Teammate> assignees;
	public ArrayList<Task> subtasks;
	public String parentPrefix;
	public boolean isComplete = false;
	
	
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
	public void setName(String name) {this.name = name;}
	public String getPrefix() {return this.prefix;}
	public String getParentPrefix() {return this.parentPrefix;}
	public List<Teammate> getAssignees() {return this.assignees;}
	
	public void assignTo(Teammate t) {
		this.assignees.add(t);
		t.addTask(this.name);
	}
	
	public void unssign(Teammate t) {
		this.assignees.remove(t);
		t.removeTask(this.name);
	}
	
	public void addSubtask(Task task) { 
		subtasks.add(task);
	}
	
	public ArrayList<Task> getSubtasks(){
		return subtasks;
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
