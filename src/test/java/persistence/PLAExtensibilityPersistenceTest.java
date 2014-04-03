package persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Statement;

import metrics.PLAExtensibility;

import org.junit.Test;

import results.Execution;
import results.Experiment;

/**
 *
 * @author elf
 */
public class PLAExtensibilityPersistenceTest {
    
    @Test
    public void shouldSavePLAExtensibilityMetrics() throws Exception{
        
    	Connection connection = mock(Connection.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);                      
        Experiment exp = mock(Experiment.class);
        
        when(exec.getId()).thenReturn("10");
        when(connection.createStatement()).thenReturn(st);
        
        PLAExtensibility plaext = new PLAExtensibility(exec,exp);
        
        PLAExtensibilityPersistence persistence = new PLAExtensibilityPersistence(connection);
        
        plaext.setPlaExtensibility(10d);
        
        persistence.save(plaext);
        String query = "insert into PLAExtensibilityMetrics (plaExtensibility, execution_id, is_all, experiement_id) values (10.0,10,0,null)";
        
        verify(st, times(1)).executeUpdate(query);
    }
}
