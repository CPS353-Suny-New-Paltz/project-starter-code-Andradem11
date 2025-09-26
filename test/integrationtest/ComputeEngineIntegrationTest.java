package integrationtest;

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
	    StorageComputeAPI dataStore = new TestDataStore(input, output); // now implements StorageComputeAPI

	    // Real implementations
	    ComputeEngineAPI computeEngine = new ComputeEngineImpl();
	    UserComputeAPI userCompute = new UserComputeImpl();

	    // Simulate compute flow
	    for (Integer i : dataStore.readInput()) {
	        int result = computeEngine.computeSum(List.of(i));
	        dataStore.writeOutput(List.of(result)); // now uses StorageComputeAPI method
	    }

	    List<String> expected = Arrays.asList("0", "0", "0");
	    List<String> actual = output.getOutput();
	    assertEquals(expected, actual);
	}
}