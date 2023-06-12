import ebs.TopologyApp;
import ebs.publications.PublisherApp;
import ebs.subscriptions.SubscriberApp;

public class App {

    public static void main(String[] args) {

        new Thread(() -> {PublisherApp.main(null);}).start();
        new Thread(() -> {SubscriberApp.main(null);}).start();
        new Thread(() -> {
            try {
                TopologyApp.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
