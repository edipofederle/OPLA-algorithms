package metrics;

import results.Execution;

public class Elegance {
    
    private double nac;
    private double atmr;
    private double ec;
    private final Execution execution;
    
    public Elegance(Execution execution){
        this.execution = execution;
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
    
}
