package integration;

import java.util.List;

import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageResponse;

public class TestDataStore implements StorageComputeAPI {
    private final TestInput input;
    private final TestOutput output;

    public TestDataStore(TestInput input, TestOutput output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public List<Integer> readInput() {
        return input.getInput();
    }
    
    @Override
    public StorageResponse writeOutput(List<Integer> data) {
        if (data == null || data.isEmpty()) {
            return new StorageResponse(StorageResponse.Status.FAIL, "No data to write");
        }
        // convert integers to strings
        for (Integer i : data) {
            output.write(String.valueOf(i));
        }
        return new StorageResponse(StorageResponse.Status.SUCCESS, "Data written successfully");
    }
}