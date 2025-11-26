package usercomputeapi.grpcserver;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import usercomputeapi.UserComputeImpl;
import computeengineapi.ComputeEngineImpl;
import storagecomputeapi.grpcclient.StorageComputeGrpcClient;

public class UserComputeServerMain {
    public static void main(String[] args) throws Exception {
        String storageHost = "localhost";
        int storagePort = 50051;
        int userComputePort = 50052; // port for this gRPC server

        StorageComputeGrpcClient storageClient = new StorageComputeGrpcClient(storageHost, storagePort);
        ComputeEngineImpl engine = new ComputeEngineImpl();
        UserComputeImpl userCompute = new UserComputeImpl(storageClient, engine);

        // Create gRPC server
        UserComputeGrpcServer serverImpl = new UserComputeGrpcServer(userCompute);
        Server server = ServerBuilder.forPort(userComputePort)
                .addService(serverImpl)
                .build()
                .start();

        System.out.println("UserCompute gRPC server started on port " + userComputePort);
        server.awaitTermination();
    }
}
