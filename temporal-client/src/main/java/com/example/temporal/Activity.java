package com.example.temporal;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activity {


    void placeOrder(int orderId);

    void setOrderAccepted(int orderId);

    void setOrderPickedUp(int orderId);

    void setOrderDelivered(int orderId);
}
