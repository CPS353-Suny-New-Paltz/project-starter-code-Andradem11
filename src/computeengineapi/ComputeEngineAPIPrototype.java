package computeengineapi;
import project.annotations.ConceptualAPIPrototype;
import java.util.ArrayList;
import java.util.List;

public class ComputeEngineAPIPrototype {
	
	@ConceptualAPIPrototype
	public List<Integer> prototype(ComputeEngineAPI compute) {
		// reference to API
		ComputeEngineAPI engine = compute;
		
		// example of input
		int input = 10;
		
		// calls engine to get input
		int sum = engine.computeSum(input);
		
    
		// store the sum and returns it
		var results = new ArrayList<Integer>(List.of(sum));
		
		return results;
	}
}