package storagecomputeapi;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SmokeTestStorage {
	
	@Test
	public void storageSmokeTestImpl() {
		TestStorageComputeAPIImpl api = new TestStorageComputeAPIImpl();
		List<Integer> input = api.readInput();
        api.writeOutput(input);
	}

}
