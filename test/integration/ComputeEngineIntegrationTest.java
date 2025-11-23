package integration;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.StorageComputeAPI;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

	@Test
	public void testIntegration() {
//	    Setup test input and output
	    TestInput input = new TestInput(Arrays.asList(1, 10, 25));
	    TestOutput output = new TestOutput();
	    StorageComputeAPI dataStore = new TestDataStore(input, output); 

//	    Real implementations
	    ComputeEngineAPI computeEngine = new ComputeEngineImpl();
	    UserComputeAPI userCompute = new UserComputeImpl(dataStore, computeEngine);
	    
	    for (final Integer number : input.getInput()) {
	        DataSource source = new DataSource() {
	            @Override
	            public int getLimit() {
	                return number;
	            }
	        };
	        ComputeRequest request = new ComputeRequest(source, ";");
	        ComputeResponse response = userCompute.computeSumOfPrimes(request);
	        dataStore.writeOutput(Arrays.asList(response.getSum()), null);
	    }

	    List<String> expected = Arrays.asList("0", "17", "100");
	    List<String> actual = output.getOutput();
	    
	    assertEquals(expected, actual,"Sum of primes match expected values");
	}
}