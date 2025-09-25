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
        UserComputeAPI user = new UserComputeImpl(); 

	    for (Integer num : dataStore.readInput()) {
	        int result = engine.computeSum(List.of(num));
	        dataStore.writeOutput(List.of(result));
	    }

	    assertEquals(List.of("0", "0", "0"), dataStore.getTestOutput());
	}
}

