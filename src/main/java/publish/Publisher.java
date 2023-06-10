package publish;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Publisher {

    private final ServerSocket server;
    private final int port = 8088;

    private final PublicationGenerator generator;


    public Publisher() throws IOException {

        this.server = new ServerSocket(port);
        this.generator = new PublicationGenerator();
    }

    public void startServer() throws IOException {

        Socket socket = server.accept();

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

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
