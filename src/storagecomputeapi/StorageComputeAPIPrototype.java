package storagecomputeapi;
import java.util.List;
import project.annotations.ProcessAPIPrototype;


public class StorageComputeAPIPrototype {
	
	@ProcessAPIPrototype
	public void prototype(StorageComputeAPI storage) {
//		reads input numbers 
		List<Integer> input = storage.readInput();
		
//		writes the output
		storage.writeOutput(input);	
	}
}
