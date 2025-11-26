package computeengineapi;

/**
 * Faster version of ComputeEngineAPI.
 *
 * Step 1 check:
 * When I ran the profiler, computeSum in the old engine basically
 * flies for small numbers, but once you hit 50,000+ it really slows down.
 *
 * Timings:
 *   10,000   -> basically 0 ms
 *   50,000   -> ~5–6 ms
 *   100,000  -> ~12–15 ms
 *
 * Why it was slow:
 * The old code checks every number with a sqrt(n) prime check,
 * so overall runtime is O(n * sqrt(n)) — it just explodes with big numbers.
 *
 * What I did:
 * Switched it to a Sieve of Eratosthenes. Now it runs way faster
 * (like 10–20x) because it’s only O(n log log n).
 */

public class ComputeEngineFastImpl implements ComputeEngineAPI {

    @Override
    public int computeSum(int limit) {
        if (limit < 2) {
            return 0;
        }

        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        for (int p = 2; p * p <= limit; p++) {
            if (isPrime[p]) {
                for (int multiple = p * p; multiple <= limit; multiple += p) {
                    isPrime[multiple] = false;
                }
            }
        }

        int sum = 0;
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                sum += i;
            }
        }

        return sum;
    }
}
