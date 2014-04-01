package persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Statement;

import metrics.FeatureDriven;

import org.junit.Test;

import persistence.FeatureDrivenPersistence;
import results.Execution;
import results.Experiment;
import database.Database;

/**
 *
 * @author elf
 */
public class FeatureDriverPersistenceTest {
    
    @Test
    public void saveFeatureDrivenMetrics() throws Exception{
        
        Database db     = mock(Database.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);     
        Experiment exp = mock(Experiment.class);
        
        when(exec.getId()).thenReturn("12");
        when(db.getConnection()).thenReturn(st);
        
        FeatureDrivenPersistence persistence = new FeatureDrivenPersistence(st);
        
        FeatureDriven fd = new FeatureDriven(exec, exp);
        
        fd.setCdaClass(10d);
        fd.setCdac(1d);
        fd.setCdai(3d);
        fd.setCdao(3d);
        fd.setCibClass(1d);
        fd.setCibc(1d);
        fd.setIibc(12d);
        fd.setLcc(12d);
        fd.setLccClass(3d);
        fd.setOobc(4d);
        
        String query = buildQuery(fd);
        
              
        persistence.save(fd);
        
        verify(db.getConnection()).executeUpdate(query);
        
    }
    
    private String buildQuery(FeatureDriven fd){
        StringBuilder query = new StringBuilder();
        query.append("insert into FeatureDrivenMetrics (msiAggregation, cdac, cdai, cdao, cibc, iibc, oobc, lcc, lccClass, cdaClass, cibClass, execution_id, is_all, experiement_id) values (");
        query.append(fd.getMsiAggregation());
        query.append(",");
        query.append(fd.getCdac());
        query.append(",");
        query.append(fd.getCdai());
        query.append(",");
        query.append(fd.getCdao());
        query.append(",");
        query.append(fd.getCibc());
        query.append(",");
        query.append(fd.getIibc());
        query.append(",");
        query.append(fd.getOobc());
        query.append(",");
        query.append(fd.getLcc());
        query.append(",");
        query.append(fd.getLccClass());
        query.append(",");
        query.append(fd.getCdaClass());
        query.append(",");
        query.append(fd.getCibClass());
        query.append(",");
        query.append(fd.getExecution().getId());
        query.append(",");
        query.append("0");
        query.append(",");
        query.append("null");
        query.append(")");
        
        return query.toString();
    }
    
}
