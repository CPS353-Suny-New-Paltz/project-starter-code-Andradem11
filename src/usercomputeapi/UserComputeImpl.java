package usercomputeapi;

public class UserComputeImpl implements UserComputeAPI {
	@Override
	public ComputeResponse computeSumOfPrimes(ComputeRequest request) {
//		default fail response
		return new ComputeResponse(0, ComputeResponse.Status.FAIL);
	}
}
