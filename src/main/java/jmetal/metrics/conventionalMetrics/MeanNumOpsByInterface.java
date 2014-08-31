package jmetal.metrics.conventionalMetrics;
import arquitetura.representation.Architecture;
import arquitetura.representation.Interface;


public class MeanNumOpsByInterface {

	private Architecture architecture;
	private int results;
	private int numberInterfaces;
	
	public MeanNumOpsByInterface(Architecture architecture) {
		this.architecture = architecture;
		this.results = 0;
		this.numberInterfaces= 0;
		
		numberInterfaces = this.architecture.getAllInterfaces().size();
		
		for (arquitetura.representation.Package component : this.architecture.getAllPackages()) {
			for (Interface itf: component.getImplementedInterfaces() ){
			    this.results += itf.getOperations().size();
			}
		}
		this.results = results / numberInterfaces;
	}
	
	public double getResults() {
		return results;
	}	
	
}
