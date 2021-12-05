package sophex.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import sophex.model.Project;
import sophex.model.Task;
import sophex.model.Teammate;
public class TasksTeammatesDAO {
	java.sql.Connection conn;
    public LambdaLogger logger;
	
	final String tblName = "tasks_teammates";   // Exact capitalization

    public TasksTeammatesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }


    public boolean assignTeammate(String teammateName, String projectName, String taskPrefix) throws Exception {
    	try {
    		int[] ids = getTaskTeammateIDs(teammateName, projectName, taskPrefix);
    		
    		if(ids[0]==-1||ids[1]==-1) {
    			return false;
    		}
    		
    		PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks_teammates (task_id, teammate_id) values (?,?);");
			ps.setInt(1, ids[0]);
			ps.setInt(2, ids[1]);
			ps.execute();
    		
    		return true;
    	} catch (Exception e) {
    		throw new Exception("Failed to assign teammate: " + e.getMessage());
    	}
    }
    
    public boolean unassignTeammate(String teammateName, String projectName, String taskPrefix) throws Exception {
    	try {
    		int[] ids = getTaskTeammateIDs(teammateName, projectName, taskPrefix);
    		
    		if(ids[0]==-1||ids[1]==-1) {
    			return false;
    		}
    		
    		PreparedStatement ps = conn.prepareStatement("DELETE FROM tasks_teammates WHERE task_id=? AND teammate_id=?;");
        	ps.setInt(1,  ids[0]);
        	ps.setInt(2,  ids[1]);
        	ps.execute();

    		return true;
    	} catch (Exception e) {
    		throw new Exception("Failed to unassign teammate: " + e.getMessage());
    	}
    }
    
    private int[] getTaskTeammateIDs(String teammateName, String projectName, String taskPrefix) throws Exception {
    	int[] result = new int[2];
    	
    	PreparedStatement task = conn.prepareStatement("SELECT * FROM task WHERE prefix=? AND p_name=?;");
    	task.setNString(1, taskPrefix);
		task.setNString(2, projectName);
		
		ResultSet newTask = task.executeQuery();
		
		if(!newTask.next()) {
			result[0] = -1;
		} else {
			result[0] = newTask.getInt("task_id");
		}
    	
		newTask.close();
		
		PreparedStatement teammate = conn.prepareStatement("SELECT * FROM teammate WHERE name=? AND project_name=?;");
    	teammate.setNString(1, projectName);
		teammate.setNString(2, teammateName);
		
		ResultSet newTeammate = teammate.executeQuery();
		
		if(!newTeammate.next()) {
			result[1] = -1;
		} else {
			result[1] = newTeammate.getInt("teammate_id");
		}
    	
		newTeammate.close();
    	
    	return result;
    }

    
    







}
