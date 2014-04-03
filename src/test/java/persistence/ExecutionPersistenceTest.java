/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import metrics.AllMetrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import results.Execution;
import results.Experiment;
import results.FunResults;
import results.InfoResult;
import utils.Id;
import factories.Factory;

/**
 *
 * @author elf
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Id.class )
public class ExecutionPersistenceTest {
    
    
    
    @Test
    public void persistExecution() throws Exception{
        PowerMockito.mockStatic(Id.class);
        Connection connection                   = mock(Connection.class);
        Statement st                        	= mock(Statement.class);
        Execution fakeExecution             	= mock(Execution.class);
        Experiment fakeExperiement				= mock(Experiment.class);
        AllMetrics allMetrics					= mock(AllMetrics.class);
        InfosResultPersistence infosPersistence = mock(InfosResultPersistence.class);
        FunsResultPersistence funsPersistence 	= mock(FunsResultPersistence.class);
        AllMetricsPersistenceDependency allMetricsPersistence = mock(AllMetricsPersistenceDependency.class);
        
        when(connection.createStatement()).thenReturn(st);
        
        when(allMetricsPersistence.getConnection()).thenReturn(connection);
        when(allMetricsPersistence.getInfosPersistence()).thenReturn(infosPersistence);
        when(allMetricsPersistence.getFunsPersistence()).thenReturn(funsPersistence);
        when(fakeExperiement.getId()).thenReturn("10");
        when(fakeExecution.getAllMetrics()).thenReturn(allMetrics);
        when(fakeExecution.getExperiement()).thenReturn(fakeExperiement);
        when(fakeExperiement.getId()).thenReturn("100");
        when(fakeExecution.getTime()).thenReturn(new Long(100));
        PowerMockito.when(Id.generateUniqueId()).thenReturn("10");
        
        
		ExecutionPersistence persistence = new ExecutionPersistence(allMetricsPersistence);
        List<InfoResult> infos = Factory.givenInfos(fakeExecution, fakeExperiement);
        List<FunResults> funs = Factory.givenFuns(fakeExecution, fakeExperiement);
        
        when(fakeExecution.getId()).thenReturn("10");
        when(fakeExecution.getInfos()).thenReturn(infos);
        when(fakeExecution.getFuns()).thenReturn(funs);
        
        String query = "insert into executions (id, experiement_id, time) values (10,100,100)";
        persistence.persist(fakeExecution);      
        
        verify(st, times(1)).executeUpdate(query);
    }
    

    
}
