package storageComputeAPI;
import java.util.*;
import project.annotations.ProcessAPIPrototype;


public class StorageComputeAPIPrototype {
	
	@ProcessAPIPrototype
	public void prototype(StorageComputeAPI storage) {
//		reads input numbers 
		List<Integer> input= storage.readInput();
		System.out.println("Input: "+ input);
		
//		writes the output
		storage.writeOutput("Sum of primes of "+ input.get(0)+"= (prime sum)");
		
	}
	

}
