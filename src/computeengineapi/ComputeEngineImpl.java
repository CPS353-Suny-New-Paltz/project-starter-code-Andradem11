package computeengineapi;

public class ComputeEngineImpl implements ComputeEngineAPI{
	@Override
	public int computeSum(int number) {
        int totalSum = 0;

        if (number < 2) {
            return 0;
        }
        for (int i = 2; i <= number; i++) {
            // Sum all prime numbers ≤ n
                if (isPrime(i)) {
                    totalSum += i;
                }
            }
        return totalSum;
    }
	private boolean isPrime(int n) {
//		base
		if(n < 2) {
			return false;
		}
//		if n has a remainder is prime
		for (int i = 2; i<= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}