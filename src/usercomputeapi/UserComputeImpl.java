package usercomputeapi;

import java.util.ArrayList;

import java.util.List;

import computeengineapi.ComputeEngineAPI;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageResponse;

public class UserComputeImpl implements UserComputeAPI {
	private final StorageComputeAPI storage;
	private final ComputeEngineAPI engine;

	/**
	 * validation:
	 * - if dependencies are null 
	 *  IllegalArgumentException is thrown
	 */
	public UserComputeImpl(StorageComputeAPI storage, ComputeEngineAPI engine) {
		if ( storage == null) {
			throw new IllegalArgumentException("StorageComputeAPI cannot be null");
		}
		if ( engine == null) {
			throw new IllegalArgumentException("ComputeEngineAPI cannot be null");
		}
		this.storage = storage;
		this.engine = engine;	
	}
	
	/**
	 * validation:
	 * -Request and data cannot be null.
	 * returns FAIL response
	 */

	@Override
	public ComputeResponse computeSumOfPrimes(ComputeRequest request) {
		if (request == null || request.getSource() == null) {
			return new ComputeResponse(0, ComputeResponse.Status.FAIL);
		}
		try {
			int number = request.getSource().getLimit();
			if (number < 0) {
				return new ComputeResponse(0, ComputeResponse.Status.FAIL);
			}
			
			int sum = engine.computeSum(number);
			return new ComputeResponse(sum, ComputeResponse.Status.SUCCESS);	
		} catch (Exception e) {
			System.err.println("Error during computation: " + e.getMessage());
			return new ComputeResponse(0, ComputeResponse.Status.FAIL);
		}
		
	}
	
	/**
	 * validation:
	 * inputPath and outputPath cannot be empty.
	 * Return error, avoids passing a bad outPath to storage implementation.
	 */
	public void processFile(String inputPath, String outputPath) {
		if (inputPath == null || inputPath.isBlank()) {
			System.err.println("processFile: Input file path cannot be empty.");
			return;
		}
		if (outputPath == null || outputPath.isBlank() ) {
			System.err.println("processFile: Output file cannot be empty.");
			return;
		}
		
		List<Integer> input;
        try {
            input = storage.readInput(inputPath);
            if (input == null) {
                input = new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("processFile: error reading input: " + e.getMessage());
            return;
        }
        
        List<Integer> result = new ArrayList<>();
        for (Integer number : input) {
        	if (number == null || number < 0) {
        		System.err.println("processFile: Skipping invalid input value: " + number);
                result.add(0);
                continue;             
        	}
        	try {
        		result.add(engine.computeSum(number));          
        	} catch (Exception e) {
        		System.err.println("processFile: Computation error for input " + number + ": " + e.getMessage());
                result.add(0);
            }           
        }
        
        try {
        	
            StorageResponse response = storage.writeOutput(result, outputPath);
            if (response == null) {
                System.err.println("processFile: Storage returned null response when writing output.");
            } else if (!response.isSuccess()) {
                System.err.println("processFile: Storage reported failure: " + response.getMessage());
            }
        } catch (Exception e) {
//          Catch unexpected exceptions from storage write
            System.err.println("processFile: Error writing output: " + e.getMessage());
        }
    }
}
