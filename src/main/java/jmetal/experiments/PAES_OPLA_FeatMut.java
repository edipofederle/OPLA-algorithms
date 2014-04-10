package jmetal.experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
import jmetal.core.Algorithm;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.paes.PAES;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.Selection;
import jmetal.operators.selection.SelectionFactory;
import jmetal.problems.OPLA;
import jmetal.util.JMException;

public class PAES_OPLA_FeatMut {
    
    private static Connection connection;
    private static AllMetricsPersistenceDependency allMetricsPersistenceDependencies;
    private static MetricsPersistence mp;
    private static Result result;

    public static int populationSize;
    public static int maxEvaluations;
    public static double mutationProbability;
    
    
    private PaesConfigs configs;

    public PAES_OPLA_FeatMut(PaesConfigs config) {
	this.configs = config;
    }

    public void execute() throws FileNotFoundException, IOException, JMException, ClassNotFoundException {
	
	intializeDependencies();

	int runsNumber = this.configs.getNumberOfRuns();
	maxEvaluations = this.configs.getMaxEvaluation();
	int archiveSize = this.configs.getArchiveSize(); //100;
	int biSections = 5; // Nao precisa estar na GUI.
	mutationProbability = this.configs.getMutationProbability();
	String context = "OPLA";

	File directory = new File("experiment/OPLA/PAES/FeatureMutation" + "/");
	if (!directory.exists()) {
	    if (!directory.mkdirs()) {
		System.out.println("Nao foi possivel criar o direorio do resultado");
		System.exit(0);
	    }
	}

	String plas[] = new String[] { "/Users/elf/mestrado/sourcesMestrado/arquitetura/src/test/java/resources/agmfinal/agm.uml" };
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

	    Mutation mutation;
	    Selection selection;

	    HashMap<String, Object> parameters; // Operator parameters

	    algorithm = new PAES(problem);

	    // Algorithm parameters
	    algorithm.setInputParameter("maxEvaluations", maxEvaluations);
	    algorithm.setInputParameter("archiveSize", archiveSize);
	    algorithm.setInputParameter("biSections", biSections);

	    // Mutation

	    parameters = new HashMap<String, Object>();
	    parameters.put("probability", mutationProbability);
	    mutation = MutationFactory.getMutationOperator("PLAFeatureMutation", parameters, this.configs.getMutationOperators());

	    // Selection Operator
	    parameters = null;
	    selection = SelectionFactory.getSelectionOperator("BinaryTournament", parameters);

	    // Add the operators to the algorithm
	    algorithm.addOperator("mutation", mutation);
	    algorithm.addOperator("selection", selection);

	    if (this.configs.isLog())
		logInforamtions(context, pla);

	    String PLAName = getPlaName(pla);
	    
	    Experiment experiement = mp.createExperiementOnDb(PLAName, "PAES");
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

	    }

	    todasRuns.printTimeToFile(directory + "/TIME_" + PLAName, runsNumber, time, pla);

	    todasRuns = problem.removeDominadas(todasRuns);
	    todasRuns = problem.removeRepetidas(todasRuns);

	    System.out.println("------    All Runs - Non-dominated solutions --------");
	    todasRuns.printObjectivesToFile(directory + "/FUN_All_" + PLAName + ".txt");
	    todasRuns.printInformationToFile(directory + "/INFO_All_" + PLAName + ".txt");
	    todasRuns.saveVariablesToFile("VAR_All_");
	}
    
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

    private void logInforamtions(String context, String pla) {
	System.out.println("\n================ PAES ================");
	System.out.println("Context: " + context);
	System.out.println("PLA: " + pla);
	System.out.println("Params:");
	System.out.println("\tMaxEva -> " + maxEvaluations);
	System.out.println("\tMuta -> " + mutationProbability);

	long heapSize = Runtime.getRuntime().totalMemory();
	heapSize = (heapSize / 1024) / 1024;
	System.out.println("Heap Size: " + heapSize + "Mb\n");
    }

    private static String getPlaName(String pla) {
	int beginIndex = pla.lastIndexOf("/") + 1;
	int endIndex = pla.length() - 4;
	return pla.substring(beginIndex, endIndex);
    }
    
    
}