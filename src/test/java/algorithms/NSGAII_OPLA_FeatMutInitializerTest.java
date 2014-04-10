package algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.Test;

public class NSGAII_OPLA_FeatMutInitializerTest {
	
	
	@Test
	public void setPlas(){
		FakeAlgorithm fake = new FakeAlgorithm();
		String plas = "/path/pla1, /path/pla2";
		fake.setPlas(plas);
		
		fake.getPlas().equals(plas);
	}
	
	@Test
	public void disableCrossover(){
		FakeAlgorithm fake = new FakeAlgorithm();
		
		fake.setCrossoverProbability(1);
		fake.disableCrossover();
		
		assertTrue(fake.getCrossoverProbability() == 0);
	}
	
	@Test
	public void disableMutation(){
		FakeAlgorithm fake = new FakeAlgorithm();
		
		fake.setMutationProbability(1);
		fake.disableMutation();
		
		assertTrue(fake.getMutationProbability() == 0);
	}
	
	
	@Test
	public void populationSizeCannotBeNegative(){
		FakeAlgorithm fake = new FakeAlgorithm();
		
		try{
			fake.setPopulationSize(-1);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("populationSize must be greater or equal 1", e.getMessage());
		}
	}
	
	@Test
	public void populationSizeCannotBeZero(){
		FakeAlgorithm fake = new FakeAlgorithm();
		
		try{
			fake.setPopulationSize(0);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("populationSize must be greater or equal 1", e.getMessage());
		}
	}
	
	@Test
	public void maxEvaluationCannotBeNegative(){
		FakeAlgorithm fake = new FakeAlgorithm();
		
		try{
			fake.setMaxEvaluations(-1);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("maxEvaluation must be greater or equal 1", e.getMessage());
		}
	}
	
	@Test
	public void maxEvaluationCannotBeZero(){
		FakeAlgorithm fake = new FakeAlgorithm();
		
		try{
			fake.setMaxEvaluations(0);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("maxEvaluation must be greater or equal 1", e.getMessage());
		}
	}
	
	@Test
	public void numberOfRunsMustBeGreaterOrEqual1(){
		FakeAlgorithm fake = new FakeAlgorithm();
		
		try{
			fake.setNumberOfRuns(-1);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("numberOfRuns must be greater or equal 1", e.getMessage());
		}
	}
	
	@Test
	public void crossoverProbabilityCannotBeNegative(){
		FakeAlgorithm fake = new FakeAlgorithm();

		try{
			fake.setCrossoverProbability(-10.0);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("crossoverProbability must be a value between 0 and 1", e.getMessage());
		}
	} 
	
	@Test
	public void crossoverProbabilityCannotBeUpperThat1(){
		FakeAlgorithm fake = new FakeAlgorithm();

		try{
			fake.setCrossoverProbability(2);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("crossoverProbability must be a value between 0 and 1", e.getMessage());
		}
	}
	
	@Test
	public void mutationProbabilityCannotBeNegative(){
		FakeAlgorithm fake = new FakeAlgorithm();

		try{
			fake.setMutationProbability(-10.0);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("mutationProbability must be a value between 0 and 1", e.getMessage());
		}
	}
	
	@Test
	public void mutationProbabilityCannotBeUpperThat1(){
		FakeAlgorithm fake = new FakeAlgorithm();

		try{
			fake.setMutationProbability(2);
			Assert.fail();
		}catch(IllegalArgumentException e){
			assertEquals("mutationProbability must be a value between 0 and 1", e.getMessage());
		}
	}
	
}
