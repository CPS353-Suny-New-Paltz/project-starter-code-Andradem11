package storagecomputeapi;

import java.util.ArrayList;
import java.util.List;

public class TestStorageComputeAPIImpl implements StorageComputeAPI{
//	attributes
	 private final List<Integer> testInput = List.of(1, 10, 25); 
	 private final List<String> testOutput = new ArrayList<>();
	 
	 @Override
	 public List<Integer> readInput() {
		 return testInput;
	 }
	 
	 @Override
	 public StorageResponse writeOutput(List<Integer> data) {
		 for (Integer i : data) {
			 testOutput.add(String.valueOf(i));
		 }
		 return new StorageResponse(StorageResponse.Status.SUCCESS, "TEST WRITE");
	 }
	 
	 public List<String> getTestOutput() {
		 return testOutput;
	 }
	 
}