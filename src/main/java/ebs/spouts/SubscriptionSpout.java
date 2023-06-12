package ebs.spouts;

import ebs.common.Pair;
import ebs.common.Utils;
import ebs.subscriptions.Subscription;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

public class SubscriptionSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private Socket socket;
    private ObjectInputStream ois;

    public void open(Map<String, Object> config, TopologyContext context, SpoutOutputCollector collector) {

        this.collector = collector;

        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), Utils.SUBSCRIPTIONS_SPOUT_PORT);
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void nextTuple() {

        try {

            Pair<String, Subscription> subscriptionPair = (Pair<String, Subscription>) ois.readObject();
            this.collector.emit(new Values("subscriptionPair", subscriptionPair));
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("type", "data"));
    }

    @Override
    public void close() {
        super.close();
        try {
            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}