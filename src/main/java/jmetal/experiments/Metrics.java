package jmetal.experiments;

public enum Metrics {
	
	CONVENTIONAL("conventional"),
	ELEGANCE("elegance"),
	FEATURE_DRIVEN("featureDriven"),
	PLA_EXTENSIBILIY("PLAExtensibiliy");
	
	private String name;
	
	private Metrics(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}

}
