package com.example.temporal;

public class ActivityImpl implements Activity {

    @Override
    public void placeOrder() {
        System.out.println("***** Order has been placed");
        // Perform any necessary operations for placing the order
    }

    @Override
    public void setOrderAccepted() {
        System.out.println("***** Restaurant has accepted your order");
        // Perform any necessary operations when the order is accepted
    }

    @Override
    public void setOrderPickedUp() {
        System.out.println("***** Order has been picked up");
        // Perform any necessary operations when the order is picked up
    }

    @Override
    public void setOrderDelivered() {
        System.out.println("***** Order Delivered");
        // Perform any necessary operations when the order is delivered
    }
}
