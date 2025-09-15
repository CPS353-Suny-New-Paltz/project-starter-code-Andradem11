package computeEngineAPI;
import project.annotations.ConceptualAPI;
import java.util.*;

@ConceptualAPI
public interface ComputeEngineAPI {
//	compute the sum of primes of a single number
	int computeSumOfPrimes(int n);
	
//	computes the sum of primes for multiple inputs
	List<Integer> computeMulti(List<Integer> inputs);


}
