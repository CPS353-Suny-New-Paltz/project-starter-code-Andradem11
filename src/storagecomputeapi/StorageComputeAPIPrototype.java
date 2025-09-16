package storagecomputeapi;
import java.util.ArrayList;
import java.util.List;

import computeengineapi.ComputeEngineAPI;
import project.annotations.ProcessAPIPrototype;


public class StorageComputeAPIPrototype {
	
	@ProcessAPIPrototype
	public void prototype(StorageComputeAPI storage) {
//		reads input numbers from storage
		List<Integer> input = storage.readInput();
		
//		compute engine
		ComputeEngineAPI compute = null;
		
//		compute sum
		int sum=10;
		
//		store result
		List<Integer> output = new ArrayList<>();
		output.add(sum); 
		
//		writes the output to storage
		storage.writeOutput(output);	
	}
}
