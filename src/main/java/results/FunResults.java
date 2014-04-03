 /*
Fun files is where objective values are stored.
*/
package results;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.Id;
import database.Database;
import exceptions.MissingConfigurationException;

/**
 *
 * @author elf`
 */
public class FunResults {
    
    
    private String id;
    private String name;
    private Execution execution;
    private int isAll;
    private Experiment experiement;
    
    public FunResults(){
    	this.id = Id.generateUniqueId();
    }
    
    /**
     * Objectives are stored on a single attribute,
     * the values are separated by pipe ("|").
     *  
     */
    private String objectives;

    public String getObjectives() {
        return objectives;
    }
    
    /**
     *  String objs should be a string of values separated with pipes |.
     * 
     * Ex: 0.19191919|0.199193393|39393993
     * 
     * @param objs 
     */
    public void setObjectives(String objs) {
        this.objectives = objs;
    }
    
    public String getId(){
        return this.id;
    }
    
    public void setExecution(Execution execution){
        this.execution = execution;
    }
    
    public Execution getExecution(){
        return this.execution;
    }
    
    public String getName(){
    	return this.name;
    }

	public void setName(String name) {
		this.name = "FUN_"+name;
		
	}

	public int getIsAll() {
		return isAll;
	}
	
	/*
	 * 0 - false
	 * 1 - true
	 * 
	 * Using int type because SQLITE don't have a boolean type
	 * 
	 */
	public void setIsAll(int isAll) {
		this.isAll = isAll;
	}

	public void setExperiement(Experiment experiement) {
		this.experiement =  experiement;
	}
	
	public Experiment getExperiement(){
		return this.experiement;
	}
	
}
