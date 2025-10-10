package storagecomputeapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageComputeImpl implements StorageComputeAPI {
	
	@Override
	public List<Integer> readInput(String inputPath) {
		
		List<Integer> data = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(inputPath))){
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.isBlank()) {
					data.add(Integer.parseInt(line.trim()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	@Override
	public StorageResponse writeOutput(List<Integer> data, String outputPath) {
		if(data == null || data.isEmpty()) {
			return new StorageResponse(StorageResponse.Status.FAIL, "NO data to write.");
		}
		
		try(FileWriter writer = new FileWriter(outputPath)){
			for (int i = 0; i < data.size(); i++) {
				writer.write(data.get(i).toString());
				if(i < data.size() -1) {
					writer.write(",");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return new StorageResponse(StorageResponse.Status.FAIL, "Error writing file.");
		}
		
		return new StorageResponse(StorageResponse.Status.SUCCESS, "Data written successfully");
	}
}
