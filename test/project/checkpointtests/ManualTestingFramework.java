package project.checkpointtests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.UserComputeImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;

public class ManualTestingFramework {
    
    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {
    	
//    	TODO 1: Instantiate  all 3 APIs
    	ComputeEngineImpl engine = new ComputeEngineImpl();
    	StorageComputeImpl storage = new StorageComputeImpl();
    	UserComputeImpl coordinator = new UserComputeImpl(storage, engine);
    	
//      TODO 2: Run a computation with an input 
    	List<Integer> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT))) {
            String line = reader.readLine();
            while (line != null) {
                String data = line.trim();
                if (!data.isEmpty()) {
                    input.add(Integer.parseInt(data));
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    	
    	List<Integer> result = new ArrayList<>();
    	for( Integer n : input) {
    		DataSource source = new DataSource() {
        		@Override
        		public int getLimit() {
        			return n;
        		}
    		};
    		ComputeRequest request = new ComputeRequest(source, ",");
        	ComputeResponse response = coordinator.computeSumOfPrimes(request);
        	if(response.isSuccess()) {
        		result.add(response.getSum());
        	}else {
        		result.add(0);
        	}
    	}
    	try (FileWriter writer = new FileWriter(OUTPUT)) {
            for (int j = 0; j < result.size(); j++) {
                writer.write(String.valueOf(result.get(j)));
                if (j < result.size() - 1) {
                    writer.write(",");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Helpful hint: the working directory of this program is <root project dir>,
        // so you can refer to the files just using the INPUT/OUTPUT constants. You do not 
        // need to (and should not) actually create those files in your repo
    }
}
