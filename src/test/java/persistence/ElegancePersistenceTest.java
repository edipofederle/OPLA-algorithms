package persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Statement;

import metrics.Elegance;

import org.junit.Test;

import persistence.ElegancePersistence;
import results.Execution;
import database.Database;

/**
 *
 * @author elf
 */
public class ElegancePersistenceTest {
    
    @Test
    public void saveEleganceMetrics() throws Exception{
        
        Database db     = mock(Database.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);                      
        
        when(exec.getId()).thenReturn("12");
        when(db.getConnection()).thenReturn(st);
        
        Elegance eleganceMetric = new Elegance(exec);
        
        eleganceMetric.setAtmr(10d);
        eleganceMetric.setEc(20d);
        eleganceMetric.setNac(30d);
        
        ElegancePersistence elegancePersistence = new ElegancePersistence(st);
        elegancePersistence.save(eleganceMetric);
       
        String query = "insert into EleganceMetrics (nac,atmr,ec,elegance,execution_id) values (30.0,10.0,20.0,60.0,"+exec.getId()+")";
                
        verify(db.getConnection()).executeUpdate(query);
    }
    
}