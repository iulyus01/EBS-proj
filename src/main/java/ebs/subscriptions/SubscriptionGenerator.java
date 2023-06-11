package ebs.subscriptions;

import ebs.common.Utils;
import ebs.common.Operator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubscriptionGenerator {

    private final List<Operator> simpleIntOperators;
    private final List<Operator> allIntOperators;
    private final List<Operator> stringOperators;
    private final boolean onlySimpleSubscriptions;

    public SubscriptionGenerator(boolean onlySimpleSubscriptions) {

        this.onlySimpleSubscriptions = onlySimpleSubscriptions;

        simpleIntOperators = Arrays.asList(Operator.EQUAL, Operator.NOT_EQUAL, Operator.GREATER, Operator.LOWER,
                Operator.GREATER_EQUAL, Operator.LOWER_EQUAL);

        allIntOperators = Arrays.asList(Operator.EQUAL_AVERAGE, Operator.GREATER_AVERAGE, Operator.GREATER_EQUAL_AVERAGE,
                Operator.NOT_EQUAL_AVERAGE, Operator.LOWER_EQUAL_AVERAGE, Operator.LOWER_AVERAGE);
        allIntOperators.addAll(simpleIntOperators);

        stringOperators = Arrays.asList(Operator.EQUAL, Operator.NOT_EQUAL);
    }

    public Subscription generateSubscription() {

        List<String> properties = Arrays.asList("stationId", "city", "temp", "wind", "direction");
        Collections.shuffle(properties);

        int noOfProperties = (int) Math.floor(Math.random() * Utils.NO_OF_PROPERTIES + 1);
        Subscription subscription = new Subscription();

        for(int i = 0; i < noOfProperties; i++) {

            fillSubscription(subscription, properties.get(i));
        }

        return subscription;
    }

    private void fillSubscription(Subscription subscription, String property) {

        if(property.compareTo("stationId") == 0) {

            int value = Utils.getRand(Utils.MIN_STATION_ID_VALUE, Utils.MAX_STATION_ID_VALUE);
            subscription.setStationIdVal(simpleIntOperators.get((int) (Math.random() * simpleIntOperators.size())), value);
        }
        else if(property.compareTo("city") == 0) {

            String value = Utils.getRand(Utils.CITY_VALUES);
            subscription.setCityVal(stringOperators.get((int) (Math.random() * stringOperators.size())), value);
        }
        else if(property.compareTo("temp") == 0) {

            int value = Utils.getRand(Utils.MIN_TEMP_VALUE, Utils.MAX_TEMP_VALUE);
            Operator operator = onlySimpleSubscriptions ? simpleIntOperators.get((int) (Math.random() * simpleIntOperators.size())) :
                    allIntOperators.get((int) (Math.random() * allIntOperators.size()));
            subscription.setTempVal(operator, value);
        }
        else if(property.compareTo("wind") == 0) {

            int value = Utils.getRand(Utils.MIN_WIND_VALUE, Utils.MAX_WIND_VALUE);
            Operator operator = onlySimpleSubscriptions ? simpleIntOperators.get((int) (Math.random() * simpleIntOperators.size())) :
                    allIntOperators.get((int) (Math.random() * allIntOperators.size()));
            subscription.setWindVal(operator, value);
        }
        else if(property.compareTo("direction") == 0) {

            String value = Utils.getRand(Utils.DIRECTION_VALUES);
            subscription.setDirectionVal(stringOperators.get((int) (Math.random() * stringOperators.size())), value);
        }
    }
}
