package database;

import java.util.ArrayList;
import java.util.List;

import jmetal.core.Solution;
import jmetal.metrics.MetricsEvaluation;
import metrics.AllMetrics;
import metrics.Conventional;
import metrics.Elegance;
import metrics.FeatureDriven;
import metrics.PLAExtensibility;
import results.Execution;
import results.Experiment;
import results.FunResults;
import results.InfoResult;
import arquitetura.representation.Architecture;
import arquitetura.representation.Concern;

public class Result {
	
	private String plaName;
	
	
	public void setPlaName(String plaName){
		this.plaName = plaName;
	}
	
	/**
	 * Returns {@link FunResults} given a {@link List} of {@link Solution}
	 * and a executionId.<br />
	 * 
	 * Pass null to execution when are ALL results. So results belongs to experiement
	 * and not to execution.
	 * 
	 * See {@link ResultTest} for more details
	 * 
	 * @param list
	 * @param experiement 
	 * @param execution
	 * @return
	 */
	public List<FunResults> getObjectives(List<Solution> list, Execution execution, Experiment experiement) {
		
		List<FunResults> funResults = new ArrayList<FunResults>();
		
		for (int i = 0; i < list.size(); i++){
			String sb = (list.get(i).toString().trim()).replace(" ", "|");

			FunResults funResult = new FunResults();
			funResult.setName(plaName + "_" + funResult.getId() );
			funResult.setExecution(execution);
			funResult.setExperiement(experiement);
			if(execution == null)
				funResult.setIsAll(1);
			funResult.setObjectives(sb.replaceAll("\\s+",""));
			funResults.add(funResult);
		}
		
		return funResults;
	}

	public List<InfoResult> getInformations(List<Solution> solutionsList, Execution execution, Experiment experiement) {
		
		List<InfoResult> infoResults = new ArrayList<InfoResult>();
		
		
		for (int i = 0; i < solutionsList.size(); i++) {
			int numberOfVariables = solutionsList.get(0).getDecisionVariables().length;
				
			for (int j = 0; j < numberOfVariables; j++) {
				Architecture arch = (Architecture) solutionsList.get(i).getDecisionVariables()[j];
				
				InfoResult ir = new InfoResult();
				ir.setExecution(execution);
				ir.setExperiement(experiement);
				if(execution == null)
					ir.setIsAll(1);
				ir.setName(plaName + "_" + ir.getId() );
				ir.setListOfConcerns(getListOfConcerns(arch.getAllConcerns()));
				ir.setNumberOfPackages(arch.getAllPackages().size());
				ir.setNumberOfVariabilities(arch.getAllVariabilities().size());
				ir.setNumberOfInterfaces(arch.getAllInterfaces().size());
				ir.setNumberOfClasses(arch.getAllClasses().size());
				ir.setNumberOfDependencies(arch.getRelationshipHolder().getAllDependencies().size());
				ir.setNumberOfAbstraction(arch.getRelationshipHolder().getAllAbstractions().size());
				ir.setNumberOfGeneralizations(arch.getRelationshipHolder().getAllGeneralizations().size());
				ir.setNumberOfAssociations(arch.getRelationshipHolder().getAllAssociationsRelationships().size());
				ir.setNumberOfassociationsClass(arch.getRelationshipHolder().getAllAssociationsClass().size());
				
				
				infoResults.add(ir);
			}
		}
		
		return infoResults;
	}

	/**
	 * Returns all concern formated like: concer1|concern2|...
	 * 
	 * @param allConcerns
	 * 
	 * @return String
	 */
	private String getListOfConcerns(List<Concern> allConcerns) {
		StringBuilder concernsList = new StringBuilder();
		for(Concern concern : allConcerns)
			concernsList.append(concern.getName()).append("|");
		
		return concernsList.substring(0, concernsList.length()-1);
	}

	public AllMetrics getMetrics(List<Solution> list, Execution execution, Experiment experiement) {
		
		MetricsEvaluation metrics = new MetricsEvaluation();
		AllMetrics allMetrics = new AllMetrics();
		int numberOfVariables = list.get(0).getDecisionVariables().length;

		//TODO Ver com Thelma: Somente gerar metrica que foram selecionadas na GUI. via (OPLAConfigs)
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < numberOfVariables; j++) {
				Architecture arch = (Architecture) list.get(i).getDecisionVariables()[j];
				allMetrics.getElegance().add(buildEleganceMetrics(execution, experiement, metrics, arch));
				allMetrics.getPlaExtensibility().add(buildPLAExtensibilityMetrics(execution, experiement, metrics, arch));
				allMetrics.getConventional().add(buildConventionalMetrics(execution, experiement, metrics, arch));
				allMetrics.getFeatureDriven().add(buildFeatureDrivenMetrics(execution, experiement, metrics, arch));
				
			}
		}
		
		return allMetrics;
	}

	private FeatureDriven buildFeatureDrivenMetrics(Execution execution, Experiment experiement, MetricsEvaluation metrics, Architecture arch) {
		FeatureDriven fd = new FeatureDriven(execution, experiement);
		
		fd.setCdac(metrics.evaluateCDAC(arch));
		fd.setCdai(metrics.evaluateCDAI(arch));
		fd.setCdao(metrics.evaluateCDAO(arch));
		fd.setCibc(metrics.evaluateCIBC(arch));
		fd.setIibc(metrics.evaluateIIBC(arch));
		fd.setOobc(metrics.evaluateOOBC(arch));
		fd.setLcc(metrics.evaluateLCC(arch));
		fd.setLccClass(metrics.evaluateLCCClass(arch));
		fd.setCdaClass(metrics.evaluateCDAClass(arch));
		fd.setCibClass(metrics.evaluateCIBClass(arch));
		
		return fd;
	}

	private Conventional buildConventionalMetrics(Execution execution, Experiment experiement, MetricsEvaluation metrics, Architecture arch) {
		Conventional conventional = new Conventional(execution, experiement);
		
		conventional.setChoesion(metrics.evaluateCohesion(arch));
		conventional.setMeanDepComps(metrics.evaluateMeanDepComps(arch));
		conventional.setMeanNumOps(metrics.evaluateMeanNumOps(arch));
		conventional.setSumClassesDepIn(metrics.evaluateSumClassesDepIn(arch));
		conventional.setSumClassesDepOut(metrics.evaluateSumClassesDepOut(arch));
		conventional.setSumDepIn(metrics.evaluateSumDepIn(arch));
		conventional.setSumDepOut(metrics.evaluateSumDepOut(arch));
		
		return conventional;
	}

	private PLAExtensibility buildPLAExtensibilityMetrics(Execution execution,
			Experiment experiement, MetricsEvaluation metrics, Architecture arch) {
		PLAExtensibility plaExtensibility = new PLAExtensibility(execution, experiement);
		plaExtensibility.setPlaExtensibility(metrics.evaluatePLAExtensibility(arch));
		return plaExtensibility;
	}

	private Elegance buildEleganceMetrics(Execution execution, Experiment experiement, MetricsEvaluation metrics, Architecture arch) {
		Elegance elegance = new Elegance(execution, experiement);
		elegance.setNac(metrics.evaluateNACElegance(arch));
		elegance.setAtmr(metrics.evaluateATMRElegance(arch));
		elegance.setEc(metrics.evaluateECElegance(arch));
		
		return elegance;
	}

}
