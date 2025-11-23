package storagecomputeapi;
import project.annotations.ProcessAPI;
import java.util.List;

@ProcessAPI
public interface StorageComputeAPI {
	
//	reads integers from storage
	List<Integer> readInput(String inputPath);
		
//	writes output format to storage
	StorageResponse writeOutput(List<Integer> data, String outputPath);
}
