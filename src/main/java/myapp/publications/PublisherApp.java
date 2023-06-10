package myapp.publications;

import java.io.IOException;

public class PublisherApp {

    public static void main(String[] args) {
        System.out.println("Publisher started");
        try {
            Publisher publisher = new Publisher();
            publisher.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
