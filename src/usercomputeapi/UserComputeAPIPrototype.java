package usercomputeapi;
import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIPrototype {
	
	@NetworkAPIPrototype
	public void prototype(UserComputeAPI user, DataSource source, String delimiter) {
		
		if(delimiter == null || delimiter.isBlank()) delimiter = ";";
		ComputeRequest request = new ComputeRequest(source, delimiter);
		ComputeResponse response = user.computeSumOfPrimes(request);
		if(response.isSuccess()) {
			System.out.println("Sum of primes: "+ response.getSum());
		}else {
			System.out.println("Compute failed.");
		}
	}
}
