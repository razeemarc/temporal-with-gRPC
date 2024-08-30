package com.example;

import com.example.temporal.WorkFlow;
import io.grpc.stub.StreamObserver;
import com.example.SetOrderDeliveredServiceGrpc.SetOrderDeliveredServiceImplBase;

import io.temporal.client.WorkflowClient;

public class SetOrderDeliveredServiceImpl extends SetOrderDeliveredServiceImplBase {

    private final WorkflowClient workflowClient;

    public SetOrderDeliveredServiceImpl(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @Override
    public void setOrderDelivered(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String workflowId = "Order_" + request.getOrderId();
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, workflowId);
        workflow.signalOrderDelivered();

        OrderResponse response = OrderResponse.newBuilder()
                .setStatus("Order Delivered Successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
