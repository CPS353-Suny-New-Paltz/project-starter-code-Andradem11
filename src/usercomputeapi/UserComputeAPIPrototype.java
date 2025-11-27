package usercomputeapi;

import java.util.ArrayList;
import java.util.List;

import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIPrototype {

    @NetworkAPIPrototype
    public List<Integer> prototype(UserComputeAPI user) {
        // reference to API
        UserComputeAPI api = user;

        // variables (input and output) using lambda for DataSource
        DataSource source = () -> 10;

        String delimiter = ";";

        // request from compute engine
        ComputeRequest request = new ComputeRequest(source, delimiter);
        ComputeResponse response = api.computeSumOfPrimes(request);

        // store sum in a list
        List<Integer> results = new ArrayList<>();
        
        // add sum if computation succeeded, otherwise print error
        if (response != null && response.isSuccess()) {
            results.add(response.getSum());
        } else {
            System.out.println("Compute failed.");
        }

        return results;
    }
}
