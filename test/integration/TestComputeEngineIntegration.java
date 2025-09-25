package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.TestStorageComputeAPIImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;

public class TestComputeEngineIntegration {

    @Test
    public void testComputeEngine() {
        StorageComputeAPI storage = new TestStorageComputeAPIImpl();
        ComputeEngineAPI engine = new ComputeEngineImpl();
        UserComputeAPI user = new UserComputeImpl();

        for (Integer num : storage.readInput()) {
            int result = engine.computeSum(List.of(num));
            storage.writeOutput(List.of(result));
        }

        usercomputeapi.DataSource source = new usercomputeapi.DataSource() {
            @Override
            public int getLimit() {
                return 100;
            }
        };
        ComputeRequest request = new ComputeRequest(source, ";");
        ComputeResponse response = user.computeSumOfPrimes(request);

        assertEquals(List.of("0", "0", "0"), ((TestStorageComputeAPIImpl) storage).getTestOutput());
    }
}

