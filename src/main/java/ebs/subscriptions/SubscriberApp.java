package ebs.subscriptions;

import java.io.IOException;

public class SubscriberApp {

    public static void main(String[] args) {

        System.out.println("Subscriber Application started");

        try {
            SubscriberClient subscriberClient1 = new SubscriberClient("Alex", false, 25);
            SubscriberClient subscriberClient2 = new SubscriberClient("Andrei", false, 40);
            SubscriberClient subscriberClient3 = new SubscriberClient("Denis", false, 35);

            SubscriptionManager subscriptionManager = new SubscriptionManager();
            subscriptionManager.addClients(subscriberClient1, subscriberClient2, subscriberClient3);

            subscriberClient1.setSubscriptionManager(subscriptionManager);
            subscriberClient2.setSubscriptionManager(subscriptionManager);
            subscriberClient3.setSubscriptionManager(subscriptionManager);

            subscriptionManager.startServer();

            subscriberClient1.start();
            subscriberClient2.start();
            subscriberClient3.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
