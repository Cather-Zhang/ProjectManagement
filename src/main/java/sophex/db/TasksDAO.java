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
    		
    		if(parentPrefix==null) {
    			PreparedStatement ps2 = conn.prepareStatement("SELECT * from task WHERE prefix IS NULL AND p_name=?;");
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
    			
    			if(prefix>1) {
    				return false;
    			}
    			
    			setPrefix = basePrefix + "." + String.valueOf(prefix);
    			hasChildren.close();
    		}
    		
    		PreparedStatement psFinal = conn.prepareStatement("INSERT INTO task (prefix,name,is_completed,p_name,parent_task) values (?,?,?,?,?);");
    		psFinal.setNString(1, setPrefix);
    		psFinal.setNString(2, taskName);
    		psFinal.setBoolean(3, false);
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
    
    
}