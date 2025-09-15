package computeengineapi;
import project.annotations.ConceptualAPIPrototype;
import java.util.ArrayList;
import java.util.List;

public class ComputeEngineAPIPrototype {
	
	@ConceptualAPIPrototype
	public List<Integer> prototype(ComputeEngineAPI compute, List<Integer> num) {
//		call engine to get the sum
		int sum = compute.computeSum(num);
		
//		store sum and sent it to storage/output
		List<Integer> results = new ArrayList<>();
		results.add(sum);
		
//		return the list of results
		return results;
	}
}
