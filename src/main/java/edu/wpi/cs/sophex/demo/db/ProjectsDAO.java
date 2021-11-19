package edu.wpi.cs.sophex.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.sophex.demo.model.Project;

public class ProjectsDAO {
	java.sql.Connection conn;
	
	final String tblName = "project";   // Exact capitalization

    public ProjectsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    
	public List<Project> getProjectsAdmin(String name) throws Exception {
        
        try {
            Project project = null;
            List<Project> projects = new ArrayList<> ();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName+";");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                project = generateProject(resultSet);
                projects.add(project);
            }
            resultSet.close();
            ps.close();
            
            return projects;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting project: " + e.getMessage());
        }
    }
	
    public Project getProjectUser(String name) throws Exception {
        
        try {
            Project project = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                project = generateProject(resultSet);
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
    
    public boolean addProject(Project project) throws Exception {
        
		try {
        	PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
        	ps.setString(1, project.getname());
        	ResultSet resultSet = ps.executeQuery();
        
        	// already present?
        	while (resultSet.next()) {
            	Project p = generateProject(resultSet);
            	resultSet.close();
            	return false;
        	}

        	ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,isArchived,progress) values(?,?,?);");
        	ps.setString(1,  project.getname());
        	ps.setBoolean(2,  project.getIsArchived());
        	ps.setDouble(3, project.getProgress());
        	ps.execute();
        	return true;

    	} catch (Exception e) {
        	throw new Exception("Failed to insert project: " + e.getMessage());
    	}
    }
	
	private Project generateProject(ResultSet resultSet) throws Exception {
		String name  = resultSet.getString("name");
		Boolean archived = resultSet.getBoolean("is_archived");
		Double progress = resultSet.getDouble("progress");
		return new Project (name,archived,progress);
	}


	
}
    

