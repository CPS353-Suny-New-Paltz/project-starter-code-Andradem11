package usercomputeapi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserComputeMultiThreaded implements UserComputeAPI{
	private final UserComputeAPI userCompute;
	private final int maxThreads;
	
	public UserComputeMultiThreaded(UserComputeAPI userCompute, int maxThreads) {
		if (userCompute == null) {
			throw new IllegalArgumentException("UserComputeAPI cannot be null");
		}
		this.userCompute = userCompute;
		if (maxThreads > 0) {
	        this.maxThreads = maxThreads;
	    } else {
	        this.maxThreads = 4; // default to 4 threads
	    }
	}
	
	@Override
	public ComputeResponse computeSumOfPrimes(ComputeRequest request) {
		return userCompute.computeSumOfPrimes(request);
	}
	
	public List<ComputeResponse> computeMultiRequest(List<ComputeRequest> requests) {
		if(requests == null || requests.isEmpty()) {
			throw new IllegalArgumentException("Request list cannot be null or empty");
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
		List<Future<ComputeResponse>> futures = new ArrayList<>();
		
//		submit each request 
		try {
			for (ComputeRequest request : requests) {
				futures.add(executor.submit(new Callable<ComputeResponse>() {
	                @Override
	                public ComputeResponse call() {
	                    return userCompute.computeSumOfPrimes(request);
	                }
	            }));
			}
			
	//		Collect results
			List<ComputeResponse> results = new ArrayList<>(requests.size());
			for (Future<ComputeResponse> f : futures) {
	            try {
	                results.add(f.get());
	            } catch (Exception e) {
	                results.add(new ComputeResponse(0, ComputeResponse.Status.FAIL));
	                System.err.println("Error during parallel computation: " + e.getMessage());
	            }
	        }
	        return results;
		} finally {
//			Shutdown executor
			executor.shutdown();
		}
	}
	public void processFile(String inputPath, String outputPath) {
        if (userCompute instanceof UserComputeImpl) {
            ((UserComputeImpl) userCompute).processFile(inputPath, outputPath);
        } else {
            throw new UnsupportedOperationException(
                    "Underlying UserComputeAPI does not support processFile"
            );
        }
    }
}
