package ebs.subscriptions;

public interface Pushable {

    void pushed(Subscription subscription, String clientId);
}
