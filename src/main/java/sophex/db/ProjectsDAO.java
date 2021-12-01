package sophex.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sophex.model.Project;
import sophex.model.Task;
import sophex.model.Teammate;
/**
 * 
 * @author Austin
 *
 */
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
    
    
	public List<Project> getProjectsAdmin() throws Exception {
        
        try {
            Project project = null;
            List<Project> projects = new ArrayList<> ();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName+";");
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
            
        	while(resultSet.next()) {
        		project = generateProject(resultSet);
        	}
            
            resultSet.close();
            ps.close();
            
            try {
            	PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM teammate WHERE project_name=?;");
            	ps2.setNString(1, name);
            	ResultSet resultSetTeammate = ps2.executeQuery();
            	
            	while(resultSetTeammate.next()) {
            		project.addTeammate(resultSet.getString("name"));
            	}
                resultSetTeammate.close();
                ps2.close();
            } catch (Exception e) {
            	e.printStackTrace();
            	throw new Exception("Failed in getting project teammates: " + e.getMessage());
            }
            
            try {
            	PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM task WHERE p_name=?;");
            	ps3.setNString(1, name);
            	ResultSet resultSetTask = ps3.executeQuery();
            	
            	while(resultSetTask.next()) {
            		Task task = new Task(resultSetTask.getString("prefix"), resultSetTask.getString("name"));
            		int taskID = resultSetTask.getInt("task_id");
            		
            		try {
            			PreparedStatement ps4 = conn.prepareStatement("SELECT * FROM tasks_teammates WHERE task_id=?;");
            			ps4.setInt(1,taskID);
            			ResultSet resultSetTaskTeammate = ps4.executeQuery();
            		
            			while(resultSetTaskTeammate.next()) {
            				try { 
            					PreparedStatement ps5 = conn.prepareStatement("SELECT * FROM teammate WHERE id=?;");
            					ps5.setInt(1, resultSetTaskTeammate.getInt("teammate_id"));
            					ResultSet resultSetTeammate = ps5.executeQuery();
            					try {
            						Teammate onTask = new Teammate(resultSetTeammate.getNString("name"));      
            						task.assignTo(onTask);
            					} catch (Exception e) {
            						e.printStackTrace();
            						throw new Exception("There are no teammates assigned to this task: " + e.getLocalizedMessage());
            					}
                                resultSetTeammate.close();
                                ps5.close();
            				} catch (Exception e) {
            	            	e.printStackTrace();
            	            	throw new Exception("Failed in getting teammates from Tasks: " + e.getMessage());
            	            }
            			}
            			project.addTask(task);
                        resultSetTaskTeammate.close();
                        ps4.close();
            		} catch (Exception e) {
                    	e.printStackTrace();
                    	throw new Exception("Failed in getting teammate/task relationships: " + e.getMessage());
                    }
            	}
                resultSetTask.close();
                ps3.close();
            	
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
    
    public boolean addProject(String project) throws Exception {
        
		try {
        	PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
        	ps.setString(1, project);
        	ResultSet resultSet = ps.executeQuery();
        
        	// already present?
        	while (resultSet.next()) {
            	Project p = generateProject(resultSet);
            	if(p.getname().equals(project)) {
            		resultSet.close();
            		return false;
            	}
        	}
        	resultSet.close();

        	ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,is_archived,progress) values(?,?,?);");
        	ps.setString(1,  project);
        	ps.setBoolean(2,  false);
        	ps.setDouble(3, 0);
        	ps.execute();
        	return true;

    	} catch (Exception e) {
        	throw new Exception("Failed to insert project: " + e.getMessage());
    	}
    }
    
    public boolean deleteProject(String project) throws Exception {
    	try {
        	PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE name = ?;");
        	ps.setString(1, project);
        	ps.execute();
        	return true;

    	} catch (Exception e) {
        	throw new Exception("Failed to delete project project: " + e.getMessage());
    	}
    }
	
	private Project generateProject(ResultSet resultSet) throws Exception {
		String name  = resultSet.getString("name");
		Boolean archived = resultSet.getBoolean("is_archived");
		Double progress = resultSet.getDouble("progress");
		return new Project (name,archived,progress);
	}

	
	
}
    

