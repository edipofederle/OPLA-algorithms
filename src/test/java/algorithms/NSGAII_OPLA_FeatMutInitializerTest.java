package algorithms;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import jmetal.experiments.FeatureMutationOperators;
import junit.framework.Assert;

import org.junit.Test;

import br.ufpr.inf.opla.patterns.strategies.scopeselection.impl.ElementsWithSameDesignPatternSelection;

public class NSGAII_OPLA_FeatMutInitializerTest {

	@Test
	public void setPlas() {
		FakeAlgorithm fake = new FakeAlgorithm();
		String plas = "/path/pla1, /path/pla2";
		fake.setPlas(plas);

		fake.getPlas().equals(plas);
	}

	@Test
	public void disableCrossover() {
		FakeAlgorithm fake = new FakeAlgorithm();

		fake.setCrossoverProbability(1);
		fake.disableCrossover();

		assertTrue(fake.getCrossoverProbability() == 0);
	}

	@Test
	public void disableMutation() {
		FakeAlgorithm fake = new FakeAlgorithm();

		fake.setMutationProbability(1);
		fake.disableMutation();

		assertTrue(fake.getMutationProbability() == 0);
	}

	@Test
	public void populationSizeCannotBeNegative() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setPopulationSize(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals("populationSize must be greater or equal 1",
					e.getMessage());
		}
	}

	@Test
	public void populationSizeCannotBeZero() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setPopulationSize(0);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals("populationSize must be greater or equal 1",
					e.getMessage());
		}
	}

	@Test
	public void maxEvaluationCannotBeNegative() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setMaxEvaluations(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals("maxEvaluation must be greater or equal 1",
					e.getMessage());
		}
	}

	@Test
	public void maxEvaluationCannotBeZero() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setMaxEvaluations(0);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals("maxEvaluation must be greater or equal 1",
					e.getMessage());
		}
	}

	@Test
	public void numberOfRunsMustBeGreaterOrEqual1() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setNumberOfRuns(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals("numberOfRuns must be greater or equal 1",
					e.getMessage());
		}
	}

	@Test
	public void crossoverProbabilityCannotBeNegative() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setCrossoverProbability(-10.0);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals(
					"crossoverProbability must be a value between 0 and 1",
					e.getMessage());
		}
	}

	@Test
	public void crossoverProbabilityCannotBeUpperThat1() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setCrossoverProbability(2);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals(
					"crossoverProbability must be a value between 0 and 1",
					e.getMessage());
		}
	}

	@Test
	public void shouldSetCorrectListOfPatterns() {
		FakeAlgorithm fake = new FakeAlgorithm();
		fake.setPatterns("strategy", "bridge", "MEDIATOR");

		assertEquals(3, fake.getPatterns().length);
		assertEquals(Arrays.asList("Strategy", "Bridge", "Mediator"),
				Arrays.asList(fake.getPatterns()));
	}

	@Test
	public void shouldReturnAllPatternsIfNoneIsSeted() {
		FakeAlgorithm fake = new FakeAlgorithm();

		assertEquals(3, fake.getPatterns().length);
		assertEquals(Arrays.asList("Strategy", "Bridge", "Mediator"),
				Arrays.asList(fake.getPatterns()));
	}

	@Test
	public void shouldRiseExceptionWhenPatternsNotValid() {
		FakeAlgorithm fake = new FakeAlgorithm();
		try {
			fake.setPatterns("BLA");
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals(
					"Invalid(s) Design Pattern(s). Valids are: Stragety, Bridge and Mediator",
					e.getMessage());
		}
	}

	/**
	 * not Strategy the Design Patterns here
	 */
	@Test
	public void shouldSetPatternsStrategyToElementsWithSameDesignPatternSelection() {
		FakeAlgorithm fake = new FakeAlgorithm();
		fake.setDesignPatternStrategy(new ElementsWithSameDesignPatternSelection());

		assertNotNull(fake.getDesignPatternStrategy());
		assertThat(fake.getDesignPatternStrategy(),
				instanceOf(ElementsWithSameDesignPatternSelection.class));
	}

	/**
	 * not Strategy the Design Patterns here
	 */
	@Test
	public void shouldSetPatternsStrategyToRandom() {
		FakeAlgorithm fake = new FakeAlgorithm();
		fake.setDesignPatternStrategy(null);

		assertNull(fake.getDesignPatternStrategy());
	}

	@Test
	public void shouldExcludeDesignPatternsFromMutationOpetatorList() {

		FakeAlgorithm fake = new FakeAlgorithm();
		List<String> operators = new LinkedList<String>(
				Arrays.asList(FeatureMutationOperators.ADD_CLASS_MUTATION
						.getOperatorName(),
						FeatureMutationOperators.MOVE_ATTRIBUTE_MUTATION
								.getOperatorName(),
						FeatureMutationOperators.FEATURE_MUTATION
								.getOperatorName(),
						FeatureMutationOperators.ADD_MANAGER_CLASS_MUTATION
								.getOperatorName(),
						FeatureMutationOperators.MOVE_METHOD_MUTATION
								.getOperatorName(),
						FeatureMutationOperators.MOVE_OPERATION_MUTATION
								.getOperatorName(),
						FeatureMutationOperators.DESIGN_PATTERNS
								.getOperatorName()));

		fake.setMutationOperators(operators);

		fake.excludeDesignPatternsFromMutationOperatorList();
	}

	@Test
	public void mutationProbabilityCannotBeNegative() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setMutationProbability(-10.0);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals("mutationProbability must be a value between 0 and 1",
					e.getMessage());
		}
	}

	@Test
	public void mutationProbabilityCannotBeUpperThat1() {
		FakeAlgorithm fake = new FakeAlgorithm();

		try {
			fake.setMutationProbability(2);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertEquals("mutationProbability must be a value between 0 and 1",
					e.getMessage());
		}
	}

}
