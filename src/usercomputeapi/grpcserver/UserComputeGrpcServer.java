package usercomputeapi.grpcserver;

import io.grpc.stub.StreamObserver;
import usercompute.UserComputeServiceGrpc;
import usercompute.Usercompute;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.DataSource;
import usercomputeapi.UserComputeAPI;

public class UserComputeGrpcServer extends UserComputeServiceGrpc.UserComputeServiceImplBase {

    private final UserComputeAPI userCompute;

    public UserComputeGrpcServer(UserComputeAPI userCompute) {
        if (userCompute == null) {
            throw new IllegalArgumentException("UserComputeAPI cannot be null");
        }
        this.userCompute = userCompute;
    }

    @Override
    public void computeSumOfPrimes(Usercompute.ComputeRequest request,
                                   StreamObserver<Usercompute.ComputeResponse> responseObserver) {

        // if client sends nothing, respond with an error
        if (request == null) {
            Usercompute.ComputeResponse errorResponse = Usercompute.ComputeResponse.newBuilder()
                    .setSum(0)
                    .setSuccess(false)
                    .setMessage("Request is null")
                    .build();
            responseObserver.onNext(errorResponse);
            responseObserver.onCompleted();
            return;
        }

        // limit for the prime sum
        int number = request.getNumber();

        // use delimiter from client if provided, otherwise just default
        String delimiter = request.hasDelimiter() ? request.getDelimiter() : ";";

        // wraps the limit so our internal API can use it
        DataSource source = () -> number;

        // request for our internal compute API
        ComputeRequest computeRequest = new ComputeRequest(source, delimiter);

        // gets the result from user compute logic
        ComputeResponse response = userCompute.computeSumOfPrimes(computeRequest);

        // simple message for client based on result
        String message = response.isSuccess() ? "Success" : "Fail";

        // builds grpc response to send back
        Usercompute.ComputeResponse grpcResponse = Usercompute.ComputeResponse.newBuilder()
                .setSum(response.getSum())
                .setSuccess(response.isSuccess())
                .setMessage(message)
                .build();

        // send and close
        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void processFile(Usercompute.ProcessFileRequest request,
                            StreamObserver<Usercompute.ProcessFileResponse> responseObserver) {

        // same idea — if client sends nothing, fail early
        if (request == null) {
            Usercompute.ProcessFileResponse errorResponse = Usercompute.ProcessFileResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Request is null")
                    .build();
            responseObserver.onNext(errorResponse);
            responseObserver.onCompleted();
            return;
        }

        // paths for input and output
        String inputPath = request.getInputPath();
        String outputPath = request.getOutputPath();

        // use client delimiter if provided, else just default
        String delimiter = request.hasDelimiter() ? request.getDelimiter() : ";";

        Usercompute.ProcessFileResponse grpcResponse;

        try {
            // call actual file logic
            userCompute.processFile(inputPath, outputPath);

            // success message for the client
            grpcResponse = Usercompute.ProcessFileResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("File processed successfully")
                    .build();

        } catch (Exception e) {
            // if file fails, return a clean error message
            grpcResponse = Usercompute.ProcessFileResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error: " + e.getMessage())
                    .build();
        }

        // send and close
        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}

