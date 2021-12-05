package sophex.model;

import java.util.ArrayList;

public class Teammate {

	final String name;
	ArrayList<String> tasks;
	
	public Teammate(String name) {
		this.name = name;
		tasks = new ArrayList<>();
	}
	
	public String getName() {return this.name;}
	
	public ArrayList<String> getTasks() {return this.tasks;}
	
	public void addTask(String taskName) {this.tasks.add(taskName);}
	public void removeTask(Task t) {this.tasks.remove(t.getName());}

}
