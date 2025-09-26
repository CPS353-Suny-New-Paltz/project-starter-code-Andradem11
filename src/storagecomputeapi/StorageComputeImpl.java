package storagecomputeapi;

import java.util.ArrayList;
import java.util.List;

public class StorageComputeImpl implements StorageComputeAPI {
	private final List<Integer> storeData = new ArrayList<>();
	
	@Override
	public List<Integer> readInput() {
		if (storeData.isEmpty()) {
			return null;
		}
		return new ArrayList<>(storeData);
	}
	@Override
	public StorageResponse writeOutput(List<Integer> data) {
		if(data == null || data.isEmpty()) {
			return new StorageResponse(StorageResponse.Status.FAIL, "No data to write");
		}
		storeData.clear();
		storeData.addAll(data);
		return new StorageResponse(StorageResponse.Status.SUCCESS, "Data written successfully");
		
	}
}
