package persistence;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import metrics.Conventional;
import metrics.Elegance;
import metrics.FeatureDriven;
import metrics.PLAExtensibility;
import results.Execution;
import results.FunResults;
import results.InfoResult;

/**
 *
 * @author elf
 */
public class ExecutionPersistence {
    
    private final Statement statement;
    private final InfosResultPersistence infosPersistence;
    private final FunsResultPersistence funsPersistence;
    private final ElegancePersistence elegancePersistence;
    private final FeatureDrivenPersistence featureDrivenPersistence;
    private final ConventionalPersistence conventionalPersistence;
    private final PLAExtensibilityPersistence plaExtensibilityPersistence;

    public void persist(Execution fakeExecution) throws SQLException {
       StringBuilder query = new StringBuilder();
       query.append("insert into executions (id, experiement_id, time) values ");
       query.append("(");
       query.append(fakeExecution.getId());
       query.append(",");
       query.append(fakeExecution.getExperiement().getId());
       query.append(",");
       query.append(fakeExecution.getTime());
       query.append(")");
        
       statement.executeUpdate(query.toString());
       
       if (fakeExecution.getInfos() != null){
    	   for(InfoResult ir : fakeExecution.getInfos())
    		   this.infosPersistence.persistInfoDatas(ir);
       }
       
       if(fakeExecution.getFuns() != null){
    	   for(FunResults fr : fakeExecution.getFuns())
    		   this.funsPersistence.persistFunsDatas(fr);
       }
       
       List<Elegance> elegances = fakeExecution.getAllMetrics().getElegance();
       if(!elegances.isEmpty()){
    	   for(Elegance elegance : elegances)
    		   elegancePersistence.save(elegance);
       }
       elegances = null;
       
       List<FeatureDriven> featuresDriven = fakeExecution.getAllMetrics().getFeatureDriven();
       if(!featuresDriven.isEmpty()){
    	   for(FeatureDriven fd : featuresDriven)
    		   featureDrivenPersistence.save(fd);
       }
       featuresDriven = null;
       
       List<Conventional> conventionals = fakeExecution.getAllMetrics().getConventional();
       if(!conventionals.isEmpty()){
    	   for(Conventional conv : conventionals)
    		   conventionalPersistence.save(conv);
       }
       conventionals = null;
       
       List<PLAExtensibility> plaExt = fakeExecution.getAllMetrics().getPlaExtensibility();
       if(!plaExt.isEmpty()){
    	   for(PLAExtensibility ext : plaExt)
    		   plaExtensibilityPersistence.save(ext);
       }
       
       plaExt = null;
       
    }

    public ExecutionPersistence(Statement st) {
        this.statement = st;
        this.infosPersistence = new InfosResultPersistence(st);
        this.funsPersistence = new FunsResultPersistence(st);
        this.elegancePersistence = new ElegancePersistence(st);
        this.featureDrivenPersistence = new FeatureDrivenPersistence(st);
        this.conventionalPersistence = new ConventionalPersistence(st);
        this.plaExtensibilityPersistence = new PLAExtensibilityPersistence(st);
    }

}
