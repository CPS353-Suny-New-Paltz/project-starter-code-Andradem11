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
		ComputeEngineAPI engineFailure = new ComputeEngineAPI() {
            @Override
            public int computeSum(int number) {
                if (number == 42) {
                    throw new RuntimeException("Simulated compute engine failure");
                }
                // Otherwise normal computation (reuse ComputeEngineImpl logic)
                return new computeengineapi.ComputeEngineImpl().computeSum(number);
            }
        };
		
//	    Storage setup that doesn't throw
		StorageComputeAPI storage = new StorageComputeAPI() {
			@Override
			public List<Integer> readInput(String inputPath) {
				return List.of(5, 42);				
			}

			@Override
			public StorageResponse writeOutput(List<Integer> data, String outputPath) {
				return new StorageResponse(StorageResponse.Status.SUCCESS, "Successfully written");
			}
			
		};
		
//	    First, normal input should succeed
		UserComputeAPI userCompute = new UserComputeImpl(storage, engineFailure);
		
		ComputeRequest requestSuccess = new ComputeRequest(new DataSource() {
			@Override
	        public int getLimit() {
				return 5;
	        }
	    }, ";");

		ComputeResponse responseSuccess = userCompute.computeSumOfPrimes(requestSuccess);
		
		assertNotNull(responseSuccess);
		assertEquals(ComputeResponse.Status.SUCCESS, responseSuccess.getStatus());
		assertEquals(10, responseSuccess.getSum());
		
//		Second, input that triggers exception should return FAIL
		ComputeRequest requestFail = new ComputeRequest(new DataSource() {
            @Override
            public int getLimit() {
                return 42;
            }
        }, ";");

        ComputeResponse responseFail = userCompute.computeSumOfPrimes(requestFail);
        assertNotNull(responseFail);
        assertEquals(ComputeResponse.Status.FAIL, responseFail.getStatus());
        assertEquals(0, responseFail.getSum());
		
	}
}
