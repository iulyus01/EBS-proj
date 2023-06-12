package ebs.spouts;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

import ebs.common.Utils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import ebs.publications.Publication;

public class PublicationSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private Socket socket;
    private ObjectInputStream ois;

    public void open(Map<String, Object> config, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        System.out.println("########################################################################################");
        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), Utils.PUBLISHER_PORT);
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void nextTuple() {
        try {
            Publication publication = (Publication) ois.readObject();
            this.collector.emit(new Values("publication", publication));
        } catch (Exception e) {
            e.printStackTrace();
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