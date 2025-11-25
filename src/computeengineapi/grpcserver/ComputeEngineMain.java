package computeengineapi.grpcserver;

import usercomputeapi.UserComputeImpl;
import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.grpcclient.StorageComputeGrpcClient;
import usercomputeapi.UserComputeAPI;

public class ComputeEngineMain {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 50051;

//      Connect to Data Store gRPC server
        StorageComputeGrpcClient storageClient = new StorageComputeGrpcClient(host, port);

//      Instantiate compute engine and user compute
        ComputeEngineImpl engine = new ComputeEngineImpl();
        UserComputeAPI userCompute = new UserComputeImpl(storageClient, engine);

//      run a file input/output test
        String inputFile = "input.txt";  // make a test file
        String outputFile = "output.txt";
        userCompute.processFile(inputFile, outputFile);

        System.out.println("Compute Engine finished processing " + inputFile + " -> " + outputFile);
    }
}