package testharness;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeMultiThreaded;
import storagecomputeapi.StorageComputeImpl;
import computeengineapi.ComputeEngineImpl;

public class TestMultiUser {

    private UserComputeMultiThreaded coordinator;

    @BeforeEach
    public void initializeComputeEngine() 
    {
        UserComputeAPI singleThreaded = new usercomputeapi.UserComputeImpl(
            new StorageComputeImpl(), new ComputeEngineImpl()
        );
        coordinator = new UserComputeMultiThreaded(singleThreaded, 4);
    }

    private void cleanup(List<Path> paths) 
    {
        for (Path p : paths) 
        {
            try 
            {
                Files.deleteIfExists(p);
            } 
            catch (IOException e) 
            {
                System.err.println("Failed to delete temporary file: " + p);
            }
        }
    }

    @Test
    public void compareMultiAndSingleThreaded() throws Exception 
    {
        int nthreads = 4;
        List<TestUser> testUsers = new ArrayList<>();
        for (int i = 0; i < nthreads; i++) 
        {
            testUsers.add(new TestUser(coordinator));
        }

        List<List<Integer>> inputs = List.of(
            List.of(1, 5, 10),
            List.of(2, 7, 11),
            List.of(3, 6, 9),
            List.of(4, 8, 12)
        );

        List<Path> singleThreadFiles = new ArrayList<>();
        for (int i = 0; i < nthreads; i++) 
        {
            Path tempOut = Files.createTempFile("singleThreadOut", ".tmp");
            singleThreadFiles.add(tempOut);
            testUsers.get(i).run(inputs.get(i), tempOut.toString());
        }

        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Path> multiThreadFiles = new ArrayList<>();
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < nthreads; i++) 
        {
            final Path tempOut = Files.createTempFile("multiThreadOut", ".tmp");
            multiThreadFiles.add(tempOut);
            final TestUser user = testUsers.get(i);
            final List<Integer> userInput = inputs.get(i);

            futures.add(
                threadPool.submit(
                    () -> 
                    {
                        user.run(userInput, tempOut.toString());
                        return null;
                    }
                )
            );
        }

        for (Future<?> f : futures) 
        {
            f.get();
        }
        threadPool.shutdown();

        List<String> singleThreaded = loadAllOutput(singleThreadFiles);
        List<String> multiThreaded = loadAllOutput(multiThreadFiles);
        Assertions.assertEquals(singleThreaded, multiThreaded, 
            "Single-threaded vs Multi-threaded outputs differ"
        );

        cleanup(singleThreadFiles);
        cleanup(multiThreadFiles);
    }

    private List<String> loadAllOutput(List<Path> paths) throws IOException 
    {
        List<String> result = new ArrayList<>();
        for (Path p : paths) 
        {
            result.addAll(Files.readAllLines(p));
        }
        return result;
    }

}
