package testharness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;
import usercomputeapi.UserComputeAPI;


public class TestUser {
	
	// TODO 3: change the type of this variable to the name you're using for your
	// @NetworkAPI interface; also update the parameter passed to the constructor
	private final UserComputeAPI coordinator;

	public TestUser(UserComputeAPI coordinator) {
		if(coordinator == null) {
			throw new IllegalArgumentException("UserComputeAPI cannot be null");
		}
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";
		
		// TODO 4: Call the appropriate method(s) on the coordinator to get it to 
		// run the compute job specified by inputPath, outputPath, and delimiter

		List<Integer> numbers = new StorageComputeImpl().readInput(inputPath);
		
		List<Integer> results = new ArrayList<>();
        for(Integer n: numbers) {
        	if (n == null) {
        		continue;
        	}
        	
        	DataSource source = new DataSource() {
                @Override
                public int getLimit() {
                    return n;
                }
            };
            
            ComputeRequest request = new ComputeRequest(source, String.valueOf(delimiter));
            ComputeResponse response = coordinator.computeSumOfPrimes(request);
            results.add(response.getSum());
            
        }
        
        new StorageComputeImpl().writeOutput(results, outputPath);
	}

}
