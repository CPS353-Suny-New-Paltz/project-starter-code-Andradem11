package computeengineapi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;


public class TestComputeEngineAPI {
	@Test
	public void smokeTestComputeEngine() {
//		create engine implementation
		ComputeEngineAPI engine = new ComputeEngineImpl();
		
		List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
		
//		call copmuteSum
		int result = engine.computeSum(input);
		
		assertEquals(22, result);
		
	}
	
	@Test
	public void testInvalidInput() {
		ComputeEngineAPI engine = new ComputeEngineImpl();
		
		List<Integer> input = Arrays.asList(-1, -10, -25);
		int result = engine.computeSum(input);
		
		assertEquals(0, result, "No numbers less than 2 should be counted");
	}
	

}
