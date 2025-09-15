package usercomputeapi;
import project.annotations.NetworkAPI;
@NetworkAPI
public interface UserComputeAPI {
	ComputeResponse computeSumOfPrimes(ComputeRequest request);
	
}
