package publish;

import java.io.Serializable;
import java.util.*;

public class Publication implements Serializable {

    private final Map<String, Object> publicationData;
    private final Set<UUID> matchingSubscriptions;

    public Publication(int stationId, String city, int temperature, int wind, String direction) {

        publicationData = new HashMap<>();
        matchingSubscriptions = new HashSet<>();

        publicationData.put("stationId", stationId);
        publicationData.put("city", city);
        publicationData.put("temperature", temperature);
        publicationData.put("wind", wind);
        publicationData.put("direction", direction);
    }

    public void addMatchingSubscription(UUID subscriptionUUID) {

        matchingSubscriptions.add(subscriptionUUID);
    }

    public void removeMatchingSubscription(UUID subscriptionUUID) {

        matchingSubscriptions.remove(subscriptionUUID);
    }

    public int getStationId() {
        return (Integer) publicationData.get("stationId");
    }

    public String getCity() {
        return (String) publicationData.get("city");
    }

    public int getTemperature() {
        return (Integer) publicationData.get("temperature");
    }

    public int getWind() {
        return (Integer) publicationData.get("wind");
    }

    public String getDirection() {
        return (String) publicationData.get("direction");
    }

    public Set<UUID> getMatchingSubscriptions() {
        return matchingSubscriptions;
    }
}
