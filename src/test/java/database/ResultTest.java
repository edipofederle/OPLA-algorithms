package database;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jmetal.core.Solution;

import org.junit.Before;
import org.junit.Test;

import results.Execution;
import results.Experiment;
import results.FunResults;

public class ResultTest {
	
	Result fakeResult;
	Execution fakeExecution;
	Experiment fakeExperiement;
	Solution fakeSolution;
	
	@Before
	public void setUp(){
		fakeResult = new Result();
		fakeExecution = mock(Execution.class);
		fakeExperiement  = mock(Experiment.class);
		fakeSolution = mock(Solution.class);
	}
	
	@Test
	public void shouldAppendObjectives(){
		when(fakeSolution.toString()).thenReturn("744.0 6.142857142857143 544.0 4.142857142857133");
		
		List<Solution> list = new ArrayList<Solution>(Arrays.asList(fakeSolution));
		List<FunResults> funresult = fakeResult.getObjectives(list, fakeExecution, fakeExperiement);
		
		assertEquals("744.0|6.142857142857143|544.0|4.142857142857133", funresult.get(0).getObjectives());
	}
	
	
	@Test
	public void shouldAppendObjectivesWithEndSpace(){
		when(fakeSolution.toString()).thenReturn("744.0 6.142857142857143 ");
		
		List<Solution> list = new ArrayList<Solution>(Arrays.asList(fakeSolution));
		List<FunResults> funresult = fakeResult.getObjectives(list, fakeExecution, fakeExperiement);
		
		assertEquals("744.0|6.142857142857143", funresult.get(0).getObjectives());
	}

}
