package computeengineapi;

import project.annotations.ConceptualAPIPrototype;
import java.util.ArrayList;
import java.util.List;

public class ComputeEngineAPIPrototype {

    @ConceptualAPIPrototype
    public List<Integer> prototype(ComputeEngineAPI compute) {
//      reference to API
        ComputeEngineAPI slowEngine = compute;

//      create a fast version
        ComputeEngineAPI fastEngine = new ComputeEngineFastImpl();

//      Example input
        int input = 10;

//      Compute sums
        int slowSum = slowEngine.computeSum(input);
        int fastSum = fastEngine.computeSum(input);

//      Store results and return
        List<Integer> results = new ArrayList<>();
        results.add(slowSum);
        results.add(fastSum);

        return results;
    }
}
