package myapp.bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import myapp.publications.Publication;

import java.util.Map;

public class TempBolt extends BaseRichBolt {

    private OutputCollector collector;

    public void prepare(Map<String, Object> stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;

    }

    public void execute(Tuple input) {

        Publication publication = (Publication) input.getValueByField("publication");

        System.out.println("=============================================================================" + publication.getCity());
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("word"));
    }

}