package com.example;

import com.example.temporal.WorkFlow;
import io.grpc.stub.StreamObserver;
import com.example.SetOrderAcceptedServiceGrpc.SetOrderAcceptedServiceImplBase;

import io.temporal.client.WorkflowClient;

public class SetOrderAcceptedServiceImpl extends SetOrderAcceptedServiceImplBase {

    private final WorkflowClient workflowClient;

    public SetOrderAcceptedServiceImpl(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @Override
    public void setOrderAccepted(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String workflowId = "Order_" + request.getOrderId();
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, workflowId);
        workflow.signalOrderAccepted();

        OrderResponse response = OrderResponse.newBuilder()
                .setStatus("Order Accepted Successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
