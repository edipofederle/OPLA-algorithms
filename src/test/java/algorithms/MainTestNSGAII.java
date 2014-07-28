package algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import jmetal.experiments.FeatureMutationOperators;
import jmetal.experiments.Metrics;
import jmetal.experiments.NSGAIIConfig;
import jmetal.experiments.NSGAII_OPLA_FeatMutInitializer;
import jmetal.experiments.OPLAConfigs;
import arquitetura.io.ReaderConfig;

/**
 * Classe exemplo de como usar o NSGA-II.
 * 
 * @author elf
 *
 */
public class MainTestNSGAII {

    public static void main(String args[]) {
	
	ReaderConfig.load(); //Carrega o arquivo de configuração (application.yaml)
	
	//Arquitetura(s) de entrada
	String plas = "/Users/elf/NetBeansProjects/OPLA-Patterns/MicrowaveOvenSoftware/Papyrus/MicrowaveOvenSoftware.uml";

	//Lista de Operadores de mutação a serem utilizados
	List<String> operators =  new LinkedList<String>(Arrays.asList(FeatureMutationOperators.ADD_CLASS_MUTATION.getOperatorName(),
		FeatureMutationOperators.MOVE_ATTRIBUTE_MUTATION.getOperatorName(),
		FeatureMutationOperators.FEATURE_MUTATION.getOperatorName(),
		FeatureMutationOperators.ADD_MANAGER_CLASS_MUTATION.getOperatorName(),
		FeatureMutationOperators.MOVE_METHOD_MUTATION.getOperatorName(),
		FeatureMutationOperators.MOVE_OPERATION_MUTATION.getOperatorName(),
		FeatureMutationOperators.DESIGN_PATTERNS.getOperatorName()));
	
	//Intancia a classe de configuracoes
	NSGAIIConfig configs = new NSGAIIConfig();

	// Seta os parametros desejados
	configs.setMutationOperators(operators);
	configs.setPlas(plas);
	configs.setNumberOfRuns(3);
	configs.setPopulationSize(30);
	configs.setMaxEvaluations(100);
	configs.disableCrossover();
	configs.setMutationProbability(0.9);
	
	// OPLA-Patterns Configurations
	configs.setPatterns("Mediator", "Strategy", "Bridge");
	configs.setDesignPatternStrategy(null);
	
	//Configura onde o db esta localizado
	configs.setPathToDb("/Users/elf/oplatool/db/oplatool.db");
	
	//Instancia a classe de configuracao da OPLA.java
	OPLAConfigs oplaConfig = new OPLAConfigs();
	
	
	//Quais funções objetivo deseja-se utilizar
	List<String> selectedObjectiveFunctions = Arrays.asList(
		Metrics.ELEGANCE.getName(),
		Metrics.CONVENTIONAL.getName(),
		Metrics.PLA_EXTENSIBILIY.getName(),
		Metrics.FEATURE_DRIVEN.getName());
	
	oplaConfig.setSelectedObjectiveFunctions(selectedObjectiveFunctions);

	//Add as confs de OPLA na classe de configuracoes gerais.
	configs.setOplaConfigs(oplaConfig);

	//Utiliza a classe Initializer do NSGAII passando as configs.
	NSGAII_OPLA_FeatMutInitializer nsgaii = new NSGAII_OPLA_FeatMutInitializer(configs);

	//Executa
	nsgaii.run();	

    }

}
