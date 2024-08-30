package com.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.PlaceOrderServiceGrpc.PlaceOrderServiceBlockingStub;
import com.example.SetOrderAcceptedServiceGrpc.SetOrderAcceptedServiceBlockingStub;
import com.example.SetOrderPickedUpServiceGrpc.SetOrderPickedUpServiceBlockingStub;
import com.example.SetOrderDeliveredServiceGrpc.SetOrderDeliveredServiceBlockingStub;


public class GrpcClient {

    private final ManagedChannel channel;
    private final PlaceOrderServiceBlockingStub placeOrderStub;
    private final SetOrderAcceptedServiceBlockingStub orderAcceptedStub;
    private final SetOrderPickedUpServiceBlockingStub orderPickedUpStub;
    private final SetOrderDeliveredServiceBlockingStub orderDeliveredStub;

    public GrpcClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.placeOrderStub = PlaceOrderServiceGrpc.newBlockingStub(channel);
        this.orderAcceptedStub = SetOrderAcceptedServiceGrpc.newBlockingStub(channel);
        this.orderPickedUpStub = SetOrderPickedUpServiceGrpc.newBlockingStub(channel);
        this.orderDeliveredStub = SetOrderDeliveredServiceGrpc.newBlockingStub(channel);
    }

    public OrderResponse placeOrder(int orderId) {
        OrderRequest request = OrderRequest.newBuilder().setOrderId(String.valueOf(orderId)).build();
        return placeOrderStub.placeOrder(request);
    }

    public OrderResponse setOrderAccepted(int orderId) {
        OrderRequest request = OrderRequest.newBuilder().setOrderId(String.valueOf(orderId)).build();
        return orderAcceptedStub.setOrderAccepted(request);
    }

    public OrderResponse setOrderPickedUp(int orderId) {
        OrderRequest request = OrderRequest.newBuilder().setOrderId(String.valueOf(orderId)).build();
        return orderPickedUpStub.setOrderPickedUp(request);
    }

    public OrderResponse setOrderDelivered(int orderId) {
        OrderRequest request = OrderRequest.newBuilder().setOrderId(String.valueOf(orderId)).build();
        return orderDeliveredStub.setOrderDelivered(request);
    }

    public void shutdown() {
        channel.shutdown();
    }

    public static void main(String[] args) {
        GrpcClient client = new GrpcClient("localhost", 9090);

        int orderId = 9;

        // Place Order
        OrderResponse placeOrderResponse = client.placeOrder(orderId);
        System.out.println("Place Order Response: " + placeOrderResponse.getStatus());

        // Set Order Accepted
        OrderResponse acceptedResponse = client.setOrderAccepted(orderId);
        System.out.println("Set Order Accepted Response: " + acceptedResponse.getStatus());

        // Set Order Picked Up
        OrderResponse pickedUpResponse = client.setOrderPickedUp(orderId);
        System.out.println("Set Order Picked Up Response: " + pickedUpResponse.getStatus());

        // Set Order Delivered
        OrderResponse deliveredResponse = client.setOrderDelivered(orderId);
        System.out.println("Set Order Delivered Response: " + deliveredResponse.getStatus());

        client.shutdown();
    }
}
