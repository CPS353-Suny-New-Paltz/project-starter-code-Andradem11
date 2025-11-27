package performance;

import computeengineapi.ComputeEngineImpl;

/**
 * Profiling shows that for small n=1000 to 10000 
 * runtime is negligible, but after n=50,000 
 * the runtime jumps to 5–6 ms and grows rapidly 
 * for larger inputs.
*/

public class ComputeEngineProfiler {
    public static void main(String[] args) {
        ComputeEngineImpl engine = new ComputeEngineImpl();
        int[] testInputs = {1000, 5000, 10000, 50000, 100000};

        for (int n : testInputs) {
            long start = System.nanoTime();
            int sum = engine.computeSum(n);
            long end = System.nanoTime();
            System.out.println("computeSum(" + n + ") = " + sum + 
                               " took " + (end - start)/1_000_000 + " ms");
        }
    }
}
