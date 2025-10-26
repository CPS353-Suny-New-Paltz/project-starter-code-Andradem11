package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import computeengineapi.ComputeEngineAPI;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageResponse;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;

public class ComputeEngineExceptionIntegrationTest {
	/**
	 * Integration test that check exception handling logic from UserComputeImpl
	 * when compute engine throws, it's caught and returns FAIL
	 */
	
	@Test
	public void testExceptionHandling() {
		ComputeEngineAPI engine = new ComputeEngineAPI() {
			@Override
			public int computeSum(int number) {
				throw new RuntimeException("Compute failure");
			}
		};
		
//	    Storage setup that doesn't throw
		StorageComputeAPI storage = new StorageComputeAPI() {
			@Override
			public List<Integer> readInput(String inputPath) {
				return List.of(5);				
			}

			@Override
			public StorageResponse writeOutput(List<Integer> data, String outputPath) {
				return new StorageResponse(StorageResponse.Status.SUCCESS, "Successfully");
			}
			
		};
		
//	    Real implementations UserCompuImpl
		UserComputeAPI user = new UserComputeImpl(storage, engine);
		
//		validation request to check exception handling
		ComputeRequest request = new ComputeRequest(new DataSource() {
			@Override
			public int getLimit() {
				return 5;
			}
		}, ";");
		
		
		ComputeResponse response = user.computeSumOfPrimes(request);
		
		assertNotNull(response);
		assertEquals(ComputeResponse.Status.FAIL, response.getStatus());
		assertEquals(0, response.getSum());
		
	}
}
