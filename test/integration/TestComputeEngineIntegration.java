package integration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.TestStorageComputeAPIImpl;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;


public class TestComputeEngineIntegration {
	
	@Test
	public void testComputeEngine() {
		TestStorageComputeAPIImpl dataStore = new TestStorageComputeAPIImpl();
	    ComputeEngineAPI engine = new ComputeEngineImpl();
	    UserComputeAPI userApi = new UserComputeImpl();
	    

	    List<Integer> input = dataStore.readInput();
	    int sum = engine.computeSum(input);
	    dataStore.writeOutput(List.of(sum));

//	    Check output list size
	    assertEquals(input.size(), dataStore.getTestOutput().size());
	}
}
