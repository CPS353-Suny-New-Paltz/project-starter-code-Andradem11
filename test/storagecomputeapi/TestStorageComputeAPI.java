package storagecomputeapi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestStorageComputeAPI {
	@Test
	public void smokeTestStorageCompute() {	
        StorageComputeAPI storage = new StorageComputeImpl();
        
        List<Integer> input = storage.readInput(); 
        StorageResponse response = storage.writeOutput(List.of(1, 2, 3));

        assertTrue(response.getStatus() == StorageResponse.Status.FAIL);
    }

}


