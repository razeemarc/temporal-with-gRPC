package com.example;

import com.example.temporal.WorkFlow;
import io.grpc.stub.StreamObserver;
import com.example.SetOrderPickedUpServiceGrpc.SetOrderPickedUpServiceImplBase;

import io.temporal.client.WorkflowClient;

public class SetOrderPickedUpServiceImpl extends SetOrderPickedUpServiceImplBase {

    private final WorkflowClient workflowClient;

    public SetOrderPickedUpServiceImpl(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @Override
    public void setOrderPickedUp(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String workflowId = "Order_" + request.getOrderId();
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, workflowId);
        workflow.signalOrderPickedUp();

        OrderResponse response = OrderResponse.newBuilder()
                .setStatus("Order Picked Up Successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
