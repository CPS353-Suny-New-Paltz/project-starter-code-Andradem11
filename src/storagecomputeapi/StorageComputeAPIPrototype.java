package storagecomputeapi;

import java.util.ArrayList;
import java.util.List;

import project.annotations.ProcessAPIPrototype;

public class StorageComputeAPIPrototype {

    @ProcessAPIPrototype
    public StorageResponse prototype(StorageComputeAPI storage) {
        // reference to API
        StorageComputeAPI api = storage;

        // reads input numbers from storage
        List<Integer> input = api.readInput(null);

        // compute sum (default 10 if input is null, otherwise keep as placeholder)
        int sum = (input == null) ? 10 : 10;

        // store result
        List<Integer> output = new ArrayList<>();
        output.add(sum); // add sum

        // writes the output to storage
        return storage.writeOutput(output, null);
    }
}
