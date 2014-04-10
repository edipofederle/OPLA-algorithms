package jmetal.experiments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import jmetal.core.Algorithm;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.crossover.Crossover;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.Selection;
import jmetal.operators.selection.SelectionFactory;
import jmetal.problems.OPLA;
import jmetal.util.JMException;
import metrics.AllMetrics;
import persistence.AllMetricsPersistenceDependency;
import persistence.ExecutionPersistence;
import persistence.MetricsPersistence;
import results.Execution;
import results.Experiment;
import results.FunResults;
import results.InfoResult;
import database.Database;
import database.Result;
import exceptions.MissingConfigurationException;

public class NSGAII_OPLA_FeatMut {

    private static Connection connection;
    private static AllMetricsPersistenceDependency allMetricsPersistenceDependencies;
    private static MetricsPersistence mp;
    private static Result result;

    public static int populationSize;
    public static int maxEvaluations;
    public static double mutationProbability;
    public static double crossoverProbability;

    private NSGAIIConfig configs;

    public NSGAII_OPLA_FeatMut(NSGAIIConfig config) {
	this.configs = config;
    }

    public void execute() throws FileNotFoundException, IOException, JMException, ClassNotFoundException {

	intializeDependencies();

	int runsNumber = this.configs.getNumberOfRuns();
	populationSize = this.configs.getPopulationSize();
	maxEvaluations = this.configs.getMaxEvaluation();
	crossoverProbability = this.configs.getCrossoverProbability();
	mutationProbability = this.configs.getMutationProbability();

	String context = "OPLA";

	String plas[] = this.configs.getPlas().split(",");
	String xmiFilePath;

	for (String pla : plas) {
	    xmiFilePath = pla;
	    OPLA problem = null;

	    try {
		problem = new OPLA(xmiFilePath, this.configs.getOplaConfigs());
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    Algorithm algorithm;
	    SolutionSet todasRuns = new SolutionSet();
	    SolutionSet allSolutions = new SolutionSet();

	    Crossover crossover;
	    Mutation mutation;
	    Selection selection;

	    HashMap<String, Object> parameters;

	    algorithm = new NSGAII(problem);

	    // Algorithm parameters
	    algorithm.setInputParameter("populationSize", populationSize);
	    algorithm.setInputParameter("maxEvaluations", maxEvaluations);

	    // Mutation and Crossover
	    parameters = new HashMap<String, Object>();
	    parameters.put("probability", crossoverProbability);
	    crossover = CrossoverFactory.getCrossoverOperator("PLACrossover", parameters);

	    parameters = new HashMap<String, Object>();
	    parameters.put("probability", mutationProbability);
	    mutation = MutationFactory
		    .getMutationOperator("PLAFeatureMutation", parameters, this.configs.getMutationOperators());

	    // Selection Operator
	    parameters = null;
	    selection = SelectionFactory.getSelectionOperator("BinaryTournament", parameters);

	    // Add the operators to the algorithm
	    algorithm.addOperator("crossover", crossover);
	    algorithm.addOperator("mutation", mutation);
	    algorithm.addOperator("selection", selection);

	    if (this.configs.isLog())
		logInforamtions(context, pla);

	    String PLAName = getPlaName(pla);

	    Experiment experiement = mp.createExperiementOnDb(PLAName, "NSGAII");
	    result.setPlaName(PLAName);

	    long time[] = new long[runsNumber];

	    for (int runs = 0; runs < runsNumber; runs++) {

		// Cria uma execução. Cada execução está ligada a um
		// experiemento.
		Execution execution = new Execution(experiement);

		// Execute the Algorithm
		long initTime = System.currentTimeMillis();
		SolutionSet resultFront = algorithm.execute();
		long estimatedTime = System.currentTimeMillis() - initTime;
		time[runs] = estimatedTime;

		resultFront = problem.removeDominadas(resultFront);
		resultFront = problem.removeRepetidas(resultFront);

		execution.setTime(estimatedTime);

		List<FunResults> funResults = result.getObjectives(resultFront.getSolutionSet(), execution, experiement);
		List<InfoResult> infoResults = result.getInformations(resultFront.getSolutionSet(), execution,experiement);
		AllMetrics allMetrics = result.getMetrics(resultFront.getSolutionSet(), execution, experiement);

		execution.setFuns(funResults);
		execution.setInfos(infoResults);
		execution.setAllMetrics(allMetrics);

		ExecutionPersistence persistence = new ExecutionPersistence(allMetricsPersistenceDependencies);
		try {
		    persistence.persist(execution);
		    persistence = null;
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		resultFront.saveVariablesToFile("VAR_" + runs + "_");
		
		// armazena as solucoes de todas runs
		todasRuns = todasRuns.union(resultFront);

		allSolutions = allSolutions.union(resultFront);

	    }
	    // Thelma - Dez2013 - duas proximas linhas
	    // TODO Hypervolume vai continuar no arquivo TXT. Ver local onde
	    // salvar.
	    // String moea = "NSGAII-M";
	    // allSolutions.printObjectivesToFile(directory + "/Hypervolume/"+
	    // NameOfPLA + "/" + NameOfPLA + "_HV_" + moea + ".txt");

	    todasRuns = problem.removeDominadas(todasRuns);
	    todasRuns = problem.removeRepetidas(todasRuns);

	    System.out.println("------ All Runs - Non-dominated solutions --------");

	    List<FunResults> funResults = result.getObjectives(todasRuns.getSolutionSet(), null, experiement);
	    mp.saveFunAll(funResults);
	    funResults = null;

	    List<InfoResult> infoResults = result.getInformations(todasRuns.getSolutionSet(), null, experiement);
	    mp.saveInfoAll(infoResults);
	    infoResults = null;

	    todasRuns.saveVariablesToFile("VAR_All_"); // Arquitetura
						       // propriamente dita

	    AllMetrics allMetrics = result.getMetrics(todasRuns.getSolutionSet(), null, experiement);
	    mp.persisteMetrics(allMetrics);
	    mp = null;
	}
    }

    private void logInforamtions(String context, String pla) {
	System.out.println("\n================ NSGAII ================");
	System.out.println("Context: " + context);
	System.out.println("PLA: " + pla);
	System.out.println("Params:");
	System.out.println("\tPop -> " + populationSize);
	System.out.println("\tMaxEva -> " + maxEvaluations);
	System.out.println("\tCross -> " + crossoverProbability);
	System.out.println("\tMuta -> " + mutationProbability);

	long heapSize = Runtime.getRuntime().totalMemory();
	heapSize = (heapSize / 1024) / 1024;
	System.out.println("Heap Size: " + heapSize + "Mb\n");
    }

    private void intializeDependencies() {
	result = new Result();
	Database.setPathToDB(this.configs.getPathToDb());

	try {
	    connection = Database.getConnection();
	} catch (ClassNotFoundException | MissingConfigurationException | SQLException e) {
	    e.printStackTrace();
	}

	allMetricsPersistenceDependencies = new AllMetricsPersistenceDependency(connection);
	mp = new MetricsPersistence(allMetricsPersistenceDependencies);
    }

    private static String getPlaName(String pla) {
	int beginIndex = pla.lastIndexOf("/") + 1;
	int endIndex = pla.length() - 4;
	return pla.substring(beginIndex, endIndex);
    }

}