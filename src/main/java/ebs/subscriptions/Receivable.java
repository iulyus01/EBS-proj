package ebs.subscriptions;

import ebs.publications.Publication;

public interface Receivable {

    void pushed(Publication ... publications);
    String getClientId();
}
