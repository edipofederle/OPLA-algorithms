package algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import jmetal.experiments.FeatureMutationOperators;
import jmetal.experiments.Metrics;
import jmetal.experiments.OPLAConfigs;
import jmetal.experiments.PAES_OPLA_FeatMutInitializer;
import jmetal.experiments.PaesConfigs;
import logs.log_log.Logger;
import arquitetura.io.ReaderConfig;

public class MainTestPaes{
    
    public static void main(String args[]) {
	
	ReaderConfig.load();
	
   	//Arquitetura(s) de entrada
   	//String plas = "/Users/elf/NetBeansProjects/OPLA-Patterns/MicrowaveOvenSoftware/Papyrus/MicrowaveOvenSoftware.uml";
	String plas = "/Users/elf/mestrado/sourcesMestrado/architecture-representation/src/test/java/resources/agmfinal/agm.uml";
   	//Lista de Operadores de mutação a serem utilizados
   	List<String> operators =  new LinkedList<String>(Arrays.asList(FeatureMutationOperators.ADD_CLASS_MUTATION.getOperatorName(),
   		FeatureMutationOperators.MOVE_ATTRIBUTE_MUTATION.getOperatorName(),
   		FeatureMutationOperators.FEATURE_MUTATION.getOperatorName(),
   		FeatureMutationOperators.ADD_MANAGER_CLASS_MUTATION.getOperatorName(),
   		FeatureMutationOperators.MOVE_METHOD_MUTATION.getOperatorName(),
   		FeatureMutationOperators.MOVE_OPERATION_MUTATION.getOperatorName()));
   	
   	//Intancia a classe de configuracoes
   	PaesConfigs configs = new PaesConfigs();

   	// Seta os parametros desejados
   	configs.setMutationOperators(operators);
   	configs.setPlas(plas);
   	configs.setNumberOfRuns(30);
   	//configs.setPopulationSize(10); // somente no nsgaii, migrar para classe concreta
   	configs.setMaxEvaluations(1000);
   	configs.disableCrossover();
   	configs.setMutationProbability(0.9);
   	configs.setArchiveSize(100);
   	configs.setDescription("max evaluations 70");
   	
   	//Logs
   	configs.activeLogs();
   	Logger.addListener(new ListenerLog());
   	configs.setLogger(Logger.getLogger());
   	
   	//Configura onde o db esta localizado
   	configs.setPathToDb("/Users/elf/oplatool/db/oplatool.db");
   	
   	//Instancia a classe de configuracao da OPLA.java
   	OPLAConfigs oplaConfig = new OPLAConfigs();
   	
	//Quais funções objetivo deseja-se utilizar
	List<String> selectedObjectiveFunctions = Arrays.asList(Metrics.CONVENTIONAL.getName(), Metrics.FEATURE_DRIVEN.getName());
	
   	oplaConfig.setSelectedObjectiveFunctions(selectedObjectiveFunctions);

   	//Add as confs de OPLA na classe de configuracoes gerais.
   	configs.setOplaConfigs(oplaConfig);

   	//Utiliza a classe Initializer do NSGAII passando as configs.
   	PAES_OPLA_FeatMutInitializer paes = new PAES_OPLA_FeatMutInitializer(configs);

   	//Executa
   	paes.run();

       }

}
