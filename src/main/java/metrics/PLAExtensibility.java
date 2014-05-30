package metrics;

import results.Execution;
import results.Experiment;

/**
 * 
 * @author elf
 */
public class PLAExtensibility {

    private double plaExtensibility;
    private final Execution execution;
    private Experiment experiement;
    private Integer isAll;
    private String idSolution;

    public PLAExtensibility(String idSolution, Execution execution, Experiment experiement) {
	this.execution = execution;
	this.idSolution = idSolution;
	this.experiement = experiement;
    }

    public double getPlaExtensibility() {
	return plaExtensibility;
    }

    public void setPlaExtensibility(double plaExtensibility) {
	this.plaExtensibility = plaExtensibility;
    }

    public Execution getExecution() {
	return this.execution;
    }

    public Experiment getExperiement() {
	return experiement;
    }

    public Integer getIsAll() {
	return isAll;
    }

    public void setIsAll(Integer isAll) {
	this.isAll = isAll;
    }
    
    public String getIdSolution(){
	return this.idSolution;
	
    }

}
