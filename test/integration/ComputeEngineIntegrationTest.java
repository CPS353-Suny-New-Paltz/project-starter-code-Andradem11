package integration;

import computeengineapi.ComputeEngineAPI;
import storagecomputeapi.StorageComputeAPI;
import computeengineapi.ComputeEngineImpl;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

	@Test
	public void testIntegration() {
	    // Setup test input and output
	    TestInput input = new TestInput(Arrays.asList(1, 10, 25));
	    TestOutput output = new TestOutput();
	    StorageComputeAPI dataStore = new TestDataStore(input, output); 

	    // Real implementations
	    ComputeEngineAPI computeEngine = new ComputeEngineImpl();
	    // Simulate compute flow
	    for (Integer number : input.getInput()) {
	    	 int sum = computeEngine.computeSum(number);
	        dataStore.writeOutput(List.of(sum), null);
	    }

	    List<String> expected = Arrays.asList("0", "17", "100");
	    List<String> actual = output.getOutput();
	    
	    assertEquals(expected, actual,"Sum of primes match expected values");
	}
}