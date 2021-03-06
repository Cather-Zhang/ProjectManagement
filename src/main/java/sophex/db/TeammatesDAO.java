package sophex.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sophex.model.Teammate;
import sophex.model.Project;
/**
 * 
 * @author Austin
 *
 */
public class TeammatesDAO {
	java.sql.Connection conn;
	
	final String tblName = "teammate";   // Exact capitalization

    public TeammatesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    
	public List<Teammate> getTeammates(String project_name) throws Exception {
        
        try {
            Teammate teammate = null;
            List<Teammate> teammates = new ArrayList<> ();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM teammate WHERE project_name=?;");
            ps.setString(1, project_name);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                teammate = generateTeammate(resultSet);
                teammates.add(teammate);
            }
            resultSet.close();
            ps.close();
            
            return teammates;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting project: " + e.getMessage());
        }
    }
	
	
    
    public boolean addTeammate(String teammateName, String projectName) throws Exception {
        
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
        	
        	PreparedStatement ps = conn.prepareStatement("SELECT * FROM teammate WHERE name=? AND project_name =?;");
        	ps.setString(1, teammateName);
        	ps.setString(2, projectName);
        	ResultSet resultSet = ps.executeQuery();
        
        	// already present?
        	while (resultSet.next()) {
        		Teammate teammate = generateTeammate(resultSet);
            	if(teammate.getName().equals(teammateName)) {
            		resultSet.close();
            		return false;
            	}       	
        	}
        	resultSet.close();

        	ps = conn.prepareStatement("INSERT INTO " + tblName + "(name,project_name) values(?,?);");
        	ps.setString(1,  teammateName);
        	ps.setString(2,  projectName);
        	ps.execute();
        	return true;

    	} catch (Exception e) {
        	throw new Exception("Failed to add teammate: " + e.getMessage());
    	}
    }
    
    public boolean removeTeammate(String teammateName, String projectName) throws Exception {
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
        	
    		PreparedStatement ps0 = conn.prepareStatement("SELECT * FROM teammate WHERE name=? AND project_name =?;");
    		ps0.setNString(1,  teammateName);
    		ps0.setNString(2, projectName);
    		ResultSet resultSet = ps0.executeQuery();
    		
    		if (resultSet.next()) {
    			int teammateID = resultSet.getInt("id");
    			PreparedStatement ps = conn.prepareStatement("DELETE FROM tasks_teammates WHERE teammate_id=?;");
	        	ps.setInt(1,  teammateID);
	        	ps.execute();
    		}
    		else return false;
        	
    		PreparedStatement ps2 = conn.prepareStatement("DELETE FROM teammate WHERE name=? AND project_name =?;");
    		ps2.setNString(1,  teammateName);
    		ps2.setNString(2, projectName);
    		ps2.execute();
    		
    		resultSet.close();
    		ps0.close();
        	return true;

    	} catch (Exception e) {
        	throw new Exception("Failed to remove teammate: " + e.getMessage());
    	}
    }
	
	private Teammate generateTeammate(ResultSet resultSet) throws Exception {
		String name  = resultSet.getString("name");
		//TODO GRAB TASK IDS FROM TABLE AND ADD TO LIST
		
		return new Teammate (name);
	}
	
	
	
	/**
	 * 
	 * @param projectName 
	 * @param teammateName
	 * @return the teammate looking for of a project
	 * @throws Exception
	 */
    /*public Teammate getTeammate(String projectName, String teammateName) throws Exception {
        
        try {
            Teammate t = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(2,  teammateName);
            ps.setString(3,  projectName);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                t = generateTeammate(resultSet);
            }
            resultSet.close();
            ps.close();
            
            try {
            	PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM teammate WHERE project_name=?;");
            	ps2.setNString(1, name);
            	ResultSet resultSetTeammate = ps2.executeQuery();
            	
            	while(resultSetTeammate.next()) {
            		project.addTeammate(resultSet.getString(name));
            	}
            	
            } catch (Exception e) {
            	e.printStackTrace();
            	throw new Exception("Failed in getting project teammates: " + e.getMessage());
            }
            
            try {
            	PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM task WHERE p_name=?;");
            	ps3.setNString(1, name);
            	ResultSet resultSetTeammate = ps3.executeQuery();
            	
            	while(resultSetTeammate.next()) {
            		//TODO Handle grabbing the tasks
            		//project.addTask(resultSet.getString(name));
            	}
            	
            } catch (Exception e) {
            	e.printStackTrace();
            	throw new Exception("Failed in getting project tasks: " + e.getMessage());
            }
            
            return project;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting project: " + e.getMessage());
        }
    }
    */

	
	
}
    

