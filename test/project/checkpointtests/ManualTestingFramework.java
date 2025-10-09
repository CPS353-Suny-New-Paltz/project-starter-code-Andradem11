package project.checkpointtests;

import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.UserComputeImpl;

public class ManualTestingFramework {
    
    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {
    	
//    	TODO 1: Instantiate  all 3 APIs
    	ComputeEngineImpl engine = new ComputeEngineImpl();
    	StorageComputeImpl storage = new StorageComputeImpl();
    	UserComputeImpl coordinator = new UserComputeImpl(storage, engine);
    	
//      TODO 2: Run a computation with an input 
    	coordinator.processFile(INPUT, OUTPUT);
        // Helpful hint: the working directory of this program is <root project dir>,
        // so you can refer to the files just using the INPUT/OUTPUT constants. You do not 
        // need to (and should not) actually create those files in your repo
    }
}
