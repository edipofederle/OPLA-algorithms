package jmetal.experiments;

import java.util.List;

public class OPLAConfigs {
	
	private Integer numberOfObjectives;
	private List<String> selectedMetrics;

	public Integer getNumberOfObjectives() {
		return numberOfObjectives;
	}

	public void setNumberOfObjectives(Integer numberOfObjectives) {
		this.numberOfObjectives = numberOfObjectives;
	}

	public List<String> getSelectedMetrics() {
		return selectedMetrics;
	}

	public void setSelectedMetrics(List<String> selectedMetrics) {
		this.selectedMetrics = selectedMetrics;
	}
	
}
