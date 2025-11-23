package testharness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;
import usercomputeapi.UserComputeAPI;


public class TestUser {
	
	// TODO 3: change the type of this variable to the name you're using for your
	// @NetworkAPI interface; also update the parameter passed to the constructor
	private final UserComputeAPI coordinator;

	public TestUser(UserComputeAPI coordinator) {
		if (coordinator == null) {
			throw new IllegalArgumentException("UserComputeAPI cannot be null");
		}
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";
		
		// TODO 4: Call the appropriate method(s) on the coordinator to get it to 
		// run the compute job specified by inputPath, outputPath, and delimiter

		if (coordinator instanceof usercomputeapi.UserComputeImpl) {
            ((usercomputeapi.UserComputeImpl) coordinator).processFile(inputPath, outputPath);
        } else {
            throw new UnsupportedOperationException(
                "Coordinator must implement processFile to handle full workflow"
            );
        }
	}

}

