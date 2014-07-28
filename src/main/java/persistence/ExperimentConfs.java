package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import jmetal.experiments.NSGAIIConfig;
import database.Database;
import exceptions.MissingConfigurationException;

/**
 * Classe responsável por guardar E persistir/recuperar informções referentes a
 * qual configuração o experimento utilizou.
 * 
 * Ex: numero de rodas, funcoes objetivos utilizadas e assim por diante.
 * 
 * @author elf
 * 
 */
public class ExperimentConfs {

    private String experimentId;
    private NSGAIIConfig configs;
    private String algorithm;

    public ExperimentConfs(String experimentId, String algorithm, NSGAIIConfig configs) {
	this.experimentId = experimentId;
	this.configs = configs;
	this.algorithm = algorithm;
    }

    public void save() {
	persist();
    }

    /**
     * Return a {@link HashMap<String, String>} with configuration settings given a experimentId.
     * 
     * @param experimentId
     * @return {@link HashMap<String, String>}
     */
    public static HashMap<String, String> getConfigs(String experimentId) {

	HashMap<String, String> confs = new HashMap<>();

	StringBuilder query = new StringBuilder();
	query.append("SELECT * FROM experiment_configurations where experiment_id = ");
	query.append(experimentId);

	try {
	    Statement stat = Database.getConnection().createStatement();

	    ResultSet result = stat.executeQuery(query.toString());
	    while (result.next()) {
		confs.put("numberOfRuns", result.getString("number_of_runs"));
		confs.put("maxEvaluations", result.getString("max_evaluations"));
		confs.put("crossover_prob", result.getString("crossover_prob"));
		confs.put("mutation_prob", result.getString("mutation_prob"));
		confs.put("mutation_prob", result.getString("mutation_prob"));
		confs.put("mutation_operators", result.getString("mutation_operators"));
		confs.put("patterns", result.getString("patterns"));
		confs.put("pattern_strategy", result.getString("pattern_strategy"));
		confs.put("algorithm", result.getString("algorithm"));
		stat.close();
		confs.put("objective_functions", getObjectiveFunctionsForExperiment(experimentId));

	    }

	} catch (SQLException | ClassNotFoundException | MissingConfigurationException e) {
	    e.printStackTrace();
	}

	return confs;
    }

    private static String getObjectiveFunctionsForExperiment(String experimentId) throws ClassNotFoundException,
	    SQLException, MissingConfigurationException {
	Statement stat = Database.getConnection().createStatement();

	StringBuilder query = new StringBuilder();

	query.append("SELECT * FROM map_objectives_names where experiment_id = ");
	query.append(experimentId);

	ResultSet result = stat.executeQuery(query.toString());
	String funcs = result.getString("names");
	stat.close();
	return funcs;
    }

    private void persist() {
	try {
	    Statement stat = Database.getConnection().createStatement();

	    StringBuilder query = new StringBuilder();
	    StringBuilder patternsList = new StringBuilder();
	    StringBuilder mutationOperatorsList = new StringBuilder();

	    if (configs.getMutationOperators().contains("DesignPatterns")) {
		for (String p : configs.getPatterns()) {
		    patternsList.append(p);
		    patternsList.append(",");
		}
	    }

	    for (String operator : configs.getMutationOperators()) {
		mutationOperatorsList.append(operator);
		mutationOperatorsList.append(",");
	    }

	    patternsList = removeLastComma(patternsList);
	    mutationOperatorsList = removeLastComma(mutationOperatorsList);

	    query.append("INSERT into experiment_configurations (experiment_id, number_of_runs,"
		    + " max_evaluations, crossover_prob, mutation_prob, patterns, pattern_strategy, algorithm, mutation_operators) VALUES (");
	    query.append(experimentId);
	    query.append(",");
	    query.append(configs.getNumberOfRuns());
	    query.append(",");
	    query.append(configs.getMaxEvaluation());
	    query.append(",");
	    query.append(configs.getCrossoverProbability());
	    query.append(",");
	    query.append(configs.getMutationProbability());
	    query.append(",");
	    query.append("'");
	    query.append(patternsList);
	    query.append("'");
	    query.append(",");
	    query.append("'");
	    query.append(getDesignPatternStrategy());
	    query.append("'");
	    query.append(",");
	    query.append("'");
	    query.append(algorithm);
	    query.append("'");
	    query.append(",");
	    query.append("'");
	    query.append(mutationOperatorsList);
	    query.append("'");
	    query.append(")");

	    stat.execute(query.toString());
	    stat.close();
	} catch (SQLException | ClassNotFoundException | MissingConfigurationException e) {
	    e.printStackTrace();
	}
    }

    private StringBuilder removeLastComma(StringBuilder list) {
	list.delete(list.length() - 1, list.length());
	return list;
    }

    private String getDesignPatternStrategy() {
	if (configs.getMutationOperators().contains("DesignPatterns")) {
	    if (configs.getDesignPatternStrategy() == null)
		return "Random";
	    if (configs.getDesignPatternStrategy() != null)
		return "Element with same design patterns or none";
	}
	return "";
    }

}
