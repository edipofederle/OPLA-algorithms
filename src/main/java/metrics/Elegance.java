package metrics;

import results.Execution;
import results.Experiment;

public class Elegance {

    private double nac;
    private double atmr;
    private double ec;
    private final Execution execution;
    private Experiment experiement;
    private Integer isAll;
    private String idSolution;

    public Elegance(String idSolution, Execution execution, Experiment experiement) {
	this.execution = execution;
	this.experiement = experiement;
	this.idSolution = idSolution;
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

    public String getIdSolution() {
	return this.idSolution;
    }

    public Integer getIsAll() {
	return isAll;
    }

    public void setIsAll(Integer isAll) {
	this.isAll = isAll;
    }
}
