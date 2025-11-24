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

        int number = request.getNumber();
        String delimiter;
        if (request.hasDelimiter()) {
            delimiter = request.getDelimiter();
        } else {
            delimiter = ";";
        }

        DataSource source = new DataSource() {
            @Override
            public int getLimit() {
                return number;
            }
        };

        ComputeRequest computeRequest = new ComputeRequest(source, delimiter);
        ComputeResponse response = userCompute.computeSumOfPrimes(computeRequest);

        String message;
        if (response.isSuccess()) {
            message = "Success";
        } else {
            message = "Fail";
        }

        Usercompute.ComputeResponse grpcResponse = Usercompute.ComputeResponse.newBuilder()
                .setSum(response.getSum())
                .setSuccess(response.isSuccess())
                .setMessage(message)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void processFile(Usercompute.ProcessFileRequest request,
                            StreamObserver<Usercompute.ProcessFileResponse> responseObserver) {

        if (request == null) {
            Usercompute.ProcessFileResponse errorResponse = Usercompute.ProcessFileResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Request is null")
                    .build();
            responseObserver.onNext(errorResponse);
            responseObserver.onCompleted();
            return;
        }

        String inputPath = request.getInputPath();
        String outputPath = request.getOutputPath();
        String delimiter;
        if (request.hasDelimiter()) {
            delimiter = request.getDelimiter();
        } else {
            delimiter = ";";
        }

        Usercompute.ProcessFileResponse grpcResponse;

        try {
            userCompute.processFile(inputPath, outputPath);
            grpcResponse = Usercompute.ProcessFileResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("File processed successfully")
                    .build();
        } catch (Exception e) {
            grpcResponse = Usercompute.ProcessFileResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error: " + e.getMessage())
                    .build();
        }

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}


