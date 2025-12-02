package testharness;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import usercomputeapi.UserComputeAPI;

public class TestUser {

    private final UserComputeAPI coordinator;

    public TestUser(UserComputeAPI coordinator) {
        if (coordinator == null) {
            throw new IllegalArgumentException("UserComputeAPI cannot be null");
        }
        this.coordinator = coordinator;
    }

    public void run(List<Integer> numbers, String outputPath) {
        try {
            // Temporary input file
            Path tempInput = Files.createTempFile("testInput", ".txt");
            for (Integer n : numbers) {
                Files.writeString(tempInput, n + "\n", java.nio.file.StandardOpenOption.APPEND);
            }

            // Delegate to coordinator (works for local or gRPC)
            coordinator.processFile(tempInput.toString(), outputPath);

            Files.deleteIfExists(tempInput);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or delete temp input file", e);
        }
    }
}
