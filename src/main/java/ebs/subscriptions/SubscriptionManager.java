package ebs.subscriptions;

import ebs.common.Pair;
import ebs.common.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionManager implements Pushable{

    private final ServerSocket server;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Map<String, Receivable> clientsMap;

    public SubscriptionManager() throws IOException {

        this.server = new ServerSocket(Utils.SUBSCRIPTIONS_MANAGER_PORT);
        this.clientsMap = new HashMap<>();
    }

    public void startServer() throws IOException {
        Socket socket = server.accept();

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void addClients(Receivable ... clients) {

        for (Receivable client: clients) {
            clientsMap.put(client.getClientId(), client);
        }
    }


    @Override
    public void pushed(Subscription subscription, String clientId) {

        try {

            oos.writeObject(new Pair<>(clientId, subscription));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
