package ebs.publications;

import java.io.IOException;

public class PublisherApp {

    public static void main(String[] args) {

        System.out.println("Publisher Application started");

        try {

            PublicationManager publicationManager = new PublicationManager();
            publicationManager.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
