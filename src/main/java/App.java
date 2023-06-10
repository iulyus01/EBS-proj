import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class App
{
    private static final String SPOUT_ID = "source_text_spout";
    private static final String SPLIT_BOLT_ID = "split_bolt";

    public static void main( String[] args ) throws Exception
    {
        TopologyBuilder builder = new TopologyBuilder();
        PublicationSpout spout = new PublicationSpout();
        PrintBolt splitbolt = new PrintBolt();


        builder.setSpout(SPOUT_ID, spout);
        builder.setBolt(SPLIT_BOLT_ID, splitbolt).shuffleGrouping(SPOUT_ID);

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();
        StormTopology topology = builder.createTopology();

        // fine tuning
        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE,1024);
        config.put(Config.TOPOLOGY_TRANSFER_BATCH_SIZE,1);

        cluster.submitTopology("count_topology", config, topology);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("count_topology");
        cluster.shutdown();

    }
}