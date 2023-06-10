package myapp;

import myapp.subscriptions.*;

import java.io.Serializable;
import java.util.*;

public class Utils {

    public static final Random random = new Random();
    public static final int NO_OF_PROPERTIES = 5;
    public static final int PUBLISHER_PORT = 8088;
    public static final int SUBSCRIPTIONS_MANAGER_PORT = 8089;

    public static final List<String> CITY_VALUES = Arrays.asList("Bucuresti", "Iasi", "Timisoara", "Constanta", "Bacau", "Suceava", "Braila");
    public static final List<String> DIRECTION_VALUES = Arrays.asList("N", "NE", "E", "SE", "S", "SW", "W", "NW");
    public static final int MIN_STATION_ID_VALUE = 0;
    public static final int MAX_STATION_ID_VALUE = 100;
    public static final int MIN_WIND_VALUE = 0;
    public static final int MAX_WIND_VALUE = 50;
    public static final int MIN_TEMP_VALUE = -50;
    public static final int MAX_TEMP_VALUE = 50;

    public static Map<CheckOperators, MatchChecker> checkersMap = new HashMap<>();

    static {
        checkersMap.put(CheckOperators.EQUAL, new EqualChecker());
        checkersMap.put(CheckOperators.NOT_EQUAL, new NotEqualChecker());
        checkersMap.put(CheckOperators.GREATER, new GreaterChecker());
        checkersMap.put(CheckOperators.LOWER, new LowerChecker());
        checkersMap.put(CheckOperators.GREATER_EQUAL, new GreaterEqualChecker());
        checkersMap.put(CheckOperators.LOWER_EQUAL, new LowerEqualChecker());
    }

    public static int getRand(int left, int right) {
        return left + random.nextInt(right - left + 1);
    }

    public static String getRand(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    public enum CheckOperators implements Serializable {
        EQUAL,
        NOT_EQUAL,
        GREATER,
        LOWER,
        GREATER_EQUAL,
        LOWER_EQUAL,
    }

    public static class Pair<U, V> implements Serializable {
        public U first;
        public V second;

        public Pair(U first, V second) {
            this.first = first;
            this.second = second;
        }
    }
}
