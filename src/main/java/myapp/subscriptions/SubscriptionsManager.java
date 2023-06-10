package myapp.subscriptions;

import myapp.Utils;
import myapp.publications.PublicationGenerator;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SubscriptionsManager {

    private final ServerSocket server;

    public SubscriptionsManager() throws IOException {
        this.server = new ServerSocket(Utils.SUBSCRIPTIONS_MANAGER_PORT);
    }

    public void startServer() throws IOException {
        Socket socket = server.accept();
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        System.out.println("Subscriber server connected");

        while(true) {
            try {
                Subscription s = generateSubscription();
                oos.writeObject(s);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                break;
            }
        }

        socket.close();
        oos.close();
        server.close();
    }

    private Subscription generateSubscription() {
        List<Utils.CheckOperators> intOperators = Arrays.asList(
            Utils.CheckOperators.EQUAL,
            Utils.CheckOperators.NOT_EQUAL,
            Utils.CheckOperators.GREATER,
            Utils.CheckOperators.LOWER,
            Utils.CheckOperators.GREATER_EQUAL,
            Utils.CheckOperators.LOWER_EQUAL
        );
        List<Utils.CheckOperators> stringOperators = Arrays.asList(
            Utils.CheckOperators.EQUAL,
            Utils.CheckOperators.NOT_EQUAL
        );
        List<String> properties = Arrays.asList("stationId", "city", "temp", "wind", "direction");
        Collections.shuffle(properties);
        int noOfProperties = (int) Math.floor(Math.random() * Utils.NO_OF_PROPERTIES + 1);
        Subscription subscription = new Subscription();
        System.out.println("no:" + noOfProperties);
        for(int i = 0; i < noOfProperties; i++) {
            String prop = properties.get(i);
            if(prop.compareTo("stationId") == 0) {
                int value = Utils.getRand(Utils.MIN_STATION_ID_VALUE, Utils.MAX_STATION_ID_VALUE);
                subscription.setStationIdVal(intOperators.get((int) (Math.random() * intOperators.size())), value);
                continue;
            }
            if(prop.compareTo("city") == 0) {
                String value = Utils.getRand(Utils.CITY_VALUES);
                subscription.setCityVal(stringOperators.get((int) (Math.random() * stringOperators.size())), value);
                continue;
            }
            if(prop.compareTo("temp") == 0) {
                int value = Utils.getRand(Utils.MIN_TEMP_VALUE, Utils.MAX_TEMP_VALUE);
                subscription.setTempVal(intOperators.get((int) (Math.random() * intOperators.size())), value);
                continue;
            }
            if(prop.compareTo("wind") == 0) {
                int value = Utils.getRand(Utils.MIN_WIND_VALUE, Utils.MAX_WIND_VALUE);
                subscription.setWindVal(intOperators.get((int) (Math.random() * intOperators.size())), value);
                continue;
            }
            if(prop.compareTo("direction") == 0) {
                String value = Utils.getRand(Utils.DIRECTION_VALUES);
                subscription.setDirectionVal(stringOperators.get((int) (Math.random() * stringOperators.size())), value);
            }
        }

        return subscription;
    }
}
