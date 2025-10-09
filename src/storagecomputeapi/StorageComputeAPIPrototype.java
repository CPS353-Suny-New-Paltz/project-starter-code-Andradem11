package storagecomputeapi;
import java.util.ArrayList;
import java.util.List;

import computeengineapi.ComputeEngineAPI;
import project.annotations.ProcessAPIPrototype;


public class StorageComputeAPIPrototype {
	
	@ProcessAPIPrototype
	public StorageResponse prototype(StorageComputeAPI storage) {
//		reference to API
		StorageComputeAPI api = storage;
		
//		reads input numbers from storage
		List<Integer> input = api.readInput(null);
		
//		compute sum
		int sum=10;
		
//		store result
		List<Integer> output = new ArrayList<>();
		output.add(sum); 
		
//		writes the output to storage
		return storage.writeOutput(output, null);	
	}
}
