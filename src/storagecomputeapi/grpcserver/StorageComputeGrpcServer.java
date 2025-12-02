package storagecomputeapi.grpcserver;

import java.util.ArrayList;
import java.util.List;

import io.grpc.stub.StreamObserver;
import storagecompute.StorageComputeServiceGrpc;
import storagecompute.Storagecompute;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageResponse;

public class StorageComputeGrpcServer extends StorageComputeServiceGrpc.StorageComputeServiceImplBase {

    private final StorageComputeAPI storage;

    public StorageComputeGrpcServer(StorageComputeAPI storage) {
        if (storage == null) {
            throw new IllegalArgumentException("StorageComputeAPI cannot be null");
        }
        this.storage = storage;
    }

    @Override
    public void readInput(Storagecompute.ReadInputRequest request,
                          StreamObserver<Storagecompute.ReadInputResponse> responseObserver) {

        // If request is null, return an empty list; otherwise, read data from storage
        List<Integer> data = (request == null) ? new ArrayList<>() : storage.readInput(request.getInputPath());
        if (data == null) {
            data = new ArrayList<>();
        }

        // Build the response by adding all numbers from the data list
        Storagecompute.ReadInputResponse.Builder responseBuilder = Storagecompute.ReadInputResponse.newBuilder();
        for (Integer number : data) {
            responseBuilder.addNumbers(number);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void writeOutput(Storagecompute.WriteOutputRequest request,
                            StreamObserver<Storagecompute.WriteOutputResponse> responseObserver) {

        // If request is null, create a failure response; otherwise, process numbers and write output
        StorageResponse storageResponse = (request == null)
                ? new StorageResponse(StorageResponse.Status.FAIL, "Request is null")
                : storage.writeOutput(
                        new ArrayList<Integer>() {{
                            for (int i = 0; i < request.getNumbersCount(); i++) add(request.getNumbers(i));
                        }},
                        request.getOutputPath()
                  );

        Storagecompute.WriteOutputResponse.Builder responseBuilder = Storagecompute.WriteOutputResponse.newBuilder();

        // Set the response status based on the result from storage
        responseBuilder.setStatus(
                (storageResponse.getStatus() == StorageResponse.Status.SUCCESS)
                        ? Storagecompute.WriteOutputResponse.Status.SUCCESS
                        : Storagecompute.WriteOutputResponse.Status.FAIL
        );

        // Ensure the message is never null by providing a default empty string if needed
        responseBuilder.setMessage(
                (storageResponse.getMessage() != null) ? storageResponse.getMessage() : ""
        );

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
