package computeengineapi;
import java.util.List;

public class ComputeEngineImpl implements ComputeEngineAPI{
	@Override
	public int computeSum(List<Integer> num) {
//		sum of prime numbers
		int totalSum = 0;
		for(int n : num) {
//			call isPrime method
			for(int i = 2; i <= n; i++) {
				if(isPrime(i)) {
					totalSum += i;
				}
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
		for (int i = 2; i<=Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
