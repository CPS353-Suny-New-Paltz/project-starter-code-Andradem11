package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
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
//		Engine that fails on input 42
        ComputeEngineAPI failingEngine = new ComputeEngineAPI() {
            @Override
            public int computeSum(int number) {
                if (number == 42) {
                    throw new RuntimeException("Simulated engine failure");
                }
                return new ComputeEngineImpl().computeSum(number);
            }
        };

//      Storage setup that doesn't throw
        StorageComputeAPI storage = new StorageComputeAPI() {
            @Override
            public List<Integer> readInput(String inputPath) {
                return List.of(42, 10);
            }

            @Override
            public StorageResponse writeOutput(List<Integer> data, String outputPath) {
                return new StorageResponse(StorageResponse.Status.SUCCESS, "Successfully");
            }
        };

//      Real implementation
        UserComputeAPI user = new UserComputeImpl(storage, failingEngine);

//      Test failing computation
        ComputeRequest requestFail = new ComputeRequest(new DataSource() {
            @Override
            public int getLimit() {
                return 42;
            }
        }, ";");

        ComputeResponse responseFail = user.computeSumOfPrimes(requestFail);
        assertNotNull(responseFail);
        assertEquals(ComputeResponse.Status.FAIL, responseFail.getStatus(),
                "Engine exception should propagate as FAIL");
        assertEquals(0, responseFail.getSum());

//      Test success computation
        ComputeRequest requestSuccess = new ComputeRequest(new DataSource() {
            @Override
            public int getLimit() {
                return 10;
            }
        }, ";");

        ComputeResponse responseSuccess = user.computeSumOfPrimes(requestSuccess);
        assertNotNull(responseSuccess);
        assertEquals(ComputeResponse.Status.SUCCESS, responseSuccess.getStatus(),
                "Valid input should return success");
        assertEquals(17, responseSuccess.getSum());
    }
}
