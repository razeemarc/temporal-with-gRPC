package com.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

public class GrpcServer {

    public static void main(String[] args) throws Exception {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(WorkflowServiceStubsOptions.newBuilder()
                .setTarget("127.0.0.1:7233")
                .build());
        WorkflowClient client = WorkflowClient.newInstance(service);

        Server server = ServerBuilder.forPort(9090)
                .addService(new PlaceOrderServiceImpl(client))
                .addService(new SetOrderAcceptedServiceImpl(client))
                .addService(new SetOrderPickedUpServiceImpl(client))
                .addService(new SetOrderDeliveredServiceImpl(client))
                .build();

        server.start();
        System.out.println("Server started on port 9090");
        server.awaitTermination();
    }
}
