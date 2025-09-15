package usercomputeapi;

import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIPrototype {

 @NetworkAPIPrototype
 public void prototype(UserComputeAPI userComputeApi, DataSource source, String delimiter) {
     if (delimiter == null || delimiter.isBlank()) {
         delimiter = ";";
     }

     ComputeRequest request = new ComputeRequest(source, delimiter);
     ComputeResponse response = userComputeApi.computeSumOfPrimes(request);

     if (response.isSuccess()) {
         System.out.println("Sum of primes: " + response.getSum());
     } else {
         System.out.println("Compute failed.");
     }
 }
}
