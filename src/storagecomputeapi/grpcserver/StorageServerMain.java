package storagecomputeapi.grpcserver;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import storagecomputeapi.StorageComputeImpl;

public class StorageServerMain {
    public static void main(String[] args) throws Exception {
        int port = 50051; 
        StorageComputeImpl storageImpl = new StorageComputeImpl();
        StorageComputeGrpcServer serverImpl = new StorageComputeGrpcServer(storageImpl);

        Server server = ServerBuilder.forPort(port)
                .addService(serverImpl)
                .build()
                .start();

        System.out.println("Storage gRPC server started on port " + port);
        server.awaitTermination();
    }
}

