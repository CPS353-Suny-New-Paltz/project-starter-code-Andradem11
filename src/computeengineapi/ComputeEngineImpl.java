package computeengineapi;

public class ComputeEngineImpl implements ComputeEngineAPI {

    /**
     * validation:
     *  the computation has no result returning 0 for numbers < 2
     */
    @Override
    public int computeSum(int number) {
        if (number < 2) return 0;

        int totalSum = 0;
        // Sum all prime numbers ≤ n
        for (int i = 2; i <= number; i++) {
            totalSum += isPrime(i) ? i : 0;
        }
        return totalSum;
    }

    private boolean isPrime(int n) {
        // base
        if (n < 2) return false;

        // Check for divisors
        boolean prime = true;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            prime = (n % i == 0) ? false : prime;
        }
        return prime;
    }
}
