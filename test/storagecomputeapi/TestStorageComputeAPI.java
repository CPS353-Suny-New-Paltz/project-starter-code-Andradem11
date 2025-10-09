package storagecomputeapi;

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
} 
