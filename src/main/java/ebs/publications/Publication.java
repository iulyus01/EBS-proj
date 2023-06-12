package ebs.publications;

import java.io.Serializable;
import java.util.*;

public class Publication implements Serializable {

    private final Map<String, Object> publicationData;
    private final Set<UUID> matchingSubscriptionsCity;
    private final Set<UUID> matchingSubscriptionsDirection;
    private final Set<UUID> matchingSubscriptionsStationId;
    private final Set<UUID> matchingSubscriptionsTemperature;
    private final Set<UUID> matchingSubscriptionsWind;

    public Publication(int stationId, String city, int temperature, int wind, String direction) {

        publicationData = new HashMap<>();
        matchingSubscriptionsCity = new HashSet<>();
        matchingSubscriptionsDirection = new HashSet<>();
        matchingSubscriptionsStationId = new HashSet<>();
        matchingSubscriptionsTemperature = new HashSet<>();
        matchingSubscriptionsWind = new HashSet<>();

        publicationData.put("stationId", stationId);
        publicationData.put("city", city);
        publicationData.put("temperature", temperature);
        publicationData.put("wind", wind);
        publicationData.put("direction", direction);
    }

    public void addMatchingSubscriptionCity(UUID subscriptionUUID) {

        matchingSubscriptionsCity.add(subscriptionUUID);
    }

    public void addMatchingSubscriptionDirection(UUID subscriptionUUID) {

        matchingSubscriptionsDirection.add(subscriptionUUID);
    }


    public void addMatchingSubscriptionStationId(UUID subscriptionUUID) {

        matchingSubscriptionsStationId.add(subscriptionUUID);
    }

    public void addMatchingSubscriptionTemp(UUID subscriptionUUID) {

        matchingSubscriptionsTemperature.add(subscriptionUUID);
    }


    public void addMatchingSubscriptionWind(UUID subscriptionUUID) {

        matchingSubscriptionsWind.add(subscriptionUUID);
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

    public Set<UUID> getMatchingSubscriptionsCity() {
        return matchingSubscriptionsCity;
    }

    public Set<UUID> getMatchingSubscriptionsDirection() {
        return matchingSubscriptionsDirection;
    }

    public Set<UUID> getMatchingSubscriptionsStationId() {
        return matchingSubscriptionsStationId;
    }

    public Set<UUID> getMatchingSubscriptionsTemperature() {
        return matchingSubscriptionsTemperature;
    }

    public Set<UUID> getMatchingSubscriptionsWind() {
        return matchingSubscriptionsWind;
    }

    @Override
    public String toString() {

        return "StationID" +
                " = " + getStationId() + " # \n" +
                "City" +
                " = " + getCity() + " # \n" +
                "Wind" +
                " = " + getWind() + " # \n" +
                "Temperature" +
                " = " + getTemperature() + " # \n" +
                "Direction" +
                " = " + getDirection() + " # \n";
    }
}
