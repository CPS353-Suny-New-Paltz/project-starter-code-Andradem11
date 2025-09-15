package computeengineapi;
import project.annotations.ConceptualAPIPrototype;
import java.util.List;

public class ComputeEngineAPIPrototype {
	@ConceptualAPIPrototype
	public void prototype(ComputeEngineAPI compute, List<Integer> num) {
		int sum= compute.computeSum(num);
		System.out.println("Computed sum: "+sum);
		
	}
}
