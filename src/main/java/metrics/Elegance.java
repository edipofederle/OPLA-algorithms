package metrics;

import results.Execution;
import results.Experiment;

public class Elegance {
    
    private double nac;
    private double atmr;
    private double ec;
    private final Execution execution;
	private Experiment experiement;
    
    public Elegance(Execution execution, Experiment experiement){
        this.execution = execution;
        this.experiement = experiement;
    }

    public double getNac() {
        return nac;
    }

    public void setNac(double nac) {
        this.nac = nac;
    }

    public double getAtmr() {
        return atmr;
    }

    public void setAtmr(double d) {
        this.atmr = d;
    }

    public double getEc() {
        return ec;
    }

    public void setEc(double ec) {
        this.ec = ec;
    }

	public Execution getExecution() {
		return execution;
	}

	public double total() {
		return this.nac + this.atmr + this.ec;
	}

	public Experiment getExperiment() {
		return this.experiement;
	}
}
