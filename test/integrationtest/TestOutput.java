package integrationtest;
import java.util.List;
import java.util.ArrayList;

public class TestOutput {
	private final List<String> output = new ArrayList<>();
	
	public void write(String value) {
		output.add(value);
	}
	public List<String> getOutput(){
		return output;
	}

}
