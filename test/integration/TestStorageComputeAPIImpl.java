package integration;

import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageResponse;
import java.util.ArrayList;
import java.util.List;

public class TestStorageComputeAPIImpl implements StorageComputeAPI {
    private final List<Integer> testInput;
    private final List<String> testOutput = new ArrayList<>();

    public TestStorageComputeAPIImpl(List<Integer> input) {
        this.testInput = new ArrayList<>(input);
    }

    @Override
    public List<Integer> readInput() {
        return new ArrayList<>(testInput);
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