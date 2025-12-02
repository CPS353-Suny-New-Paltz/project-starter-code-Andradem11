package storagecomputeapi.grpcserver;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import storagecomputeapi.StorageComputeImpl;

public class StorageServerMain {
    public static void main(String[] args) throws Exception {
        // Define the port where the gRPC server will listen
        int port = 50051;

        // Create the storage implementation that will handle requests
        StorageComputeImpl storageImpl = new StorageComputeImpl();

        // Create the gRPC server implementation using the storage API
        StorageComputeGrpcServer serverImpl = new StorageComputeGrpcServer(storageImpl);

        // Build and start the gRPC server
        Server server = ServerBuilder.forPort(port)
                .addService(serverImpl)
                .build()
                .start();

        System.out.println("Storage gRPC server started on port " + port);

        // Keep the server running and waiting for requests
        server.awaitTermination();
    }
}
