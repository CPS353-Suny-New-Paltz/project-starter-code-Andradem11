package testharness;

import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;
import usercomputeapi.UserComputeMultiThreaded;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private UserComputeMultiThreaded coordinator;
	
	@BeforeEach
	public void initializeComputeEngine() {
		UserComputeAPI singleThreaded = new UserComputeImpl(new StorageComputeImpl(), new ComputeEngineImpl());
		coordinator = new UserComputeMultiThreaded(singleThreaded, 4);
		//TODO 2: create an instance of the implementation of your @NetworkAPI; this is the component
		// that the user will make requests to
		// Store it in the 'coordinator' instance variable
	}
	private void cleanup(List<Path> paths) {
		for (Path p : paths) {
	        try {
	            Files.deleteIfExists(p);
	        } catch (IOException e) {
	            System.err.println("Failed to delete temporary file: " + p);
	        }
	    }
    }
	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int nthreads = 4;
		List<TestUser> testUsers = new ArrayList<>();
		for (int i = 0; i < nthreads; i++) {
			testUsers.add(new TestUser(coordinator));
		}
		
		// Run single threaded
		List<Path> singleThreadFilePrefix = new ArrayList<>();
		
        for (int i = 0; i < nthreads; i++) {
            Path temp = Files.createTempFile("singleThreadOut", ".tmp");
            singleThreadFilePrefix.add(temp);
            testUsers.get(i).run(temp.toString());
        }
		
		// Run multi threaded
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Future<?>> results = new ArrayList<>();
        List<Path> multiThreadFilePrefix = new ArrayList<>();
        
        for (int i = 0; i < nthreads; i++) {
            final Path temp = Files.createTempFile("multiThreadOut", ".tmp");
            multiThreadFilePrefix.add(temp);
            final TestUser testUser = testUsers.get(i);
            
            results.add(threadPool.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    testUser.run(temp.toString());
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
		threadPool.shutdown();
		
		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix);
		Assertions.assertEquals(singleThreaded, multiThreaded);
		
//		Clean up temp files
		cleanup(singleThreadFilePrefix);
        cleanup(multiThreadFilePrefix);
		
	}

	private List<String> loadAllOutput(List<Path> paths) throws IOException {
        List<String> result = new ArrayList<>();
        for (Path p : paths) {
            result.addAll(Files.readAllLines(p));
        }
        return result;
    }
	
	@Test
	public void smokeTest() throws Exception {
	    int nthreads = 3;
	    List<TestUser> testUsers = new ArrayList<>();
	    List<Path> tempFiles = new ArrayList<>();

	    for (int i = 0; i < nthreads; i++) {
	        testUsers.add(new TestUser(coordinator));
	    }

	    for (TestUser user : testUsers) {
	        Path temp = Files.createTempFile("smokeTest", ".tmp");
	        tempFiles.add(temp);
	        user.run(temp.toString());
	    }

	    // Validate that files exist
	    for (Path p : tempFiles) {
	        Assertions.assertTrue(Files.exists(p), "Output file should exist: " + p);
	    }

	    cleanup(tempFiles);
	}

}
