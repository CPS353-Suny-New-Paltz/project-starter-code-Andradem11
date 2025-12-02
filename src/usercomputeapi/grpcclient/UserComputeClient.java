package usercomputeapi.grpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import usercompute.UserComputeServiceGrpc;
import usercompute.Usercompute;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserComputeClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

<<<<<<< Updated upstream
//      Input numbers separated by commas
        System.out.println("Enter a list of numbers separated by commas:");
=======
        // File or list of numbers 
        System.out.println("Enter either a file path or a list of numbers separated by commas:");
>>>>>>> Stashed changes
        String input = scanner.nextLine();

//      Output file name in project folder
        String projectFolder = System.getProperty("user.dir");
        System.out.println("Enter output file name (will be saved in project folder):");
        String outputFileName = scanner.nextLine();
        String outputPath = projectFolder + "/" + outputFileName;

//      Optional delimiter
        System.out.println("Enter delimiter (optional, press Enter for default ';'):");
        String delimiter = scanner.nextLine();
        if (delimiter == null || delimiter.isBlank()) {
            delimiter = ";";
        }

//      Connect to gRPC server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();
        UserComputeServiceGrpc.UserComputeServiceBlockingStub stub =
                UserComputeServiceGrpc.newBlockingStub(channel);

        try {
            String[] numbers;
            if (input.contains(",")) {
                numbers = input.split(",");
            } else {
                numbers = new String[1];
                numbers[0] = input;
            }

            StringBuilder outputContent = new StringBuilder();

            for (int i = 0; i < numbers.length; i++) {
                String numStr = numbers[i].trim();
                int number = 0;
                try {
                    number = Integer.parseInt(numStr);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number input: " + numStr);
                    outputContent.append("0").append(delimiter);
                    continue;
                }

                Usercompute.ComputeRequest request = Usercompute.ComputeRequest.newBuilder()
                        .setNumber(number)
                        .setDelimiter(delimiter)
                        .build();

                Usercompute.ComputeResponse response = stub.computeSumOfPrimes(request);

                if (response.getSuccess()) {
                    System.out.println("Number: " + number + " -> Sum of primes: " + response.getSum());
                    outputContent.append(response.getSum()).append(delimiter);
                } else {
                    System.out.println("Computation failed for number: " + number);
                    outputContent.append("0").append(delimiter);
                }
            }

//          Remove last delimiter
            if (outputContent.length() > 0) {
                outputContent.setLength(outputContent.length() - delimiter.length());
            }

//          Write output file
            try {
                FileWriter writer = new FileWriter(outputPath);
                writer.write(outputContent.toString());
                writer.close();
            } catch (IOException e) {
                System.out.println("Error writing output file: " + e.getMessage());
            }

            System.out.println("File processed successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            channel.shutdown();
            scanner.close();
        }
    }
}

