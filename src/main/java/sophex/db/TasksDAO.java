package sophex.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import sophex.model.Project;
import sophex.model.Task;
import sophex.model.Teammate;
/**
 * 
 * @author Austin
 *
 */
public class TasksDAO {
	java.sql.Connection conn;
    public LambdaLogger logger;
	
	final String tblName = "project";   // Exact capitalization

    public TasksDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    
    public boolean renameTask(String newTaskName, String projectName, String taskPrefix) throws Exception {
    	try {
    		
    		PreparedStatement updateTask = conn.prepareStatement("UPDATE task SET name=? WHERE prefix=? AND p_name=?");
    		updateTask.setNString(1, newTaskName);
    		updateTask.setNString(2, taskPrefix);
    		updateTask.setNString(3, projectName);
    		
    		updateTask.execute();
    		return true;
    		
    	} catch (Exception e) {
    		throw new Exception("Failed to rename task: " + e.getMessage());
    	}
    }
    
    public boolean markTask(String projectName, String taskPrefix) throws Exception {
    	try {
    		PreparedStatement ps = conn.prepareStatement("SELECT * from task WHERE p_name=? AND prefix=?;");
    		ps.setNString(1, projectName);
    		ps.setNString(2, taskPrefix);
    		
    		ResultSet resultSetTask = ps.executeQuery();
    		
    		if(resultSetTask.next()) {
    			int isComplete = resultSetTask.getInt("is_completed");
    			int flag = (isComplete + 1) % 2;
    			
    			try {
    	    		
    	    		PreparedStatement updateTask = conn.prepareStatement("UPDATE task SET is_completed=? WHERE prefix=? AND p_name=?");
    	    		updateTask.setInt(1, flag);
    	    		updateTask.setNString(2, taskPrefix);
    	    		updateTask.setNString(3, projectName);
    	    		
    	    		updateTask.execute();
    	    		return true;
    	    		
    	    	} catch (Exception e) {
    	    		throw new Exception("Failed to rename task: " + e.getMessage());
    	    	}
    		}
    		else return false;
    		
    	} catch (Exception e) {
    		throw new Exception("Failed to mark task: " + e.getMessage());
    		
    	}
    	
    }
    
    public boolean addTask(String taskName, String projectName, String parentPrefix) throws Exception {
    	try {
    		int parentID = -1;
    		String setPrefix;
    		String basePrefix = "";
    		PreparedStatement ps = conn.prepareStatement("SELECT * from task WHERE p_name=? AND prefix=?;");
    		ps.setNString(1, projectName);
    		ps.setNString(2, parentPrefix);
    		ResultSet resultSetParent = ps.executeQuery();
    		
    		if(resultSetParent.next()) {
    			parentID = resultSetParent.getInt("task_id");
    			basePrefix = resultSetParent.getNString("prefix");
    		}
    		
    		resultSetParent.close();
    		
    		if(parentPrefix==null || parentPrefix.equals("")) {
    			PreparedStatement ps2 = conn.prepareStatement("SELECT * from task WHERE parent_task IS NULL AND p_name=?;");
    			ps2.setNString(1, projectName);
    			int prefix = 1;
    			ResultSet resultSetTopLevel = ps2.executeQuery();
    			
    			while(resultSetTopLevel.next()) {
    				prefix++;
    			}
    			resultSetTopLevel.close();
    			setPrefix = String.valueOf(prefix);
    			
    		} else {
    			PreparedStatement ps3 = conn.prepareStatement("SELECT * from task WHERE parent_task=?;");
    			ps3.setInt(1,parentID);
    			ResultSet hasChildren = ps3.executeQuery();
    			
    			int prefix = 1;
    			
    			while(hasChildren.next()) {
    				prefix++;
    			}
    			
    			if(prefix == 1) {
    				return false;
    			}
    			
    			setPrefix = basePrefix + "." + String.valueOf(prefix);
    			hasChildren.close();
    		}
    		
    		PreparedStatement psFinal = conn.prepareStatement("INSERT INTO task (prefix,name,is_completed,p_name,parent_task) values (?,?,?,?,?);");
    		psFinal.setNString(1, setPrefix);
    		psFinal.setNString(2, taskName);
    		psFinal.setInt(3, 0);
    		psFinal.setNString(4, projectName);
    		if(parentID != -1) {
    			psFinal.setInt(5, parentID);
    		} else {
    			psFinal.setNull(5, java.sql.Types.INTEGER);
    		}
    		
    		psFinal.execute();
    		return true;
    		
    		
    		
    	} catch (Exception e) {
    		throw new Exception("Failed to add task: " + e.getMessage());
    	}
    }
    
    public boolean decomposeTask(String[] taskNames, String projectName, String parentPrefix) throws Exception {
    	try {
    		
    		
    		// Grabs the parent task's ID for the tasks team mates table
    		int parentTaskID;
    		int prefixAddon = 1;
    		ArrayList<Integer> teammateIDs = new ArrayList<>();
    		PreparedStatement parent = conn.prepareStatement("SELECT * FROM task WHERE prefix=? AND p_name=?;");
    		parent.setNString(1, parentPrefix);
    		parent.setNString(2, projectName);
    		ResultSet parentTask = parent.executeQuery();
    		
    		
    		// If parent task somehow doesnt exist, return false
    		if(!parentTask.next()) {
    			return false;
    		}
    		parentTaskID = parentTask.getInt("task_id");
    		parentTask.close();
    		
    		//Sets parent task isLeaf to false
    		PreparedStatement isLeaf = conn.prepareStatement("UPDATE task SET is_leaf=? WHERE task_id=?");
    		isLeaf.setInt(1, 0);
    		isLeaf.setInt(2, parentTaskID);
    		isLeaf.execute();
    		isLeaf.close();
    		
    		PreparedStatement updateTask = conn.prepareStatement("UPDATE task SET is_completed=? WHERE task_id=?");
    		updateTask.setInt(1, 0);
    		updateTask.setInt(2, parentTaskID);
    		updateTask.execute();
    		updateTask.close();
    		
    		
    		//Selecting all task team mate relationships from this table to add all team mate names to be used later
    		PreparedStatement teamTasks = conn.prepareStatement("SELECT * from tasks_teammates WHERE task_id=?;");
    		teamTasks.setInt(1, parentTaskID);
    		ResultSet teammates = teamTasks.executeQuery();
    		
    		while(teammates.next()) {
    			teammateIDs.add(teammates.getInt("teammate_id"));
    		}
    		teammates.close();
    		
    		// Removes team mates from the parent Task
    		PreparedStatement deleteOldRelationship = conn.prepareStatement("DELETE FROM tasks_teammates WHERE task_id=?;");
    		deleteOldRelationship.setInt(1, parentTaskID);
    		deleteOldRelationship.execute();
    		
    		for(int i = 0; i < taskNames.length; i++) {
    			
    			// Inserts new task into the database
    			PreparedStatement psFinal = conn.prepareStatement("INSERT INTO task (prefix,name,is_completed,p_name,parent_task) values (?,?,?,?,?);");
        		String newPrefix = parentPrefix + "." + String.valueOf(prefixAddon + i);
    			psFinal.setNString(1, newPrefix);
        		psFinal.setNString(2, taskNames[i]);
        		psFinal.setBoolean(3, false);
        		psFinal.setNString(4, projectName);
        		psFinal.setInt(5,parentTaskID);
        		psFinal.execute();
        		
        		// Immediately grabs new task from database to fetch its ID:
        		PreparedStatement getNewTask = conn.prepareStatement("SELECT * from task WHERE p_name=? AND prefix=?;");
        		getNewTask.setNString(1, projectName);
        		getNewTask.setNString(2, newPrefix);
        		
        		ResultSet newTask = getNewTask.executeQuery();
        		
        		if(!newTask.next()) {
        			return false;
        		}
        		
        		int taskID = newTask.getInt("task_id");     
        		
        		newTask.close();
        		
        		//Adds new team mate task relationship between ALL teammates to EACH task
        		int roundNum = (teammateIDs.size() - taskNames.length) / taskNames.length + 2;
        		for(int j = 0; j < roundNum; j++) {
        			int index = i + taskNames.length * j;
        			if (index > teammateIDs.size() - 1) break;
        			int teammateID = teammateIDs.get(index);
        			PreparedStatement teamTask = conn.prepareStatement("INSERT INTO tasks_teammates (task_id, teammate_id, project_name) values (?,?,?);");
        			teamTask.setInt(1, taskID);
        			teamTask.setInt(2, teammateID);
        			teamTask.setNString(3, projectName);
        			teamTask.execute();
        		}
    		}
    		return true;
    	} catch (Exception e) {
    		throw new Exception("Failed to decompose task: " + e.getMessage());
    	}
    }
}
    
