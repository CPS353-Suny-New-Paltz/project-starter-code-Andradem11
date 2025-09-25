package storagecomputeapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
	@Test
	public void smokeTestStorageComputeReal() {
	    StorageComputeAPI realStorage = new StorageComputeImpl();
	    List<Integer> input = realStorage.readInput(); 
	    StorageResponse response = realStorage.writeOutput(List.of(1,2,3));
	    assertTrue(response.getStatus() == StorageResponse.Status.FAIL);
	}


}

