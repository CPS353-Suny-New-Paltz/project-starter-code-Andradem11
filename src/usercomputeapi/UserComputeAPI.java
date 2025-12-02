package usercomputeapi;
import project.annotations.NetworkAPI;
@NetworkAPI
public interface UserComputeAPI {
	// request to compute sum of primes
	ComputeResponse computeSumOfPrimes(ComputeRequest request);
	// process input file and write output
	void processFile(String inputPath, String outputPath);
	
}

