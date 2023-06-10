package myapp.subscriptions;

import java.io.IOException;

public class SubscriberApp {

    public static void main(String[] args) {
        System.out.println("Subscriber started");
        try {
            SubscriptionsManager subscriptionsManager = new SubscriptionsManager();
            subscriptionsManager.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
