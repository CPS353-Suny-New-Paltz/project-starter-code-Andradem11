package performance;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineFastImpl;
import computeengineapi.ComputeEngineImpl;
import usercomputeapi.UserComputeMultiThreaded;
import usercomputeapi.UserComputeImpl;
import integration.TestDataStore;
import integration.TestInput;
import integration.TestOutput;

import java.util.List;
/**
 * Benchmark for ComputeEngineImpl vs ComputeEngineFastImpl.
 *
 * Results (example run):
 *   Input     Original(ms)  Fast(ms)  Speed-up(%)
 *   1000      3             0         100
 *   5000      0             0         NaN
 *   10000     0             0         NaN
 *   50000     10            1         90
 *   100000    5             0         100
 *   200000    11            0         100
 *
 * - Small inputs are too fast to measure reliably (NaN for speed-up).
 * - Large inputs show the fast engine is 40–100% faster.
 * - Uses TestDataStore to isolate CPU time and avoid file I/O.
 */

public class ComputeEngineBenchmark {

    public static void main(String[] args) {
//      Test inputs
        List<Integer> testNumbers = List.of(1000, 5000, 10000, 50000, 100000, 200000);

        System.out.printf("%-10s %-15s %-10s %-12s%n", "Input", "Original(ms)", "Fast(ms)", "Speed-up(%)");

        for (int n : testNumbers) {
            long originalTime = benchmarkEngine(new ComputeEngineImpl(), n);
            long fastTime = benchmarkEngine(new ComputeEngineFastImpl(), n);

            double speedup = ((double)(originalTime - fastTime) / originalTime) * 100;

            System.out.printf("%-10d %-15.2f %-10.2f %-12.2f%n",
                    n, (double)originalTime, (double)fastTime, speedup);
        }
    }

    private static long benchmarkEngine(ComputeEngineAPI engine, int input) {
        TestInput inputWrapper = new TestInput(List.of(input));
        TestOutput output = new TestOutput();
        TestDataStore dataStore = new TestDataStore(inputWrapper, output);

        UserComputeMultiThreaded coordinator =
                new UserComputeMultiThreaded(new UserComputeImpl(dataStore, engine), 4);

        long start = System.nanoTime();
        coordinator.processFile("ignoredInput.txt", "ignoredOutput.txt");
        long end = System.nanoTime();

        return (end - start) / 1_000_000; // milliseconds
    }
}

