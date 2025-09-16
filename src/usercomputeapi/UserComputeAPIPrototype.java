package usercomputeapi;

import java.util.ArrayList;
import java.util.List;

import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIPrototype {
	@NetworkAPIPrototype
	 public List<Integer> prototype(UserComputeAPI user) {
//		variables (input and output)
		DataSource source= null;
		String delimiter = ";";
		OutputSource output= null;

//	     request form compute engine
	     ComputeRequest request = new ComputeRequest(source, delimiter);
	     ComputeResponse response = null;
	     if (user != null ) {
	    	 response=user.computeSumOfPrimes(request);
	    	 
	     }
//	     store sum in a list
	     List<Integer> results=new ArrayList<>();
	     if(response != null && response.isSuccess()) {
	    	 results.add(response.getSum());
	     } else {
	    	 
//	    	 error message if failed
	    	 System.out.println("Compute failed. ");
	     }
	     return results;
	}
     
}
