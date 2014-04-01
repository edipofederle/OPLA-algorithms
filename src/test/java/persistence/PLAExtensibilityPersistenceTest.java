package persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Statement;

import metrics.PLAExtensibility;

import org.junit.Test;

import persistence.PLAExtensibilityPersistence;
import results.Execution;
import database.Database;

/**
 *
 * @author elf
 */
public class PLAExtensibilityPersistenceTest {
    
    @Test
    public void shouldSavePLAExtensibilityMetrics() throws Exception{
        
        Database db     = mock(Database.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);                      
        
        when(exec.getId()).thenReturn("10");
        when(db.getConnection()).thenReturn(st);
        
        PLAExtensibility plaext = new PLAExtensibility(exec);
        
        PLAExtensibilityPersistence persistence = new PLAExtensibilityPersistence(st);
        
        plaext.setPlaExtensibility(10d);
        
        persistence.save(plaext);
        String query = "insert into PLAExtensibilityMetrics (plaExtensibility, execution_id) values (10.0,10)";
        
        verify(db.getConnection(), times(1)).executeUpdate(query);
        
        
        
    }
}
