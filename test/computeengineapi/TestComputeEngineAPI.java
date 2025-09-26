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
		
		List<Integer> input = Arrays.asList(1, 2);
		
//		call copmuteSum
		int result = engine.computeSum(input);
		
		assertEquals(0, result);
		
	}

}
