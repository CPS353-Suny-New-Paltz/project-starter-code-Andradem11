package performance;

import usercomputeapi.UserComputeMultiThreaded;
import usercomputeapi.UserComputeImpl;
import computeengineapi.ComputeEngineImpl;
import integration.TestDataStore;
import integration.TestInput;
import integration.TestOutput;

import java.util.List;

/**
 * The UserComputeMultiThreaded coordinator spends most of its CPU time
 * running computeSum for the bigger numbers. Small numbers are basically
 * instant, but 50,000 and 100,000 take most of the time.
 * Using TestDataStore so file I/O doesn't affect the timing.
 */


public class CoordinatorProfiler {
    public static void main(String[] args) {
//      Create test input numbers for the coordinator to process
        TestInput input = new TestInput(List.of(1000, 5000, 10000, 50000, 100000));
        TestOutput output = new TestOutput();
        
//      Use TestDataStore instead of real files
        TestDataStore dataStore = new TestDataStore(input, output);

//      Coordinator with 4 threads
        UserComputeMultiThreaded coordinator = new UserComputeMultiThreaded(
            new UserComputeImpl(dataStore, new ComputeEngineImpl()), 4
        );

        long start = System.nanoTime();
//      Process the "file" (actually the in-memory input)
        coordinator.processFile("ignoredInputPath.txt", "ignoredOutputPath.txt");
        long end = System.nanoTime();

        System.out.println("Multi-user processing took " + (end - start)/1_000_000 + " ms");

//      print output sums
        System.out.println("Output sums: " + output.getOutput());
    }
}
