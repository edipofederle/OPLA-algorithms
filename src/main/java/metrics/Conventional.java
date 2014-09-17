/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import results.Execution;
import results.Experiment;

/**
 * 
 * @author elf
 */
public class Conventional {

    private Double sumChoesion;
    private Double cohesion;
    private Double meanDepComps;
    private Double meanNumOps;
    private int sumClassesDepIn;
    private int sumClassesDepOut;
    private Double sumDepIn;
    private Double sumDepOut;
    private Integer isAll;
    private String idSolution;

    private Experiment experiement;
    private final Execution execution;

    public Conventional(String idSolution, Execution execution, Experiment experiement) {
	this.execution = execution;
	this.experiement = experiement;
	this.idSolution = idSolution;
    }

    public Double getMacAggregation() {
	return this.meanNumOps + this.meanDepComps  + Double.valueOf(this.sumClassesDepIn) + Double.valueOf(this.sumClassesDepOut) + this.sumDepIn + this.sumDepOut + (1 / this.sumChoesion);
    }
    

    public Double getSumChoesion() {
	return sumChoesion;
    }

    public void setSumCohesion(Double sumChoesion) {
	this.sumChoesion = sumChoesion;
    }

    public Double getMeanDepComps() {
	return meanDepComps;
    }

    public void setMeanDepComps(Double meanDepComps) {
	this.meanDepComps = meanDepComps;
    }

    public Double getMeanNumOps() {
	return meanNumOps;
    }

    public void setMeanNumOps(Double meanNumOps) {
	this.meanNumOps = meanNumOps;
    }

    public int getSumClassesDepIn() {
	return sumClassesDepIn;
    }

    public void setSumClassesDepIn(int sumClassesDepIn) {
	this.sumClassesDepIn = sumClassesDepIn;
    }

    public int getSumClassesDepOut() {
	return sumClassesDepOut;
    }

    public void setSumClassesDepOut(int sumClassesDepOut) {
	this.sumClassesDepOut = sumClassesDepOut;
    }

    public Double getSumDepIn() {
	return sumDepIn;
    }

    public void setSumDepIn(Double sumDepIn) {
	this.sumDepIn = sumDepIn;
    }

    public Double getSumDepOut() {
	return sumDepOut;
    }

    public void setSumDepOut(Double sumDepOut) {
	this.sumDepOut = sumDepOut;
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

    public String getIdSolution() {
	return this.idSolution;
    }

    public Double getCohesion() {
        return cohesion;
    }

    public void setCohesion(Double choesion) {
        this.cohesion = choesion;
    }

    public void setSumChoesion(Double sumChoesion) {
        this.sumChoesion = sumChoesion;
    }
    
    

}
