package com.example;

import com.example.temporal.WorkFlow;
import io.grpc.stub.StreamObserver;
import com.example.PlaceOrderServiceGrpc.PlaceOrderServiceImplBase;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;

public class PlaceOrderServiceImpl extends PlaceOrderServiceImplBase {

    private final WorkflowClient workflowClient;

    public PlaceOrderServiceImpl(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @Override
    public void placeOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String workflowId = "Order_" + request.getOrderId();
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, WorkflowOptions.newBuilder()
                .setTaskQueue(WorkFlow.QUEUE_NAME)
                .setWorkflowId(workflowId)
                .build());

        WorkflowClient.start(workflow::startApprovalWorkflow);

        OrderResponse response = OrderResponse.newBuilder()
                .setStatus("Order Placed Successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
