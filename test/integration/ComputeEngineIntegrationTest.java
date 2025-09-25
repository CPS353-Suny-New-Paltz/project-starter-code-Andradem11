package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;

public class ComputeEngineIntegrationTest {

    @Test
    public void testComputeEngine() {
//    	inpput and output
    	TestInput input = new TestInput(Arrays.asList(1, 10, 25));
        TestOutput output = new TestOutput();
        TestDataStore dataStore = new TestDataStore(input, output);

//        use empty engine implementation
        ComputeEngineAPI computeEngine = new ComputeEngineImpl();

//      Use UserComputeImpl
        UserComputeAPI userCompute = new UserComputeImpl();

//      Prepare ComputeRequest
        DataSource source = new DataSource() {
            @Override
            public int getLimit() {
                return 100; // arbitrary value
            }
        };

        ComputeRequest request = new ComputeRequest(source, null); // null delimiter

//      Call compute engine
        ComputeResponse response = userCompute.computeSumOfPrimes(request);

        for (Integer i : input.getInput()) {
            int result = computeEngine.computeSum(List.of(i));
            output.write(String.valueOf(result));
        }

        List<String> expected = List.of("0", "0", "0");
        assertEquals(expected, output.getOutput());
    }
}
