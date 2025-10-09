package storagecomputeapi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestStorageComputeAPI {
    @Test
    public void smokeTestStorageCompute() {
        StorageComputeAPI storage = new StorageComputeImpl();
        StorageResponse response = storage.writeOutput(List.of(1, 2, 3), null);
        assertTrue(response.getStatus() == StorageResponse.Status.SUCCESS);
    }

    @Test
    public void testStorageComputeImplInstantiation() {
        StorageComputeImpl impl = new StorageComputeImpl();
        assertTrue(impl.readInput(null).isEmpty());
    }
} 