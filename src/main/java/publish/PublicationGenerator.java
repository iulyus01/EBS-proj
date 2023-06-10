package publish;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PublicationGenerator {

    private final Random random;

    private final List<String> cityValues;
    private final List<String> directionValues;
    private final int minStationIdValue;
    private final int maxStationIdValue;
    private final int minWindValue;
    private final int maxWindValue;
    private final int minTempValue;
    private final int maxTempValue;


    public PublicationGenerator() {

        random = new Random();

        cityValues = Arrays.asList("Bucuresti", "Iasi", "Timisoara", "Constanta", "Bacau", "Suceava", "Braila");
        directionValues = Arrays.asList("N", "NE", "E", "SE", "S", "SW", "W", "NW");
        minStationIdValue = 0;
        maxStationIdValue = 100;
        minWindValue = 0;
        maxWindValue = 50;
        minTempValue = -50;
        maxTempValue = 50;
    }

    public Publication generatePublication() {

        String city = cityValues.get(random.nextInt(cityValues.size()));
        String direction = directionValues.get(random.nextInt(directionValues.size()));
        int stationId = minStationIdValue + random.nextInt(maxStationIdValue + 1);
        int wind = minWindValue + random.nextInt(maxWindValue + 1);
        int temperature = minTempValue + random.nextInt(maxTempValue - minTempValue + 1);

        return new Publication(stationId, city, temperature, wind, direction);
    }
}
