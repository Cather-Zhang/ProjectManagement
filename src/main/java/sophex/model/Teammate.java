package sophex.model;

import java.util.List;

public class Teammate {

	final String name;
	List<String> tasks;
	
	public Teammate(String name) {
		this.name = name;
	}
	
	public String getName() {return this.name;}
	
	public List<String> getTasks() {return this.tasks;}
	
	public void addTask(Task t) {this.tasks.add(t.getName());}
	public void removeTask(Task t) {this.tasks.remove(t.getName());}

}
