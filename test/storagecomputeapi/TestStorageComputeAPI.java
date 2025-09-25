package storagecomputeapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestStorageComputeAPI {
	@Test
	public void smokeTestStorageCompute() {	
//		create mock of API
		StorageComputeAPI mockStorage = Mockito.mock(StorageComputeAPI.class);
		
//		when readInput call, print 1, 2
		when(mockStorage.readInput()).thenReturn(Arrays.asList(1, 2));
		
//		when writeOutput call, print success
		when(mockStorage.writeOutput(any()))
		.thenReturn(new StorageResponse(StorageResponse.Status.SUCCESS, "MOCKED WRITE"));
		
//		run the Prototype
		StorageComputeAPIPrototype prototype = new StorageComputeAPIPrototype();
		prototype.prototype(mockStorage);
		
		assertEquals(1,1);	
	}


}

