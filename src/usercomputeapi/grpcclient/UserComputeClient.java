package usercomputeapi.grpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import usercompute.UserComputeServiceGrpc;
import usercompute.Usercompute;

import java.util.Scanner;

public class UserComputeClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//      Part 2c.1: File or list of numbers
        System.out.println("Enter either a file path or a list of numbers separated by commas:");
        String input = scanner.nextLine();

//      Part 2c.2: Output file
        System.out.println("Enter output file path:");
        String outputPath = scanner.nextLine();

//      Part 2c.3: Optional delimiter
        System.out.println("Enter delimiter (optional, press Enter for default ';'):");
        String delimiter = scanner.nextLine();
        if (delimiter == null || delimiter.isBlank()) {
            delimiter = ";";
        }

//      Part 2b: Connect to gRPC server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        UserComputeServiceGrpc.UserComputeServiceBlockingStub stub =
                UserComputeServiceGrpc.newBlockingStub(channel);

//      Build gRPC request
        Usercompute.ProcessFileRequest request = Usercompute.ProcessFileRequest.newBuilder()
                .setInputPath(input)
                .setOutputPath(outputPath)
                .setDelimiter(delimiter)
                .build();

        try {
//          Part 2d: Call server
            Usercompute.ProcessFileResponse response = stub.processFile(request);

            if (response.getSuccess()) {
                System.out.println("File processed successfully!");
            } else {
                System.out.println("Processing failed: " + response.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error during gRPC call: " + e.getMessage());
        } finally {
            channel.shutdown();
        }

        scanner.close();
    }
}

