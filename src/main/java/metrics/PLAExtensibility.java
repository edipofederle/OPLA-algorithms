package metrics;

import results.Execution;

/**
 *
 * @author elf
 */
public class PLAExtensibility {
    
    private double plaExtensibility;
    private final Execution execution;

    public PLAExtensibility(Execution execution) {
        this.execution = execution;
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

        
}
