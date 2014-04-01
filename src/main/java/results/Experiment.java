/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package results;

import java.text.SimpleDateFormat;
import java.util.Date;

import utils.Id;
import database.Database;

/**
 * Classe que representa um experiemento.
 * 
 * @author elf
 */
public class Experiment {
    
    private String id;
    private String name;
    private String description;
    private String createdAt;
    //private List<Execution> executions;
    
    /**
     * Use this if you don't cares about id generated. Otherwise  use setters
     * 
     * @param name
     * @param description
     * @throws Exception 
     */
    public Experiment(String name, String description) throws Exception{
        this.name = name;
        this.description = description;
        this.id = givenId();
        this.createdAt = setCreatedAt();
    }

    public String getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    private String makeQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("insert into experiments (id, name, description, created_at) ");
        sb.append("values (");
        sb.append(this.id);
        sb.append(",'");
        sb.append(this.name);
        sb.append("','");
        sb.append(this.description);
        sb.append("','");
        sb.append(this.getCreatedAt());
        sb.append("')");
        return sb.toString();
    }
    
    private String givenId(){
       return Id.generateUniqueId();
    }

    public void save() throws Exception {
       Database.getInstance().getConnection().executeUpdate(makeQuery());
    }
    
	public String getCreatedAt() {
		return createdAt;
	}
	
	private String setCreatedAt(){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return dt.format(new Date()).toString();
	}
    
}