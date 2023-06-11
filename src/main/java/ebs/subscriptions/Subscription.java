package ebs.subscriptions;

import ebs.common.Pair;
import ebs.common.Operator;

import java.io.Serializable;
import java.util.UUID;

public class Subscription implements Serializable {

    private final UUID uuid;
    private Pair<Operator, Integer> stationIdVal;
    private Pair<Operator, String> cityVal;
    private Pair<Operator, Integer> tempVal;
    private Pair<Operator, Integer> windVal;
    private Pair<Operator, String> directionVal;

    public Subscription() {

        uuid = UUID.randomUUID();
    }

    public void setStationIdVal(Operator operator, int value) {
        this.stationIdVal = new Pair<>(operator, value);
    }

    public void setCityVal(Operator operator, String value) {
        this.cityVal = new Pair<>(operator, value);
    }

    public void setTempVal(Operator operator, Integer value) {
        this.tempVal = new Pair<>(operator, value);
    }

    public void setWindVal(Operator operator, Integer value) {
        this.windVal = new Pair<>(operator, value);
    }

    public void setDirectionVal(Operator operator, String value) {
        this.directionVal = new Pair<>(operator, value);
    }

    public Pair<Operator, Integer> getStationIdVal() {
        return stationIdVal;
    }

    public Pair<Operator, String> getCityVal() {
        return cityVal;
    }

    public Pair<Operator, Integer> getTempVal() {
        return tempVal;
    }

    public Pair<Operator, Integer> getWindVal() {
        return windVal;
    }

    public Pair<Operator, String> getDirectionVal() {
        return directionVal;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(stationIdVal != null) {
            builder.append("StationID");
            builder.append(" ").append(stationIdVal.first).append(" ");
            builder.append(stationIdVal.second).append(" # ");
        }
        if(cityVal != null) {
            builder.append("City");
            builder.append(" ").append(cityVal.first).append(" ");
            builder.append(cityVal.second).append(" # ");
        }
        if(tempVal != null) {
            builder.append("Temp");
            builder.append(" ").append(tempVal.first).append(" ");
            builder.append(tempVal.second).append(" # ");
        }
        if(windVal != null) {
            builder.append("Wind");
            builder.append(" ").append(windVal.first).append(" ");
            builder.append(windVal.second).append(" # ");
        }
        if(directionVal != null) {
            builder.append("Direction");
            builder.append(" ").append(directionVal.first).append(" ");
            builder.append(directionVal.second).append(" # ");
        }
        builder.append("\n");
        return builder.toString();
    }
}
