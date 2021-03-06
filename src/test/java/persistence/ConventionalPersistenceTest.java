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

import metrics.Conventional;

import org.junit.Test;

import results.Execution;
import results.Experiment;

/**
 * 
 * @author elf
 */
public class ConventionalPersistenceTest {

    @Test
    public void saveConventionalMetrics() throws Exception {
	Connection connection = mock(Connection.class);
	Statement st = mock(Statement.class);
	Execution exec = mock(Execution.class);
	Experiment exp = mock(Experiment.class);

	when(exec.getId()).thenReturn("1");
	when(connection.createStatement()).thenReturn(st);
	ConventionalPersistence persistence = new ConventionalPersistence(connection);

	Conventional conventionalMetrics = new Conventional("1", exec, exp);
	conventionalMetrics.setSumCohesion(10d);
	conventionalMetrics.setMeanDepComps(10d);
	conventionalMetrics.setCohesion(10d);
	conventionalMetrics.setMeanNumOps(10d);
	conventionalMetrics.setSumClassesDepIn(1);
	conventionalMetrics.setSumClassesDepOut(11);
	conventionalMetrics.setSumDepIn(1d);
	conventionalMetrics.setSumDepOut(3d);

	String expectedQuery = "insert into ConventionalMetrics (sum_cohesion, cohesion, macAggregation, meanDepComps, meanNumOps, sumClassesDepIn, sumClassesDepOut, sumDepIn, sumDepOut, execution_id, is_all, experiement_id, id_solution) values (10.0,10.0,36.1,10.0,10.0,1,11,1.0,3.0,1,0,null,1)";

	persistence.save(conventionalMetrics);

	verify(st).executeUpdate(expectedQuery);

    }

}