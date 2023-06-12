package ebs.subscriptions;

import ebs.common.Pair;
import ebs.common.Utils;
import ebs.publications.Publication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionManager implements Pushable{

    private final ServerSocket subscriptionSpoutServer;
    private final ServerSocket terminalBoltServer;
    private ObjectOutputStream oos;
    private ObjectInputStream terminalBoltOis;
    private Map<String, Receivable> clientsMap;

    public SubscriptionManager() throws IOException {

        this.subscriptionSpoutServer = new ServerSocket(Utils.SUBSCRIPTIONS_SPOUT_PORT);
        this.terminalBoltServer = new ServerSocket(Utils.TERMINAL_BOLT_PORT);
        this.clientsMap = new HashMap<>();

        handleReceivedPublications();
    }

    @SuppressWarnings("unchecked")
    private void handleReceivedPublications() {

        new Thread(()-> {
            try {
                Socket socket = terminalBoltServer.accept();
                terminalBoltOis = new ObjectInputStream(socket.getInputStream());
                System.out.println("Terminal bolt connected");

                while(true) {

                    Pair<String, Publication> publicationPair = (Pair<String, Publication>) terminalBoltOis.readObject();
                    Receivable client = clientsMap.get(publicationPair.first);

                    client.pushed(publicationPair.second);
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }).start();
    }

    public void startServer() throws IOException {
        Socket socket = subscriptionSpoutServer.accept();

        System.out.println("Subscriber spout connected");

        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    public void addClients(Receivable ... clients) {

        for (Receivable client: clients) {
            clientsMap.put(client.getClientId(), client);
        }
    }


    @Override
    public synchronized void pushed(Subscription subscription, String clientId) {

        try {

            oos.writeObject(new Pair<>(clientId, subscription));
            oos.flush();

//            System.out.println("Client " + clientId + " send subscription");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
