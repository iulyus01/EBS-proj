package ebs;

import ebs.bolts.*;
import ebs.spouts.SubscriptionSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import ebs.spouts.PublicationSpout;

public class App {

    private static final String PUBLISHER_SPOUT_ID = "publisher_spout";
    private static final String SUBSCRIBER_SPOUT_ID = "subscriber_spout";
    private static final String CITY_BOLT_ID = "CITY_BOLT_ID";
    private static final String DIRECTION_BOLT_ID = "DIRECTION_BOLT_ID";
    private static final String STATION_BOLT_ID = "STATION_BOLT_ID";
    private static final String TEMPERATURE_BOLT_ID = "TEMPERATURE_BOLT_ID";
    private static final String WIND_BOLT_ID = "WIND_BOLT_ID";
    private static final String TERMINAL_BOLT_ID = "TERMINAL_BOLT_ID";

    public static void main( String[] args ) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();
        PublicationSpout pubSpout = new PublicationSpout();
        SubscriptionSpout subSpout = new SubscriptionSpout();
        CityBolt cityBolt = new CityBolt();
        DirectionBolt directionBolt = new DirectionBolt();
        StationIdBolt stationIdBolt = new StationIdBolt();
        TemperatureBolt temperatureBolt = new TemperatureBolt();
        WindBolt windBolt = new WindBolt();
        TerminalBolt terminalBolt = new TerminalBolt();

        builder.setSpout(PUBLISHER_SPOUT_ID, pubSpout);
        builder.setSpout(SUBSCRIBER_SPOUT_ID, subSpout);
        builder.setBolt(CITY_BOLT_ID, cityBolt).globalGrouping(PUBLISHER_SPOUT_ID).globalGrouping(SUBSCRIBER_SPOUT_ID);
        builder.setBolt(DIRECTION_BOLT_ID, directionBolt).globalGrouping(CITY_BOLT_ID);
        builder.setBolt(STATION_BOLT_ID, stationIdBolt).globalGrouping(DIRECTION_BOLT_ID);
        builder.setBolt(TEMPERATURE_BOLT_ID, temperatureBolt).globalGrouping(STATION_BOLT_ID);
        builder.setBolt(WIND_BOLT_ID, windBolt).globalGrouping(TEMPERATURE_BOLT_ID);
        builder.setBolt(TERMINAL_BOLT_ID, terminalBolt).globalGrouping(WIND_BOLT_ID);

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();
        StormTopology topology = builder.createTopology();

        // fine tuning
        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE,1024);
        config.put(Config.TOPOLOGY_TRANSFER_BATCH_SIZE,1);
        config.put(Config.TOPOLOGY_DEBUG, false);

        cluster.submitTopology("CrazyTopology", config, topology);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("CrazyTopology");
        cluster.shutdown();

    }
}