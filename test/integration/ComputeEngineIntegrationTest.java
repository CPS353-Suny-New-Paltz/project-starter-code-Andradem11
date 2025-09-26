package integration;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

    @Test
    public void testIntegration() {
//      ProcessAPI implementation
        TestStorageComputeAPIImpl dataStore = new TestStorageComputeAPIImpl(Arrays.asList(1, 10, 25));

//      ConceptualAPI
        ComputeEngineAPI computeEngine = new ComputeEngineImpl();

//      NetworkAPI
        UserComputeAPI userCompute = new UserComputeImpl();

//      DataSource for ComputeRequest
        DataSource source = () -> 100;
        ComputeRequest request = new ComputeRequest(source, null);

//      compute engine through input
        for (Integer i : dataStore.readInput()) {
            int result = computeEngine.computeSum(List.of(i));
            dataStore.writeOutput(List.of(result));
        }

        List<String> expected = List.of("0", "0", "0");
        assertEquals(expected, dataStore.getTestOutput());
    }
}