package storagecomputeapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageComputeImpl implements StorageComputeAPI {

    /**
     * validation:
     * -inputPath cannot be empty and must refer to a existing file
     * Returns an empty list
     * -Lines that cannot be parsed as integers are skipped 
     */
    @Override
    public List<Integer> readInput(String inputPath) {
        if (inputPath == null || inputPath.isBlank()) {
            System.err.println("readInput: Input cannot be empty.");
            return new ArrayList<>();
        }

        File file = new File(inputPath);
        if (!file.exists() || !file.isFile()) {
            System.err.println("readInput: Input file does not exist or is not a file.");
            return new ArrayList<>();
        }

        List<Integer> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    try {
                        data.add(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("readInput: Skipping invalid number: |" + line + "|");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("readInput: Error while reading file: " + e.getMessage());
            return new ArrayList<>();
        }
        return data;
    }

    /**
     * Validation
     * - data  and outputPath cannot be empty.
     * Returns FAIL StorageResponse.
     */
    @Override
    public StorageResponse writeOutput(List<Integer> data, String outputPath) {
        if (data == null || data.isEmpty()) {
            return new StorageResponse(StorageResponse.Status.FAIL, "NO data to write.");
        }
        if (outputPath == null || outputPath.isBlank()) {
            return new StorageResponse(StorageResponse.Status.FAIL, "Output path cannot be empty.");
        }

        File outFile = new File(outputPath);
        try (FileWriter writer = new FileWriter(outFile)) {
            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i).toString());
                // Add a comma between numbers, but not after the last one
                writer.write((i < data.size() - 1) ? "," : "");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new StorageResponse(StorageResponse.Status.FAIL, "Error writing file." + e.getMessage());
        }

        // Successfully wrote data
        return new StorageResponse(StorageResponse.Status.SUCCESS, "Data written successfully");
    }
}
