package ebs.subscriptions;

import ebs.publications.Publication;

import java.util.Arrays;

public class SubscriberClient extends Thread implements Receivable{

    private final String clientId;
    private final SubscriptionGenerator generator;
    private Pushable manager;
    private final int numberOfSubscriptions;

    public SubscriberClient(String clientId, boolean onlySimpleSubscription, int numberOfSubscriptions) {

        this.generator = new SubscriptionGenerator(onlySimpleSubscription);
        this.clientId = clientId;
        this.numberOfSubscriptions = numberOfSubscriptions;
    }

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < numberOfSubscriptions; i++) {

//            System.out.println("Client sending subscription");
            manager.pushed(generator.generateSubscription(), clientId);
        }
    }

    @Override
    public void pushed(Publication... publications) {

        System.out.println("Client " + clientId + " has received " + publications.length + " publications.");
        Arrays.stream(publications).sequential().forEach(System.out::println);
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setSubscriptionManager(Pushable manager) {

        this.manager = manager;
    }
}
