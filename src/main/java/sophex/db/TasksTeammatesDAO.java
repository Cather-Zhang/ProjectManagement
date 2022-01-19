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
			PreparedStatement archived = conn.prepareStatement("SELECT * FROM project WHERE name = ?;");
			archived.setString(1, projectName);
        	ResultSet A = archived.executeQuery();
        
        	// already present?
        	while (A.next()) {
            	int isArchived = A.getInt("is_archived");
            	if(isArchived == 1) {
            		A.close();
            		return false;
            	}
        	}
        	
    		int[] ids = getTaskTeammateIDs(teammateName, projectName, taskPrefix);
    		
    		if(ids[0]==-1||ids[1]==-1) {
    			return false;
    		}
    		
    		//Cather: check if the teammate is already assigned to the task 
    		PreparedStatement exist = conn.prepareStatement("SELECT * FROM tasks_teammates WHERE task_id=? AND teammate_id=?;");
    		exist.setInt(1, ids[0]);
    		exist.setInt(2, ids[1]);
    		
    		ResultSet rs = exist.executeQuery();
    		
    		if(rs.next()) {
    			return false;
    		} else {
        		PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks_teammates (task_id, teammate_id, project_name) values (?,?,?);");
    			ps.setInt(1, ids[0]);
    			ps.setInt(2, ids[1]);
    			ps.setNString(3, projectName);
    			ps.execute();
    		}      	
    		rs.close();
    		return true;
    	} catch (Exception e) {
    		throw new Exception("Failed to assign teammate: " + e.getMessage());
    	}
    }
    
    public boolean unassignTeammate(String teammateName, String projectName, String taskPrefix) throws Exception {
    	try {
    		
			PreparedStatement archived = conn.prepareStatement("SELECT * FROM project WHERE name = ?;");
			archived.setString(1, projectName);
        	ResultSet A = archived.executeQuery();
        
        	// already present?
        	while (A.next()) {
            	int isArchived = A.getInt("is_archived");
            	if(isArchived == 1) {
            		A.close();
            		return false;
            	}
        	}
        	
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
    	teammate.setNString(1, teammateName);
		teammate.setNString(2, projectName);
		
		ResultSet newTeammate = teammate.executeQuery();
		
		if(!newTeammate.next()) {
			result[1] = -1;
		} else {
			result[1] = newTeammate.getInt("id");
		}
    	
		newTeammate.close();
    	
    	return result;
    }
    


}
