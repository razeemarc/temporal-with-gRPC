package com.example.temporal;

import io.temporal.workflow.Workflow;
import io.temporal.activity.ActivityOptions;
import java.time.Duration;

public class WorkflowImpl implements WorkFlow {

    private final Activity activities = Workflow.newActivityStub(Activity.class, ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofMinutes(1)) // Set your desired timeout
            .build());

    @Override
    public void executeWorkflow() {
        // Implementation code
        int orderId = 6; // Example order ID
        activities.placeOrder(orderId);
        activities.setOrderAccepted(orderId);
        activities.setOrderPickedUp(orderId);
        activities.setOrderDelivered(orderId);
    }

    public void updateOrderStatus(String status) {
        // Implementation code
        // Example: Convert status to corresponding activity call
        int orderId = 11; // Example order ID
        switch (status) {
            case "Accepted":
                activities.setOrderAccepted(orderId);
                break;
            case "PickedUp":
                activities.setOrderPickedUp(orderId);
                break;
            case "Delivered":
                activities.setOrderDelivered(orderId);
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
