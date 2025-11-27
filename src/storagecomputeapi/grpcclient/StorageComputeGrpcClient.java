package storagecomputeapi.grpcclient;

import java.util.ArrayList;
import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import storagecompute.StorageComputeServiceGrpc;
import storagecompute.Storagecompute;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageResponse;

public class StorageComputeGrpcClient implements StorageComputeAPI {

    private final StorageComputeServiceGrpc.StorageComputeServiceBlockingStub blockingStub;

    public StorageComputeGrpcClient(String host, int port) {
        if (host == null || host.isEmpty()) {
            throw new IllegalArgumentException("Host cannot be null or empty");
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = StorageComputeServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public List<Integer> readInput(String inputPath) {
        if (inputPath == null || inputPath.trim().isEmpty()) {
            return new ArrayList<>();
        }

        Storagecompute.ReadInputRequest request = Storagecompute.ReadInputRequest.newBuilder()
                .setInputPath(inputPath)
                .build();

        Storagecompute.ReadInputResponse response = blockingStub.readInput(request);

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < response.getNumbersCount(); i++) {
            numbers.add(response.getNumbers(i));
        }

        return numbers;
    }

    @Override
    public StorageResponse writeOutput(List<Integer> numbers, String outputPath) {
        if (numbers == null || numbers.isEmpty() || outputPath == null || outputPath.trim().isEmpty()) {
            return new StorageResponse(StorageResponse.Status.FAIL, "Invalid numbers or output path");
        }

        Storagecompute.WriteOutputRequest.Builder requestBuilder = Storagecompute.WriteOutputRequest.newBuilder();
        requestBuilder.setOutputPath(outputPath);

        for (int i = 0; i < numbers.size(); i++) {
            requestBuilder.addNumbers(numbers.get(i));
        }

        Storagecompute.WriteOutputRequest request = requestBuilder.build();
        Storagecompute.WriteOutputResponse response = blockingStub.writeOutput(request);

        // map gRPC status to local status
        StorageResponse.Status status = (response.getStatus() == Storagecompute.WriteOutputResponse.Status.SUCCESS)
                ? StorageResponse.Status.SUCCESS
                : StorageResponse.Status.FAIL;

        // make message null-safe
        String message = (response.getMessage() != null) ? response.getMessage() : "";

        return new StorageResponse(status, message);
    }
}
