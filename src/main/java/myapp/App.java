package myapp;

import myapp.bolts.StationIdBolt;
import myapp.spouts.SubscriptionSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import myapp.spouts.PublicationSpout;

public class App {

    private static final String PUBLISHER_SPOUT_ID = "publisher_spout";
    private static final String SUBSCRIBER_SPOUT_ID = "subscriber_spout";
    private static final String SPLIT_BOLT_ID = "split_bolt";

    public static void main( String[] args ) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        PublicationSpout pubSpout = new PublicationSpout();
        SubscriptionSpout subSpout = new SubscriptionSpout();
        StationIdBolt splitBolt = new StationIdBolt();

//        builder.setSpout(PUBLISHER_SPOUT_ID, pubSpout);
        builder.setSpout(SUBSCRIBER_SPOUT_ID, subSpout);
        builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SUBSCRIBER_SPOUT_ID);

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();
        StormTopology topology = builder.createTopology();

        // fine tuning
        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE,1024);
        config.put(Config.TOPOLOGY_TRANSFER_BATCH_SIZE,1);

        cluster.submitTopology("count_topology", config, topology);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("count_topology");
        cluster.shutdown();

    }
}