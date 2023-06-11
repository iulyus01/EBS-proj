package ebs.publications;

import ebs.common.Utils;

import java.util.Random;

public class PublicationGenerator {

    private final Random random;

    public PublicationGenerator() {
        random = new Random();
    }

    public Publication generatePublication() {
        String city = Utils.CITY_VALUES.get(random.nextInt(Utils.CITY_VALUES.size()));
        String direction = Utils.DIRECTION_VALUES.get(random.nextInt(Utils.DIRECTION_VALUES.size()));
        int stationId = Utils.getRand(Utils.MIN_STATION_ID_VALUE, Utils.MAX_STATION_ID_VALUE);
        int wind = Utils.getRand(Utils.MIN_WIND_VALUE, Utils.MAX_WIND_VALUE);
        int temperature = Utils.getRand(Utils.MIN_TEMP_VALUE, Utils.MAX_TEMP_VALUE);

        return new Publication(stationId, city, temperature, wind, direction);
    }
}
