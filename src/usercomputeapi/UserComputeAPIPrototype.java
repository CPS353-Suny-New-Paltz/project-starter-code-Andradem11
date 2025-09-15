package usercomputeapi;

import java.util.ArrayList;
import java.util.List;

import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIPrototype {
	@NetworkAPIPrototype
	 public List<Integer> prototype(UserComputeAPI userComputeApi, DataSource source, String delimiter, OutputSource output) {
	     if (delimiter == null || delimiter.isBlank()) {
	         delimiter = ";";
	     }

//	     request form compute engine
	     ComputeRequest request = new ComputeRequest(source, delimiter);
	     ComputeResponse response = userComputeApi.computeSumOfPrimes(request);

//	     store sum 
	     List<Integer> results=new ArrayList<>();
	     if(response.isSuccess()) {
	    	 results.add(response.getSum());
	    	 
//	    	 writes result to the output
	    	 output.write(results);
	     } else {
	    	 
//	    	 error message if failed
	    	 System.out.println("Compute failed. ");
	     }
	     return results;
	}
     
}
