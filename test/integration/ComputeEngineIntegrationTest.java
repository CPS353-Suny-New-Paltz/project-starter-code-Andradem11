package integration;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
import usercomputeapi.DataSource;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class ComputeEngineIntegrationTest {

    @Test
    public void testIntegration() {
//      create test input and output
        TestInput input = new TestInput(Arrays.asList(1, 10, 25));
        TestOutput output = new TestOutput();
        TestDataStore dataStore = new TestDataStore(input, output);

//      implementation
        ComputeEngineAPI computeEngine = new ComputeEngineImpl();

        UserComputeAPI userCompute = new UserComputeImpl();

        // DataSource for ComputeRequest
        DataSource source = () -> 100;
        ComputeRequest request = new ComputeRequest(source, null);

        for (Integer i : dataStore.readInput()) {
            int result = computeEngine.computeSum(List.of(i));
            dataStore.writeOut(List.of(String.valueOf(result)));
        }

        List<String> expected = Arrays.asList("0", "0", "0");
        assertEquals(expected, output.getOutput());
    }
}