package results;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import database.Database;

public class ReadDbTests {
	
	@Before
	public void setUp(){
		String PATH_TO_DB = "/Users/elf/oplatool/db/oplatool.db";
		Database.setPathToDB(PATH_TO_DB);
	}
	
	@Test
	public void shouldReturnAllExperiments() throws Exception{
		List<Experiment> result = Experiment.all();
		
		System.out.println("Experiments\n");
		for(Experiment exp : result){
			System.out.println("\t"+exp.getName() + " - " + exp.getCreatedAt());
			for(Execution exec : exp.getExecutions()){
				System.out.println("\t\tExecution:"+exec.getId() + " - "+ exec.getTime());
				System.out.println("\t\t\tObjectives");
				for(FunResults fun : exec.getFuns()){
					System.out.println("\t\t\t\t"+fun.getObjectives());
				}
				
			}
			System.out.println("\n");
		}
		
//		assertNotNull(result);
//		assertEquals(1, result.size());
//		List<Execution> executions = result.get(0).getExecutions();
//		
//		assertEquals(10, executions.size());
//		Execution execution = executions.get(6);
//		System.out.println(execution.getId());
//		
//		
//		assertNotNull(execution.getInfos());
//		assertEquals(3, execution.getInfos().size());
//		assertEquals(3, execution.getFuns().size());
//		
//		assertEquals(3, execution.getAllMetrics().getConventional().size());
	}
	
}
