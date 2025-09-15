package computeengineapi;
import project.annotations.ConceptualAPIPrototype;


import java.util.*;

public class ComputeEngineAPIPrototype {
	@ConceptualAPIPrototype
	public void prototype(ComputeEngineAPI compute) {
//		single input example
		int n=10;
		int result=compute.computeSumOfPrimes(n);
		System.out.println("Sum of prime "+n+" = "+ result);
		
//		multi-input example
		List<Integer> inputs= new ArrayList<>();
		inputs.add(10);
		inputs.add(15);
		List<Integer> results= compute.computeMulti(inputs);
		System.out.println("Multi-input results: " + results);
	}
}
