package myapp.subscriptions;

import myapp.Utils;

import java.io.Serializable;

public class Subscription implements Serializable {

    private Utils.Pair<Utils.CheckOperators, Integer> stationIdVal;
    private Utils.Pair<Utils.CheckOperators, String> cityVal;
    private Utils.Pair<Utils.CheckOperators, Integer> tempVal;
    private Utils.Pair<Utils.CheckOperators, Integer> windVal;
    private Utils.Pair<Utils.CheckOperators, String> directionVal;

    public Subscription() {
    }

    public void setStationIdVal(Utils.CheckOperators operator, int value) {
        this.stationIdVal = new Utils.Pair<>(operator, value);
    }

    public void setCityVal(Utils.CheckOperators operator, String value) {
        this.cityVal = new Utils.Pair<>(operator, value);
    }

    public void setTempVal(Utils.CheckOperators operator, Integer value) {
        this.tempVal = new Utils.Pair<>(operator, value);
    }

    public void setWindVal(Utils.CheckOperators operator, Integer value) {
        this.windVal = new Utils.Pair<>(operator, value);
    }

    public void setDirectionVal(Utils.CheckOperators operator, String value) {
        this.directionVal = new Utils.Pair<>(operator, value);
    }

    public Utils.Pair<Utils.CheckOperators, Integer> getStationIdVal() {
        return stationIdVal;
    }

    public Utils.Pair<Utils.CheckOperators, String> getCityVal() {
        return cityVal;
    }

    public Utils.Pair<Utils.CheckOperators, Integer> getTempVal() {
        return tempVal;
    }

    public Utils.Pair<Utils.CheckOperators, Integer> getWindVal() {
        return windVal;
    }

    public Utils.Pair<Utils.CheckOperators, String> getDirectionVal() {
        return directionVal;
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
