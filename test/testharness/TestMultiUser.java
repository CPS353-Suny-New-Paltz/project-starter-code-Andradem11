package testharness;

import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMultiUser {
	
	// TODO 1: change the type of this variable to the name you're using for your @NetworkAPI
	// interface
	private UserComputeAPI coordinator;
	
	@BeforeEach
	public void initializeComputeEngine() {
		coordinator = new UserComputeImpl(new StorageComputeImpl(), new ComputeEngineImpl());
		
		//TODO 2: create an instance of the implementation of your @NetworkAPI; this is the component
		// that the user will make requests to
		// Store it in the 'coordinator' instance variable
	}
	public void cleanup() {
    }
	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int nthreads = 4;
		List<TestUser> testUsers = new ArrayList<>();
		for (int i = 0; i < nthreads; i++) {
			testUsers.add(new TestUser(coordinator));
		}
		
		// Run single threaded
		String singleThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.singleThreadOut.tmp";
		for (int i = 0; i < nthreads; i++) {
			File singleThreadedOut = 
					new File(singleThreadFilePrefix + i);
			singleThreadedOut.deleteOnExit();
			testUsers.get(i).run(singleThreadedOut.getCanonicalPath());
		}
		
		// Run multi threaded
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<?>> results = new ArrayList<>();
		String multiThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.multiThreadOut.tmp";
		for (int i = 0; i < nthreads; i++) {
			File multiThreadedOut = 
					new File(multiThreadFilePrefix + i);
			multiThreadedOut.deleteOnExit();
			String multiThreadOutputPath = multiThreadedOut.getCanonicalPath();
			TestUser testUser = testUsers.get(i);
			results.add(threadPool.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    testUser.run(multiThreadOutputPath);
                    return null;
                }
            }));
		}
		
		for (Future<?> future : results) {
            try {
                future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
		
		
		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix, nthreads);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix, nthreads);
		Assertions.assertEquals(singleThreaded, multiThreaded);
	}

	private List<String> loadAllOutput(String prefix, int nthreads) throws IOException {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < nthreads; i++) {
			File multiThreadedOut = 
					new File(prefix + i);
			result.addAll(Files.readAllLines(multiThreadedOut.toPath()));
		}
		return result;
	}
	@Test
    public void smokeTest() {
//        List<String> requests = List.of("test1", "test2", "test3");
//        List<String> results = UserComputeImpl.processFile(requests);
//        Assertions.assertEquals(requests.size(), results.size());
    }
}
