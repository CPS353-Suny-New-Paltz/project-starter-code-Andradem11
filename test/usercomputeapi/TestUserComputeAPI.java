package usercomputeapi;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageComputeImpl;

public class TestUserComputeAPI {
	@Test
	public void smokeTestUserCompute() {
//		create mock of API
		UserComputeAPI mockUser = Mockito.mock(UserComputeAPI.class);
//		when computeSumOfPrimes called, print fail
		when(mockUser.computeSumOfPrimes(any())).thenReturn(new ComputeResponse(0, ComputeResponse.Status.FAIL));
		
//		run prototype
		UserComputeAPIPrototype prototype = new UserComputeAPIPrototype();
		List<Integer> result = prototype.prototype(mockUser);
		
		assertTrue(result.isEmpty());
	}
	@Test
    public void smokeTestUserComputeReal() {
		StorageComputeAPI storage = new StorageComputeImpl();
        UserComputeAPI realUser = new UserComputeImpl();
        ComputeRequest request = new ComputeRequest(null, ";");
        ComputeResponse response = realUser.computeSumOfPrimes(request);
        assertTrue(response.getStatus() == ComputeResponse.Status.FAIL);
    }

}
