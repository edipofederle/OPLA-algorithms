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

    private Double choesion;
    private Double meanDepComps;
    private Double meanNumOps;
    private Double sumClassesDepIn;
    private Double sumClassesDepOut;
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
	return meanNumOps + meanDepComps + sumClassesDepIn + sumClassesDepOut + sumDepIn + sumDepOut + (1 / choesion);
    }

    public Double getChoesion() {
	return choesion;
    }

    public void setChoesion(Double choesion) {
	this.choesion = choesion;
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

    public Double getSumClassesDepIn() {
	return sumClassesDepIn;
    }

    public void setSumClassesDepIn(Double sumClassesDepIn) {
	this.sumClassesDepIn = sumClassesDepIn;
    }

    public Double getSumClassesDepOut() {
	return sumClassesDepOut;
    }

    public void setSumClassesDepOut(Double sumClassesDepOut) {
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

}
