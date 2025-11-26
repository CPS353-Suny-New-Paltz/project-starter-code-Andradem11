package integration;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineFastImpl;
import computeengineapi.ComputeEngineImpl;
import usercomputeapi.UserComputeMultiThreaded;
import usercomputeapi.UserComputeImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

/**
 * - Only large numbers are tested (50,000+) because small numbers run too fast
 *   and both times are 0 ms, which would produce NaN for speed-up calculations.
 * - The test asserts that the fast version is at least 10% faster.
 */


public class ComputeEngineBenchmarkTest {

    @Test
    public void testFastVersionSpeedup() {
//      Use only large numbers to avoid tiny runtimes
        List<Integer> testNumbers = List.of(50000, 100000, 200000);

        for (int n : testNumbers) {
            long originalTime = benchmarkEngine(new ComputeEngineImpl(), n);
            long fastTime = benchmarkEngine(new ComputeEngineFastImpl(), n);

            double speedup = ((double)(originalTime - fastTime) / originalTime) * 100;

            System.out.printf("%-10d %-15.2f %-10.2f %-12.2f%n",
                    n, (double)originalTime, (double)fastTime, speedup);

//          Test requirement: fast version must be at least 10% faster
            assertTrue(speedup >= 10.0,
                    "Speedup for input " + n + " is less than 10%: " + speedup + "%");
        }
    }

    /**
     * Run multiple iterations and average the results for stable measurements
     */
    private long benchmarkEngine(ComputeEngineAPI engine, int input) {
        TestInput inputWrapper = new TestInput(List.of(input));
        TestOutput output = new TestOutput();
        TestDataStore dataStore = new TestDataStore(inputWrapper, output);

        UserComputeMultiThreaded coordinator = new UserComputeMultiThreaded(
                new UserComputeImpl(dataStore, engine), 4);

        for (int i = 0; i < 3; i++) {
            coordinator.processFile("ignoredInput.txt", "ignoredOutput.txt");
        }

//      Benchmark multiple runs
        long totalTime = 0;
        int runs = 5;
        for (int i = 0; i < runs; i++) {
            long start = System.nanoTime();
            coordinator.processFile("ignoredInput.txt", "ignoredOutput.txt");
            long end = System.nanoTime();
            totalTime += (end - start);
        }

        return (totalTime / runs) / 1_000_000; // average in milliseconds
    }
}


