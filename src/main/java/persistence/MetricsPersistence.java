package persistence;

import java.util.List;

import metrics.AllMetrics;
import metrics.Conventional;
import metrics.Elegance;
import metrics.FeatureDriven;
import metrics.PLAExtensibility;
import results.Execution;

public class MetricsPersistence {

	private AllMetricsPersistenceDependency allMetricsPersistenceDependencies;

	public MetricsPersistence(
			AllMetricsPersistenceDependency allMetricsPersistenceDependencies) {
		this.allMetricsPersistenceDependencies = allMetricsPersistenceDependencies;
	}

	public void persisteMetrics(Execution execution) {
		persisteElegance(execution.getAllMetrics().getElegance());
		persisteFeatureDriven(execution.getAllMetrics().getFeatureDriven());
		persisteConventional(execution.getAllMetrics().getConventional());
		persistePlaExtensibility(execution.getAllMetrics()
				.getPlaExtensibility());
	}

	public void persisteMetrics(AllMetrics allMetrics) {
		persisteElegance(allMetrics.getElegance());
		persisteFeatureDriven(allMetrics.getFeatureDriven());
		persisteConventional(allMetrics.getConventional());
		persistePlaExtensibility(allMetrics.getPlaExtensibility());
	}

	private void persistePlaExtensibility(List<PLAExtensibility> plaExt) {
		if (!plaExt.isEmpty()) {
			for (PLAExtensibility ext : plaExt)
				this.allMetricsPersistenceDependencies
						.getPlaExtensibilityPersistence().save(ext);
		}

		plaExt = null;
	}

	private void persisteConventional(List<Conventional> conventionals) {
		if (!conventionals.isEmpty()) {
			for (Conventional conv : conventionals)
				this.allMetricsPersistenceDependencies
						.getConventionalPersistence().save(conv);
		}
		conventionals = null;
	}

	private void persisteFeatureDriven(List<FeatureDriven> featuresDriven) {
		if (!featuresDriven.isEmpty()) {
			for (FeatureDriven fd : featuresDriven)
				this.allMetricsPersistenceDependencies
						.getFeatureDrivenPersistence().save(fd);
		}
		featuresDriven = null;
	}

	private void persisteElegance(List<Elegance> elegances) {
		if (!elegances.isEmpty()) {
			for (Elegance elegance : elegances)
				this.allMetricsPersistenceDependencies.getElegancePersistence()
						.save(elegance);
		}
		elegances = null;
	}
}