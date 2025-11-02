package computeengineapi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComputeEngineAPI {
	@Test
	public void smokeTestComputeEngine() {
//		create engine implementation
		ComputeEngineAPI engine = new ComputeEngineImpl();
		
//		call copmuteSum
		int result = engine.computeSum(10);
		
		assertEquals(17, result);
		
	}
	
	@Test
	public void testInvalidInput() {
		ComputeEngineAPI engine = new ComputeEngineImpl();
		
		int result = engine.computeSum(-1);
		
		assertEquals(0, result, "No numbers less than 2 should be counted");
	}
}