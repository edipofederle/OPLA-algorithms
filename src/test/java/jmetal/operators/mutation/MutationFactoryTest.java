package jmetal.operators.mutation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import jmetal.experiments.FeatureMutationOperators;
import jmetal.experiments.NSGAIIConfig;
import jmetal.util.JMException;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.instanceOf;
import br.ufpr.inf.opla.patterns.operator.impl.DesignPatternMutationOperator;

public class MutationFactoryTest {
    
    @Test
    public void getMutationOperatorOnlyDesignPatternsOperator() throws JMException{
	NSGAIIConfig configs = Mockito.mock(NSGAIIConfig.class);
	List<String> operators =  new LinkedList<String>(Arrays.asList(FeatureMutationOperators.DESIGN_PATTERNS.getOperatorName()));
	Mockito.when(configs.getMutationOperators()).thenReturn(operators);
	HashMap<String, Object> parameters = null;
	
	Mutation mo = MutationFactory.getMutationOperator("DesignPatterns", parameters, configs);
	assertThat(mo, instanceOf(DesignPatternMutationOperator.class));
    }
    
    @Test
    public void getMutationOperatorOnlyPLAFeatureMutation() throws JMException{
	NSGAIIConfig configs = Mockito.mock(NSGAIIConfig.class);
	List<String> operators =  new LinkedList<String>(Arrays.asList(FeatureMutationOperators.ADD_CLASS_MUTATION.getOperatorName()));
	Mockito.when(configs.getMutationOperators()).thenReturn(operators);
	HashMap<String, Object> parameters = new HashMap<>();
	parameters.put("probability", 0.9);
	
	Mutation mo = MutationFactory.getMutationOperator("DesignPatterns", parameters, configs);
	assertThat(mo, instanceOf(PLAFeatureMutation.class));
    }
    
    @Test
    public void getMutationOperatorOnlyDesignPatternAndPLAMutation() throws JMException{
	NSGAIIConfig configs = Mockito.mock(NSGAIIConfig.class);
	List<String> operators =  new LinkedList<String>(
		Arrays.asList(FeatureMutationOperators.DESIGN_PATTERNS.getOperatorName(),
			FeatureMutationOperators.ADD_CLASS_MUTATION.getOperatorName()));
	Mockito.when(configs.getMutationOperators()).thenReturn(operators);
	HashMap<String, Object> parameters = new HashMap<>();
	parameters.put("probability", 0.9);
	
	Mutation mo = MutationFactory.getMutationOperator("DesignPatterns", parameters, configs);
	assertThat(mo, instanceOf(DesignPatternAndPLAMutation.class));
    }
    

}
