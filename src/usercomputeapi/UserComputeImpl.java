package usercomputeapi;

import java.util.List;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageResponse;

public class UserComputeImpl implements UserComputeAPI {
	private final StorageComputeAPI storage;
	private final ComputeEngineAPI engine;

	public UserComputeImpl(StorageComputeAPI storage, ComputeEngineAPI engine) {
		this.storage = storage;
		this.engine = engine;
	}

	@Override
	public ComputeResponse computeSumOfPrimes(ComputeRequest request) {

//		check if empty
		if(request == null || request.getSource() == null) {
			return new ComputeResponse(0, ComputeResponse.Status.FAIL);
		}

//		read from storage
		List<Integer> inputNum = storage.readInput();
		if(inputNum == null || inputNum.isEmpty()) {
			return new ComputeResponse(0, ComputeResponse.Status.FAIL);
		}

//		Pass the integers to the compute component
		int sum = engine.computeSum(inputNum);

//		write result to storage
		StorageResponse response = storage.writeOutput(List.of(sum));
		if(!response.isSuccess()) {
			return new ComputeResponse(0, ComputeResponse.Status.FAIL);
		}

//		return success response
		return new ComputeResponse(sum, ComputeResponse.Status.SUCCESS);
	}
}


