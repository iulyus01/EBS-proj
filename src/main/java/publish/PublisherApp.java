package publish;

import java.io.IOException;

public class PublisherApp {

    public static void main(String[] args) {

        try {

            Publisher publisher = new Publisher();
            publisher.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
