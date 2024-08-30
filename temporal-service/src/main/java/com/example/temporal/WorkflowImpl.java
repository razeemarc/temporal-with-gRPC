package com.example.temporal;

import java.time.Duration;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

public class WorkflowImpl implements WorkFlow {

    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(50000)
            .build();

    private final ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryOptions)
            .build();

    private final Activity activity = Workflow.newActivityStub(Activity.class, options);

    private boolean isOrderConfirmed = false;
    private boolean isOrderPickedUp = false;
    private boolean isOrderDelivered = false;

    @Override
    public void startApprovalWorkflow() {
        // Place order activity
        activity.placeOrder();
        System.out.println("***** Order has been placed");

        // Wait for the order to be confirmed by the restaurant
        Workflow.await(() -> isOrderConfirmed);
        System.out.println("***** Order has been confirmed by the restaurant");

        // Order accepted activity
        if (isOrderConfirmed) {
            activity.setOrderAccepted();
        }

        // Wait for the order to be picked up by the delivery executive
        Workflow.await(() -> isOrderPickedUp);
        System.out.println("***** Order has been picked up by the delivery executive");

        // Order picked up activity
        if (isOrderPickedUp) {
            activity.setOrderPickedUp();
        }

        // Wait for the order to be delivered to the customer
        Workflow.await(() -> isOrderDelivered);
        System.out.println("***** Order has been delivered");

        // Order delivered activity
        if (isOrderDelivered) {
            activity.setOrderDelivered();
        }

        // Workflow will complete here naturally
        System.out.println("***** Workflow completed");
    }



    @Override
    public void signalOrderAccepted() {
        // Update the order confirmation state
        this.isOrderConfirmed = true;
    }

    @Override
    public void signalOrderPickedUp() {
        // Update the order picked up state
        this.isOrderPickedUp = true;
    }

    @Override
    public void signalOrderDelivered() {
        // Update the order delivered state
        this.isOrderDelivered = true;
    }
}
