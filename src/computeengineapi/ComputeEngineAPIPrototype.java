package computeengineapi;
import project.annotations.ConceptualAPIPrototype;
import java.util.ArrayList;
import java.util.List;

public class ComputeEngineAPIPrototype {
	
	@ConceptualAPIPrototype
	public List<Integer> prototype(ComputeEngineAPI compute) {		
//		store input
		List<Integer> num = new ArrayList<>();
//		example of input
		num.add(10);
		
//		calls engine to get input
		int sum = compute.computeSum(num);
		
		
//		store the sum and returns it
		List<Integer> results=new ArrayList<>();
		results.add(sum);
		
		return results;
	}
}
