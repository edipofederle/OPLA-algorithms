/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Test;

import results.Execution;
import results.Experiment;
import results.InfoResult;

public class InfoResultsPersistenceTest {
    
    @Test
    public void shouldNotBeNull() throws  Exception{
        Connection connection = mock(Connection.class);
        Statement st = mock(Statement.class);
        when(connection.createStatement()).thenReturn(st);
        InfosResultPersistence persistence = new InfosResultPersistence(connection);
        assertNotNull(persistence);
    }

    
    @Test
    public void persistInfosDatas() throws Exception{
    	Connection connection = mock(Connection.class);
        Statement st = mock(Statement.class);
        Execution fakeExecution = mock(Execution.class);
        Experiment fakeExperiement = mock(Experiment.class);
        
        when(fakeExecution.getId()).thenReturn("1");
        when(fakeExperiement.getId()).thenReturn("10");
        
        when(connection.createStatement()).thenReturn(st);
        
        InfosResultPersistence persistence = new InfosResultPersistence(connection);
        
        InfoResult fakeInfoResult = mock(InfoResult.class);
        
        when(fakeInfoResult.getId()).thenReturn("1");
        when(fakeInfoResult.getListOfConcerns()).thenReturn("concern1 | concern2");
        when(fakeInfoResult.getExecution()).thenReturn(fakeExecution);
        when(fakeInfoResult.getExperiement()).thenReturn(fakeExperiement);
        when(fakeInfoResult.getName()).thenReturn("INFO_AGM_1");
        when(fakeInfoResult.getNumberOfPackages()).thenReturn(10);
        when(fakeInfoResult.getNumberOfVariabilities()).thenReturn(5);
        when(fakeInfoResult.getNumberOfInterfaces()).thenReturn(6);
        when(fakeInfoResult.getNumberOfClasses()).thenReturn(20);
        when(fakeInfoResult.getNumberOfDependencies()).thenReturn(2);
        when(fakeInfoResult.getNumberOfAbstraction()).thenReturn(3);
        when(fakeInfoResult.getNumberOfGeneralizations()).thenReturn(3);
        when(fakeInfoResult.getNumberOfAssociations()).thenReturn(5);
        when(fakeInfoResult.getNumberOfassociationsClass()).thenReturn(1);
        
        persistence.persistInfoDatas(fakeInfoResult);
        
        String query = "insert into infos(id, execution_id, name,"
                + " list_of_concerns, number_of_packages, number"
                + "_of_variabilities, number_of_interfaces, number_of_classes,"
                + " number_of_dependencies, number_of_abstractions,"
                + " number_of_generalizations, number_of_associations,"
                + " number_of_associations_class, is_all, experiement_id)"
                + " values (1,1,'INFO_AGM_1',"
                + "'concern1 | concern2',10,5,6,20,2,3,3,5,1,0,10)";
        verify(st).executeUpdate(query);
        
    }
    
}
