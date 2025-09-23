package storagecomputeapi;

import java.util.ArrayList;
import java.util.List;

public class StorageComputeImpl implements StorageComputeAPI {
	@Override
	public List<Integer> readInput() {
//		return an empty list
		return new ArrayList<>();
	}
	@Override
	public StorageResponse writeOutput(List<Integer> data) {
//		return default fail
		return new StorageResponse(StorageResponse.Status.FAIL, "Not implemented yet");	
	}
}
