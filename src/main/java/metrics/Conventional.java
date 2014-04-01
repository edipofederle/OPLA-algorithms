/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import results.Execution;

/**
 *
 * @author elf
 */
public class Conventional {

    private double choesion;
    private double meanDepComps;
    private double meanNumOps;
    private double sumClassesDepIn;
    private double sumClassesDepOut;
    private double sumDepIn;
    private double sumDepOut;

    private final Execution execution;

    public Conventional(Execution execution) {
        this.execution = execution;
    }

    public double getMacAggregation() {
        return meanNumOps + meanDepComps  + sumClassesDepIn + sumClassesDepOut + sumDepIn + sumDepOut + (1 / choesion);
    }

    public double getChoesion() {
        return choesion;
    }

    public void setChoesion(double choesion) {
        this.choesion = choesion;
    }

    public double getMeanDepComps() {
        return meanDepComps;
    }

    public void setMeanDepComps(double meanDepComps) {
        this.meanDepComps = meanDepComps;
    }

    public double getMeanNumOps() {
        return meanNumOps;
    }

    public void setMeanNumOps(double meanNumOps) {
        this.meanNumOps = meanNumOps;
    }

    public double getSumClassesDepIn() {
        return sumClassesDepIn;
    }

    public void setSumClassesDepIn(double sumClassesDepIn) {
        this.sumClassesDepIn = sumClassesDepIn;
    }

    public double getSumClassesDepOut() {
        return sumClassesDepOut;
    }

    public void setSumClassesDepOut(double sumClassesDepOut) {
        this.sumClassesDepOut = sumClassesDepOut;
    }

    public double getSumDepIn() {
        return sumDepIn;
    }

    public void setSumDepIn(double sumDepIn) {
        this.sumDepIn = sumDepIn;
    }

    public double getSumDepOut() {
        return sumDepOut;
    }

    public void setSumDepOut(double sumDepOut) {
        this.sumDepOut = sumDepOut;
    }
    
    public Execution getExecution(){
    	return this.execution;
    }

}
