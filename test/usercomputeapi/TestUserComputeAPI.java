package usercomputeapi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageComputeImpl;
import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;

public class TestUserComputeAPI {
	@Test
	public void smokeTestUserComputeSuccess() {
        StorageComputeAPI storage = new StorageComputeImpl();
        ComputeEngineAPI engine = new ComputeEngineImpl();
        UserComputeAPI user = new UserComputeImpl(storage, engine);
        
        storage.writeOutput(List.of(5));
        ComputeRequest request = new ComputeRequest(new DataSource() {
        	public int getLimit() {
        		return 5;
        	}
        },";");
        ComputeResponse response = user.computeSumOfPrimes(request);
        assertTrue(response.isSuccess());
        assertEquals(10, response.getSum());
    }
	
	@Test
	public void smokeTestUserComputeFail() {
		StorageComputeAPI storage = new StorageComputeImpl();
        ComputeEngineAPI engine = new ComputeEngineImpl();
        UserComputeAPI user = new UserComputeImpl(storage, engine);
        
        ComputeRequest request = new ComputeRequest(null, ";");
        ComputeResponse response = user.computeSumOfPrimes(request);

        assertTrue(response.getStatus() == ComputeResponse.Status.FAIL);
	}

}

