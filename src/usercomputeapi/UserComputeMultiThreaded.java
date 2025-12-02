package usercomputeapi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserComputeMultiThreaded implements UserComputeAPI {

    private final UserComputeAPI userCompute;
    private final int maxThreads;

    public UserComputeMultiThreaded(UserComputeAPI userCompute, int maxThreads) {
        // store compute api
        if (userCompute == null) {
            throw new IllegalArgumentException("UserComputeAPI cannot be null");
        }
        this.userCompute = userCompute;

        // set thread count (default 4)
        this.maxThreads = (maxThreads > 0) ? maxThreads : 4;
    }

    @Override
    public ComputeResponse computeSumOfPrimes(ComputeRequest request) {
        return userCompute.computeSumOfPrimes(request);
    }

    @Override
    public void processFile(String inputPath, String outputPath) {
        userCompute.processFile(inputPath, outputPath);
    }

    public List<ComputeResponse> computeMultiRequest(List<ComputeRequest> requests) {
        // validate list
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Request list cannot be null or empty");
        }

        // create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
        List<Future<ComputeResponse>> futures = new ArrayList<>();

        try {
            // submit each request
            for (ComputeRequest req : requests) {
                futures.add(executor.submit(new Callable<ComputeResponse>() {
                    @Override
                    public ComputeResponse call() {
                        return userCompute.computeSumOfPrimes(req);
                    }
                }));
            }

            // collect all results
            List<ComputeResponse> results = new ArrayList<>(requests.size());
            for (Future<ComputeResponse> f : futures) {
                try {
                    results.add(f.get());
                } catch (Exception e) {
                    // add fail result if something breaks
                    results.add(new ComputeResponse(0, ComputeResponse.Status.FAIL));
                    System.err.println("parallel compute error: " + e.getMessage());
                }
            }

            return results;

        } finally {
            // close executor
            executor.shutdown();
        }
    }
}

