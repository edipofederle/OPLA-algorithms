package jmetal.experiments;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCommomConfigs {
    
    	private boolean log = false;
	
	private int numberOfRuns;
	private int populationSize;
	private int maxEvaluations;
	private double crossoverProbability;
	private double mutationProbability;
	private String plas;
	private OPLAConfigs oplaConfigs;
	
	private List<String> mutationOperators = new ArrayList<String>();
	
	public void activeLogs(boolean status) {
	    log = status;
	}
	
	/**
	 * PLAs - Path to PLAs separated by comma.
	 * 
	 * @param plas
	 */
	public void setPlas(String plas) {
		this.plas = plas;
	}
	
	public String getPlas(){
		return this.plas;
	}

	public int getNumberOfRuns() {
		return numberOfRuns;
	}
	
	public void setNumberOfRuns(int numberOfRuns) {
		validateGreaterOrEqualOne("numberOfRuns", numberOfRuns);
		this.numberOfRuns = numberOfRuns;
	}

	private void validateGreaterOrEqualOne(String arg, int numberOfRuns) {
		if(numberOfRuns < 1)
			throw new IllegalArgumentException(arg + " must be greater or equal 1");
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		validateGreaterOrEqualOne("populationSize", numberOfRuns);
		this.populationSize = populationSize;
	}

	public int getMaxEvaluation() {
		return maxEvaluations;
	}

	public void setMaxEvaluations(int maxEvaluations) {
		validateGreaterOrEqualOne("maxEvaluation", maxEvaluations);
		this.maxEvaluations = maxEvaluations;
	}
	
	public void setCrossoverProbability(double crossoverProbability) {
		validateArgument("crossoverProbability", crossoverProbability);
		this.crossoverProbability = crossoverProbability;
	}

	private void validateArgument(String arg, double probability) {
		if(probability < 0 || probability > 1)
			throw new IllegalArgumentException(arg+ " must be a value between 0 and 1");
	}
	
	public double getCrossoverProbability(){
		return this.crossoverProbability;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		validateArgument("mutationProbability", mutationProbability);
		this.mutationProbability = mutationProbability;
	}
	
	public void disableCrossover() {
		this.crossoverProbability = 0;
	}
	
	public void disableMutation() {
		this.mutationProbability = 0;
	}
	
	public List<String> getMutationOperators() {
	    return mutationOperators;
	}

	public void setMutationOperators(List<String> mutationOperators) {
	    this.mutationOperators = mutationOperators;
	}

	public OPLAConfigs getOplaConfigs() {
		return oplaConfigs;
	}

	public void setOplaConfigs(OPLAConfigs oplaConfigs) {
		this.oplaConfigs = oplaConfigs;
	}

	public boolean isLog() {
	    return log;
	}

}
