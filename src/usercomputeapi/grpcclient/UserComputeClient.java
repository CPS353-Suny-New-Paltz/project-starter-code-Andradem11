package usercomputeapi.grpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import usercompute.UserComputeServiceGrpc;
import usercompute.Usercompute;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class UserComputeClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // File or list of numbers
        System.out.println("Enter either a file path or a list of numbers separated by commas:");
        String input = scanner.nextLine();

        // output file name in project folder
        String projectFolder = System.getProperty("user.dir");
        System.out.println("Enter output file name (will be saved in project folder):");
        String outputFileName = scanner.nextLine();
        String outputPath = projectFolder + "/" + outputFileName;

        // optional delimiter (default ;)
        System.out.println("Enter delimiter (optional, press Enter for default ';'):");
        String delimiter = scanner.nextLine();
        delimiter = (delimiter == null || delimiter.isBlank()) ? ";" : delimiter;

        // connect to gRPC server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();
        UserComputeServiceGrpc.UserComputeServiceBlockingStub stub =
                UserComputeServiceGrpc.newBlockingStub(channel);

        try {
            // Decide if the input is a real file path
            boolean isFile = Files.exists(Path.of(input));

            if (isFile) {
                // Use the processFile RPC, which calls UserComputeImpl.processFile
                // and talks to the storage server via StorageComputeGrpcClient
                Usercompute.ProcessFileRequest request = Usercompute.ProcessFileRequest.newBuilder()
                        .setInputPath(input)
                        .setOutputPath(outputPath)
                        .setDelimiter(delimiter)
                        .build();

                Usercompute.ProcessFileResponse response = stub.processFile(request);

                System.out.println("Success: " + response.getSuccess());
                System.out.println("Message: " + response.getMessage());

            } else {
                // Treat input as comma-separated numbers and call computeSumOfPrimes for each
                String[] numbers = input.contains(",")
                        ? input.split(",")
                        : new String[]{ input };

                StringBuilder outputContent = new StringBuilder();

                // process each input number
                for (String num : numbers) {
                    String numStr = num.trim();
                    int number;

                    // parse input value
                    try {
                        number = Integer.parseInt(numStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number input: " + numStr);
                        outputContent.append("0").append(delimiter);
                        continue;
                    }

                    // build gRPC request
                    Usercompute.ComputeRequest request = Usercompute.ComputeRequest.newBuilder()
                            .setNumber(number)
                            .setDelimiter(delimiter)
                            .build();

                    Usercompute.ComputeResponse response = stub.computeSumOfPrimes(request);

                    if (response.getSuccess()) {
                        outputContent.append(response.getSum()).append(delimiter);
                    } else {
                        System.out.println("Computation failed for number: " + number
                                + " (" + response.getMessage() + ")");
                        outputContent.append("0").append(delimiter);
                    }
                }

                // remove last delimiter
                if (outputContent.length() > 0) {
                    outputContent.setLength(outputContent.length() - delimiter.length());
                }

                // write output file locally
                try (FileWriter writer = new FileWriter(outputPath)) {
                    writer.write(outputContent.toString());
                } catch (IOException e) {
                    System.out.println("Error writing output file: " + e.getMessage());
                }

                System.out.println("File processed successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // close grpc and scanner
            channel.shutdown();
            scanner.close();
        }
    }
}
