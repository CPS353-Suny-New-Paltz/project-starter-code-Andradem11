package usercomputeapi;

import java.util.ArrayList;

import java.util.List;

import computeengineapi.ComputeEngineAPI;
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
		if (request == null || request.getSource() == null) {
			return new ComputeResponse(0, ComputeResponse.Status.FAIL);
		}
		
		int number = request.getSource().getLimit();
		int sum = engine.computeSum(number);
		
		return new ComputeResponse(sum, ComputeResponse.Status.SUCCESS);
	}
	
	public void processFile(String inputPath, String outputPath) {
		List<Integer> input = storage.readInput(inputPath);
		List<Integer> result = new ArrayList<>(); 
		
		for(Integer number : input) {
			int sum = engine.computeSum(number);
			result.add(sum);
		}
		
		StorageResponse response = storage.writeOutput(result, outputPath);
		if (!response.isSuccess()) {
			System.err.println("Error writing output: " + response.getMessage());
		}
	}
}