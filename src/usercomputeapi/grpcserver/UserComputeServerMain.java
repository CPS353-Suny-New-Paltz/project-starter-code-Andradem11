package usercomputeapi.grpcserver;

import io.grpc.Server;

import io.grpc.ServerBuilder;
import usercomputeapi.UserComputeImpl;
import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineFastImpl;
import storagecomputeapi.grpcclient.StorageComputeGrpcClient;

public class UserComputeServerMain {
    public static void main(String[] args) throws Exception {
        // storage gRPC host and port
        String storageHost = "localhost";
        int storagePort = 50051;

        // port for this gRPC server
        int userComputePort = 50052;

        // connect to storage gRPC client
        StorageComputeGrpcClient storageClient = new StorageComputeGrpcClient(storageHost, storagePort);

        // create compute engine
        ComputeEngineAPI engine = new ComputeEngineFastImpl();

        // create user compute implementation
        UserComputeImpl userCompute = new UserComputeImpl(storageClient, engine);

        // create gRPC server
        UserComputeGrpcServer serverImpl = new UserComputeGrpcServer(userCompute);
        Server server = ServerBuilder.forPort(userComputePort)
                .addService(serverImpl)
                .build()
                .start();

        // server started
        System.out.println("UserCompute gRPC server started on port " + userComputePort);

        // wait for shutdown
        server.awaitTermination();
    }
}
