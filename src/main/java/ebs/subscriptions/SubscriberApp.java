package ebs.subscriptions;

import java.io.IOException;

public class SubscriberApp {

    public static void main(String[] args) {

        System.out.println("Subscriber Application started");

        try {
            SubscriberClient subscriberClient1 = new SubscriberClient("Alex", true, 2500);
            SubscriberClient subscriberClient2 = new SubscriberClient("Andrei", true, 4000);
            SubscriberClient subscriberClient3 = new SubscriberClient("Denis", false, 3500);

            SubscriptionManager subscriptionManager = new SubscriptionManager();
            subscriptionManager.addClients(subscriberClient1, subscriberClient2, subscriberClient3);

            subscriptionManager.startServer();

            subscriberClient1.start();
            subscriberClient2.start();
            subscriberClient3.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
