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

        List<Integer> data = null;
        if (request == null) {
            data = new ArrayList<Integer>();
        } else {
            String inputPath = request.getInputPath();
            data = storage.readInput(inputPath);
            if (data == null) {
                data = new ArrayList<Integer>();
            }
        }

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

        StorageResponse storageResponse = null;

        if (request == null) {
            storageResponse = new StorageResponse(StorageResponse.Status.FAIL, "Request is null");
        } else {
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 0; i < request.getNumbersCount(); i++) {
                numbers.add(request.getNumbers(i));
            }
            String outputPath = request.getOutputPath();
            storageResponse = storage.writeOutput(numbers, outputPath);
        }

        Storagecompute.WriteOutputResponse.Builder responseBuilder = Storagecompute.WriteOutputResponse.newBuilder();

        if (storageResponse.getStatus() == StorageResponse.Status.SUCCESS) {
            responseBuilder.setStatus(Storagecompute.WriteOutputResponse.Status.SUCCESS);
        } else {
            responseBuilder.setStatus(Storagecompute.WriteOutputResponse.Status.FAIL);
        }

        if (storageResponse.getMessage() != null) {
            responseBuilder.setMessage(storageResponse.getMessage());
        } else {
            responseBuilder.setMessage("");
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
