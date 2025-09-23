package integration;

import java.util.List;

public class TestDataStore {
	private final TestInput input;
	private final TestOutput output;
	
	public TestDataStore(TestInput input, TestOutput output) {
		this.input = input;
		this.output = output;
	}
	
//	read integers from input
	public List<Integer> readInput(){
		return input.getInput();
	}
	
//	writes string result (output)
	public void writeOut(List<String> results) {
		for(String s : results) {
			output.write(s);
		}
	}

}
