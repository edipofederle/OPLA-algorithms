package persistence;

import java.sql.SQLException;
import java.util.List;

import metrics.AllMetrics;
import metrics.Conventional;
import metrics.Elegance;
import metrics.FeatureDriven;
import metrics.PLAExtensibility;
import results.Execution;
import results.Experiment;
import results.FunResults;
import results.InfoResult;

public class MetricsPersistence {

	private AllMetricsPersistenceDependency allMetricsPersistenceDependencies;

	public MetricsPersistence(
			AllMetricsPersistenceDependency allMetricsPersistenceDependencies) {
		this.allMetricsPersistenceDependencies = allMetricsPersistenceDependencies;
	}
	
	
	public void saveInfoAll(List<InfoResult> infoResults) {
		InfosResultPersistence infosPersistence = new InfosResultPersistence(this.allMetricsPersistenceDependencies.getConnection());
		try{
			for(InfoResult info : infoResults)
				infosPersistence.persistInfoDatas(info);
		}catch(SQLException e){
			e.printStackTrace();
		}
		infosPersistence = null;
	}

	public void saveFunAll(List<FunResults> funResults) {
		FunsResultPersistence funsPersistence = new FunsResultPersistence(this.allMetricsPersistenceDependencies.getConnection());
		try {
			for(FunResults fun : funResults)
				funsPersistence.persistFunsDatas(fun);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		funsPersistence = null;
	}

	public Experiment createExperiementOnDb(String PLAName) {
		Experiment experiement = null;
		try {
			experiement = new Experiment(PLAName, "a description");
			experiement.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return experiement;
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