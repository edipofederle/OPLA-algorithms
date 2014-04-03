/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.junit.Test;

import results.Execution;
import results.Experiment;
import results.FunResults;
import factories.Factory;

/**
 *
 * @author elf
 */
public class FunResultsPersistenceTest {
    
    @Test
    public void persistFunsDatas() throws Exception{
        Connection connection = mock(Connection.class);
        Statement st = mock(Statement.class);
        Execution fakeExecution  = mock(Execution.class);
        Experiment fakeExperiement = mock(Experiment.class);
        
        when(connection.createStatement()).thenReturn(st);
        
        FunsResultPersistence persistence = new FunsResultPersistence(connection);
        
        List<FunResults> funs = Factory.givenFuns(fakeExecution, fakeExperiement);
        
        persistence.persistFunsDatas(funs.get(0));
        
        String query = "insert into objectives (id, execution_id, objectives, is_all, experiement_id) values ("+ funs.get(0).getId()+","+funs.get(0).getExecution().getId()+",'"+funs.get(0).getObjectives()+"',"+funs.get(0).getIsAll()+","+funs.get(0).getExperiement().getId()+")";
        verify(st).executeUpdate(query);
    }
}
