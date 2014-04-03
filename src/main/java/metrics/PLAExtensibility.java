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

    public PLAExtensibility(Execution execution, Experiment experiement) {
        this.execution = execution;
        this.experiement = experiement;
    }

    public double getPlaExtensibility() {
        return plaExtensibility;
    }

    public void setPlaExtensibility(double plaExtensibility) {
        this.plaExtensibility = plaExtensibility;
    }
    
    public Execution getExecution(){
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

	
}
