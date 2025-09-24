package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;

public class TestComputeEngineIntegration {
	
	public void TestComputeEngine() {
//		create input and output
		TestInput input = new TestInput(Arrays.asList(1, 10, 25));
		TestOutput output = new TestOutput();
		
//		create a data store
		TestDataStore data = new TestDataStore(input, output);
		
//		read input from data store
		List<Integer> inputNum = data.readInput();
		
//		compute engine implementation
		ComputeEngineAPI engine = new ComputeEngineImpl();
		
//		call input and store it to string
		for(Integer num : inputNum) {
			int result = engine.computeSum(Arrays.asList(num));
			data.writeOut(Arrays.asList(String.valueOf(result)));
		}
		
//		output
		List<String> out = output.getOutput();
		assertEquals(Arrays.asList("0","0","0"), out);
	}

}
