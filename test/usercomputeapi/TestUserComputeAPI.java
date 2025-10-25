package usercomputeapi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageComputeImpl;
import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineImpl;

public class TestUserComputeAPI {
	@Test
	public void smokeTestUserComputeSuccess() throws IOException {
        StorageComputeAPI storage = new StorageComputeImpl();
        ComputeEngineAPI engine = new ComputeEngineImpl();
        UserComputeAPI user = new UserComputeImpl(storage, engine);
        
//      Temporary file for storage output
        Path tempFile = Files.createTempFile("userOutput", ".txt");
        tempFile.toFile().deleteOnExit();

        storage.writeOutput(List.of(5), tempFile.toString());
        ComputeRequest request = new ComputeRequest(new DataSource() {
        	@Override
        	public int getLimit() {
        		return 5;
        	}
        },";");
        
        ComputeResponse response = user.computeSumOfPrimes(request);
        assertTrue(response.isSuccess());
        assertEquals(10, response.getSum());
    }
	
	@Test
	public void smokeTestUserComputeFail() {
		StorageComputeAPI storage = new StorageComputeImpl();
        ComputeEngineAPI engine = new ComputeEngineImpl();
        UserComputeAPI user = new UserComputeImpl(storage, engine);
        
        ComputeRequest request = new ComputeRequest(null, ";");
        ComputeResponse response = user.computeSumOfPrimes(request);

        assertEquals(ComputeResponse.Status.FAIL, response.getStatus(),
                "Expected FAIL when DataSource is null");
	}
	
//	validation test
	@Test
	public void testComputeSumOfPrimesValidation() {
		StorageComputeAPI storage = new StorageComputeImpl();
		ComputeEngineAPI engine = new ComputeEngineImpl();
		UserComputeAPI user = new UserComputeImpl(storage, engine);
		
//		null request
		ComputeResponse r1 = user.computeSumOfPrimes(null);
		assertEquals(ComputeResponse.Status.FAIL, r1.getStatus(), 
				"null request should return FAIL.");
		
//		null DataSource 
		ComputeRequest requestNull = new ComputeRequest(null,";");
		ComputeResponse r2 = user.computeSumOfPrimes(requestNull);
		assertEquals(ComputeResponse.Status.FAIL, r2.getStatus(),
				"null DataSource should return FAIL");
		
//		negative number
		ComputeRequest requestNegative = new ComputeRequest(new DataSource() {
	        @Override
	        public int getLimit() {
	            return -10;
	        }
	    }, ";");
		ComputeResponse r3 = user.computeSumOfPrimes(requestNegative);
		assertEquals(ComputeResponse.Status.FAIL, r3.getStatus(), 
				"negative number should return FAIL");
		
	}

}

