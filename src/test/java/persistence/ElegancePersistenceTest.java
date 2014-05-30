package persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Statement;

import metrics.Elegance;

import org.junit.Test;

import results.Execution;
import results.Experiment;

/**
 *
 * @author elf
 */
public class ElegancePersistenceTest {
    
    @Test
    public void saveEleganceMetrics() throws Exception{
        
        Statement st = mock(Statement.class);
        Connection connection = mock(Connection.class);
        Execution exec  = mock(Execution.class);    
        Experiment exp = mock(Experiment.class);
        
        when(exec.getId()).thenReturn("12");
        when(connection.createStatement()).thenReturn(st);
        
        Elegance eleganceMetric = new Elegance("1", exec, exp);
        
        eleganceMetric.setAtmr(10d);
        eleganceMetric.setEc(20d);
        eleganceMetric.setNac(30d);
        
        ElegancePersistence elegancePersistence = new ElegancePersistence(connection);
        elegancePersistence.save(eleganceMetric);
       
        String query = "insert into EleganceMetrics (nac,atmr,ec,elegance,execution_id, experiement_id, is_all, id_solution) values (30.0,10.0,20.0,60.0,"+exec.getId()+",null,0,1)";
                
        verify(st).executeUpdate(query);
    }
    
}