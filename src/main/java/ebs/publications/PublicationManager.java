package ebs.publications;

import ebs.common.Utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PublicationManager {

    private final ServerSocket server;
    private final PublicationGenerator generator;

    public PublicationManager() throws IOException {
        this.server = new ServerSocket(Utils.PUBLISHER_PORT);
        this.generator = new PublicationGenerator();
    }

    public void startServer() throws IOException {
        Socket socket = server.accept();
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        System.out.println("Publisher server connected");

        while(true) {
            try {
                oos.writeObject(generator.generatePublication());
            } catch (IOException ioException) {
                ioException.printStackTrace();
                break;
            }
        }

        socket.close();
        oos.close();
        server.close();
    }
}