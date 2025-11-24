package client;

import java.util.Scanner;
import usercomputeapi.UserComputeImpl;
import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.grpcclient.StorageComputeGrpcClient;
import usercomputeapi.UserComputeAPI;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter input file path: ");
        String input = sc.nextLine();

        System.out.print("Enter output file path: ");
        String output = sc.nextLine();

        // Connect to storage server
        StorageComputeGrpcClient storageClient = new StorageComputeGrpcClient("localhost", 50051);
        ComputeEngineImpl engine = new ComputeEngineImpl();
        UserComputeAPI userCompute = new UserComputeImpl(storageClient, engine);

        userCompute.processFile(input, output);

        System.out.println("Processing done! Check output file: " + output);
    }
}
