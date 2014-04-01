package jmetal.experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
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
import persistence.FunsResultPersistence;
import persistence.InfosResultPersistence;
import persistence.MetricsPersistence;
import results.Execution;
import results.Experiment;
import results.FunResults;
import results.InfoResult;
import database.Database;
import database.Result;
import exceptions.MissingConfigurationException;

public class NSGAII_OPLA_FeatMut {
	
	private static final String PATH_TO_DB = "src/test/resources/opla_test.db";
    private static Statement connection;
    private static AllMetricsPersistenceDependency allMetricsPersistenceDependencies;
    private static Result result;

	public static int populationSize_;
	public static int maxEvaluations_;
	public static double mutationProbability_;
	public static double crossoverProbability_;

	public static void main(String[] args) throws FileNotFoundException, IOException, JMException, ClassNotFoundException {
		
		
		intializeDependencies();
		

		int runsNumber = 5; // 30;
		populationSize_ = 10; // 100;
		maxEvaluations_ = 5; // 300 gerações

		crossoverProbability_ = 0.0;
		mutationProbability_ = 0.9;
		String context = "OPLA";
		
		/*Thelma - Dez2013 linha adicionada para identificar o algoritmo no
		/* nome do arquivo do hypervolume
		*/
		String moea = "NSGAII-M";

		File directory = new File("experiment/OPLA/NSGA-II/FeatureMutation"+ "/");
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				System.out.println("Nãoo foi posível criar o diretório do resultado");
				System.exit(0);
			}
		}
		
		//TODO GUI - Irá vir da GUI
		String plas[] = new String[] { "/Users/elf/mestrado/sourcesMestrado/arquitetura/src/test/java/resources/agmfinal/agm.uml" };
		String xmiFilePath;

		for (String pla : plas) {
			xmiFilePath = pla;
			OPLA problem = null;
			
			try {
				problem = new OPLA(xmiFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Algorithm algorithm;
			SolutionSet todasRuns = new SolutionSet();
			SolutionSet allSolutions = new SolutionSet();

			Crossover crossover;
			Mutation mutation;
			Selection selection;

			HashMap<String, Double> parameters; // Operator parameters

			algorithm = new NSGAII(problem);

			// Algorithm parameters
			algorithm.setInputParameter("populationSize", populationSize_);
			algorithm.setInputParameter("maxEvaluations", maxEvaluations_);

			// Mutation and Crossover
			parameters = new HashMap<String, Double>();
			parameters.put("probability", crossoverProbability_);
			crossover = CrossoverFactory.getCrossoverOperator("PLACrossover", parameters);

			parameters = new HashMap<String, Double>();
			parameters.put("probability", mutationProbability_);
			mutation = MutationFactory.getMutationOperator("PLAFeatureMutation", parameters);

			// Selection Operator
			parameters = null;
			selection = SelectionFactory.getSelectionOperator("BinaryTournament", parameters);

			// Add the operators to the algorithm
			algorithm.addOperator("crossover", crossover);
			algorithm.addOperator("mutation", mutation);
			algorithm.addOperator("selection", selection);

			System.out.println("\n================ NSGAII ================");
			System.out.println("Context: " + context);
			System.out.println("PLA: " + pla);
			System.out.println("Params:");
			System.out.println("\tPop -> " + populationSize_);
			System.out.println("\tMaxEva -> " + maxEvaluations_);
			System.out.println("\tCross -> " + crossoverProbability_);
			System.out.println("\tMuta -> " + mutationProbability_);

			long heapSize = Runtime.getRuntime().totalMemory();
			heapSize = (heapSize / 1024) / 1024;
			System.out.println("Heap Size: " + heapSize + "Mb\n");

			String PLAName = getPlaName(pla);
			
			Experiment experiement = createExperiementOnDb(PLAName); 
			result.setPlaName(PLAName);
	        
			long time[] = new long[runsNumber];

			for (int runs = 0; runs < runsNumber; runs++) {
				
				//Cria uma execução. Cada execução está ligada a um experiemento.
				Execution execution = new Execution(experiement);
				
				// Execute the Algorithm
				long initTime = System.currentTimeMillis();
				SolutionSet resultFront = algorithm.execute();
				long estimatedTime = System.currentTimeMillis() - initTime;
				time[runs] = estimatedTime;

				resultFront = problem.removeDominadas(resultFront);
				resultFront = problem.removeRepetidas(resultFront);

				// TEste ////
				//resultFront.printObjectivesToFile(directory + "/FUN_" + PLAName	+ "_" + runs + ".txt"); //Será removido
				//resultFront.printInformationToFile(directory + "/INFO_"+ PLAName + "_" + runs + ".txt");//Será removido
				
				execution.setTime(estimatedTime);

				List<FunResults> funResults = result.getObjectives(resultFront.getSolutionSet(), execution, experiement); // Édipo...
				List<InfoResult> infoResults = result.getInformations(resultFront.getSolutionSet(), execution, experiement); // Édipo...
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
			    
			    //// TEste ////
				resultFront.saveVariablesToFile("VAR_" + runs + "_"); //Arquivos da arqutietura em si (.uml, .notation e .di)
				// armazena as solucoes de todas runs
				todasRuns = todasRuns.union(resultFront);

				// Thelma - Dez2013
				allSolutions = allSolutions.union(resultFront);
				resultFront.printMetricsToFile(directory + "/Metrics_" + PLAName + "_" + runs + ".txt");

			}
			// Thelma - Dez2013 - duas proximas linhas
			String NameOfPLA = getPlaName(pla);
			
			//TODO Hypervolume vai continur no arquivo TXT. Ver local onde salvar.
			allSolutions.printObjectivesToFile(directory + "/Hypervolume/"+ NameOfPLA + "/" + NameOfPLA + "_HV_" + moea + ".txt");

			todasRuns = problem.removeDominadas(todasRuns);
			todasRuns = problem.removeRepetidas(todasRuns);

			System.out.println("------    All Runs - Non-dominated solutions --------");
			todasRuns.printObjectivesToFile(directory + "/FUN_All_" + PLAName + ".txt");
			
			List<FunResults> funResults = result.getObjectives(todasRuns.getSolutionSet(), null, experiement);
			saveFunAll(funResults);
			funResults = null;
			
			List<InfoResult> infoResults = result.getInformations(todasRuns.getSolutionSet(), null, experiement);
			saveInfoAll(infoResults);
			
			todasRuns.saveVariablesToFile("VAR_All_");

			// Thelma - Dez2013
			todasRuns.printMetricsToFile(directory + "/Metrics_All_" + PLAName + ".txt");
			AllMetrics allMetrics = result.getMetrics(todasRuns.getSolutionSet(), null, experiement);
			MetricsPersistence mp = new MetricsPersistence(allMetricsPersistenceDependencies);
			mp.persisteMetrics(allMetrics);
			mp = null;
			
			//todasRuns.printAllMetricsToFile(directory + "/FUN_Metrics_All_"	+ PLAName + ".txt"); // ignorar escrita

		}
	}

	private static void saveInfoAll(List<InfoResult> infoResults) {
		InfosResultPersistence infosPersistence = new InfosResultPersistence(connection);
		try{
			for(InfoResult info : infoResults)
				infosPersistence.persistInfoDatas(info);
		}catch(SQLException e){
			e.printStackTrace();
		}
		infosPersistence = null;
	}

	private static void saveFunAll(List<FunResults> funResults) {
		FunsResultPersistence funsPersistence = new FunsResultPersistence(connection);
		try {
			for(FunResults fun : funResults)
				funsPersistence.persistFunsDatas(fun);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		funsPersistence = null;
	}

	private static Experiment createExperiementOnDb(String PLAName) {
		Experiment experiement = null;
		try {
			experiement = new Experiment(PLAName, "a description");
			experiement.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return experiement;
	}

	private static void intializeDependencies() {
		result  = new Result();
		Database.setPathToDB(PATH_TO_DB);
		
		try {
			connection = Database.getInstance().getConnection();
			allMetricsPersistenceDependencies = new AllMetricsPersistenceDependency(connection);
		} catch (ClassNotFoundException | MissingConfigurationException	| SQLException e) {
			e.printStackTrace();
		}
	}

	private static String getPlaName(String pla) {
		int beginIndex = pla.lastIndexOf("/") + 1;
		int endIndex = pla.length() - 4;
		return pla.substring(beginIndex, endIndex);
	}

}