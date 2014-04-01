package persistence;

import java.sql.Statement;

public class AllMetricsPersistenceDependency {
	
	
	private Statement statement;
	private InfosResultPersistence infosPersistence;
	private FunsResultPersistence funsPersistence;
	private ElegancePersistence elegancePersistence;
	private FeatureDrivenPersistence featureDrivenPersistence;
	private ConventionalPersistence conventionalPersistence;
	private PLAExtensibilityPersistence plaExtensibilityPersistence;

	public AllMetricsPersistenceDependency(Statement st){
        this.statement = st;
        this.infosPersistence = new InfosResultPersistence(st);
        this.funsPersistence = new FunsResultPersistence(st);
        this.elegancePersistence = new ElegancePersistence(st);
        this.featureDrivenPersistence = new FeatureDrivenPersistence(st);
        this.conventionalPersistence = new ConventionalPersistence(st);
        this.plaExtensibilityPersistence = new PLAExtensibilityPersistence(st);
	}

	public Statement getStatement() {
		return statement;
	}

	public InfosResultPersistence getInfosPersistence() {
		return infosPersistence;
	}

	public FunsResultPersistence getFunsPersistence() {
		return funsPersistence;
	}

	public ElegancePersistence getElegancePersistence() {
		return elegancePersistence;
	}

	public FeatureDrivenPersistence getFeatureDrivenPersistence() {
		return featureDrivenPersistence;
	}

	public ConventionalPersistence getConventionalPersistence() {
		return conventionalPersistence;
	}

	public PLAExtensibilityPersistence getPlaExtensibilityPersistence() {
		return plaExtensibilityPersistence;
	}

}
