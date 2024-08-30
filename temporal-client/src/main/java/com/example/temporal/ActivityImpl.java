package com.example.temporal;

import io.temporal.activity.ActivityOptions;
import java.time.Duration;

public class ActivityImpl implements Activity {

    private final ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofMinutes(1)) // Set your desired timeout
            .build();

    @Override
    public void placeOrder(int orderId) {
        // Implementation code
        System.out.println("Placing order with ID: " + orderId);
    }

    @Override
    public void setOrderAccepted(int orderId) {
        // Implementation code
        System.out.println("Order with ID " + orderId + " has been accepted.");
    }

    @Override
    public void setOrderPickedUp(int orderId) {
        // Implementation code
        System.out.println("Order with ID " + orderId + " has been picked up.");
    }

    @Override
    public void setOrderDelivered(int orderId) {
        // Implementation code
        System.out.println("Order with ID " + orderId + " has been delivered.");
    }
}
