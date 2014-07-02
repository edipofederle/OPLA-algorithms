package algorithms;

import java.util.Arrays;
import java.util.List;

import arquitetura.io.ReaderConfig;
import jmetal.experiments.FeatureMutationOperators;
import jmetal.experiments.Metrics;
import jmetal.experiments.NSGAIIConfig;
import jmetal.experiments.NSGAII_OPLA_FeatMutInitializer;
import jmetal.experiments.OPLAConfigs;

public class MainTestNSGAII {

    public static void main(String args[]) {
	
	ReaderConfig.load();
	
	//Arquitetura(s) de entrada
	String plas = "/Users/elf/mestrado/sourcesMestrado/arquitetura/src/test/java/resources/agmfinal/agm.uml";

	//Lista de Operadores de mutação a serem utilizados
	List<String> operators =  Arrays.asList(FeatureMutationOperators.ADD_CLASS_MUTATION.getOperatorName(),
		FeatureMutationOperators.MOVE_ATTRIBUTE_MUTATION.getOperatorName(),
		FeatureMutationOperators.FEATURE_MUTATION.getOperatorName(),
		FeatureMutationOperators.ADD_MANAGER_CLASS_MUTATION.getOperatorName(),
		FeatureMutationOperators.MOVE_METHOD_MUTATION.getOperatorName(),
		FeatureMutationOperators.MOVE_OPERATION_MUTATION.getOperatorName());
	
	//Intancia a classe de configuracoes
	NSGAIIConfig configs = new NSGAIIConfig();
	

	// Seta os parametros desejados
	configs.setMutationOperators(operators);
	configs.setPlas(plas);
	configs.setNumberOfRuns(10);
	configs.setPopulationSize(15);
	configs.setMaxEvaluations(100);
	configs.disableCrossover();
	configs.setMutationProbability(0.9);
	
	//Configura onde o db esta localizado
	configs.setPathToDb("/Users/elf/oplatool/db/oplatool.db");
	
	//Instancia a classe de configuracao da OPLA.java
	OPLAConfigs oplaConfig = new OPLAConfigs();
	
	
	//Quais metricas deseja-se utilizar
	List<String> selectedMetrics = Arrays.asList(
		Metrics.ELEGANCE.getName(),
		Metrics.CONVENTIONAL.getName(),
		Metrics.PLA_EXTENSIBILIY.getName(),
		Metrics.FEATURE_DRIVEN.getName());
	
	oplaConfig.setSelectedMetrics(selectedMetrics);
	//Numero de objetivos
	oplaConfig.setNumberOfObjectives(selectedMetrics.size());

	//Add as confs de OPLA na classe de configuracoes gerais.
	configs.setOplaConfigs(oplaConfig);

	//Utiliza a classe Initializer do NSGAII passando as configs.
	NSGAII_OPLA_FeatMutInitializer nsgaii = new NSGAII_OPLA_FeatMutInitializer(configs);

	//Executa
	nsgaii.run();	

    }

}
