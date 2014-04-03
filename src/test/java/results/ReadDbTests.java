package results;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import database.Database;

public class ReadDbTests {
	
	@Before
	public void setUp(){
		String PATH_TO_DB = "src/test/resources/opla_test.db";
		Database.setPathToDB(PATH_TO_DB);
	}
	
	@Test
	public void shouldReturnAllExperiments() throws Exception{
		List<Experiment> result = Experiment.all();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		List<Execution> executions = result.get(0).getExecutions();
		
		assertEquals(10, executions.size());
		Execution execution = executions.get(6);
		System.out.println(execution.getId());
		
		
		assertNotNull(execution.getInfos());
		assertEquals(2, execution.getInfos().size());
		assertEquals(2, execution.getFuns().size());
		
		assertEquals(2, execution.getAllMetrics().getConventional().size());
	}
	
}
