package storagecomputeapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestStorageComputeAPI {
    @Test
    public void smokeTestStorageCompute() throws IOException {
//		create storage implementation
        StorageComputeAPI storage = new StorageComputeImpl();
        
//      Create a temporary file for testing
        Path tempFile = Files.createTempFile("testOutput", ".txt");
        tempFile.toFile().deleteOnExit();
        
        StorageResponse response = storage.writeOutput(List.of(1, 2, 3), tempFile.toString());
        assertTrue(response.getStatus() == StorageResponse.Status.SUCCESS);
    }

    @Test
    public void testStorageComputeImplInstantiation() throws IOException {
        StorageComputeImpl impl = new StorageComputeImpl();
        
//      Create a temporary file for reading (empty file)
        Path tempFile = Files.createTempFile("testInput", ".txt");
        tempFile.toFile().deleteOnExit();
        
        assertTrue(impl.readInput(tempFile.toString()).isEmpty());
    }
    
    
//    validation test
    @Test
    public void testReadInputValidation() {
    	StorageComputeAPI storage = new StorageComputeImpl();
    	
//    	null should return empty list
    	List<Integer> r1 = storage.readInput(null);
    	assertTrue(r1.isEmpty(), "readInput(null) returns empty list");
    	
//    	empty should return empty list
    	List<Integer> r2 = storage.readInput("  ");
    	assertTrue(r2.isEmpty(), "readInput(blank) returns empty list");
    	
//    	non-existing file should return empty list
    	List<Integer> r3 = storage.readInput("noFile.txt");
    	assertTrue(r3.isEmpty(), "readInput(no File) returns empty list");
    }
    @Test
    public void testWriteOutputValidation() throws IOException {
    	StorageComputeAPI storage = new StorageComputeImpl();
    	
//    	temporary file
    	Path tempFile = Files.createTempFile("OutputTest","");
    	tempFile.toFile().deleteOnExit();
    	
//    	null data returns FAIL
    	StorageResponse r1 = storage.writeOutput(null, tempFile.toString());
    	assertEquals(StorageResponse.Status.FAIL, r1.getStatus(),
    			"writeOutput(null data) should return FAIL");
    	
//    	empty list returns FAIL
    	StorageResponse r2 = storage.writeOutput(List.of(), tempFile.toString());
    	assertEquals(StorageResponse.Status.FAIL, r2.getStatus(),
    			"writeOutput(empty list) should return FAIL");
    	
//    	null path returns FAIL
    	StorageResponse r3 = storage.writeOutput(List.of(2,5,10), null);
    	assertEquals(StorageResponse.Status.FAIL, r3.getStatus(),
    			"writeOutput(null path) should return FAIL");
    	
//    	empty path returns FAIL
    	StorageResponse r4 = storage.writeOutput(List.of(2,5,10), "  ");
    	assertEquals(StorageResponse.Status.FAIL, r1.getStatus(),
    			"writeOutput(empty path) should return FAIL");
    }
} 
